package com.Api.ecommerce.Service.Security.Implementations;

import com.Api.ecommerce.Exception.Security.*;
import com.Api.ecommerce.Model.Dto.Security.*;
import com.Api.ecommerce.Model.Entity.Security.Customer;
import com.Api.ecommerce.Model.Entity.Security.Role;
import com.Api.ecommerce.Repository.Security.CustomerRepository;
import com.Api.ecommerce.Repository.Security.RoleRepository;
import com.Api.ecommerce.Security.CustomeUserDetailsService;
import com.Api.ecommerce.Security.JwtUtil;
import com.Api.ecommerce.Security.SecurityConstants;
import com.Api.ecommerce.Service.Implementations.EmailService;
import com.Api.ecommerce.Service.Security.Interfaces.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private CustomerRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private CustomeUserDetailsService userDetailsService;
    @Autowired
    private EmailService emailService;


    @Override
    public ResponseEntity<String> register(RegisterDto registerDto) {
        if (userRepository.existsByEmail(registerDto.getEmail())) {
            throw new UsernameAlreadyExistsException("Username already exists");
        }

        Role role = roleRepository.findByName(registerDto.getRole()).orElse(null);
        if (role == null) {
            throw new RoleNotFoundException("Role '" + registerDto.getRole() + "' not found");
        }

        String password = passwordEncoder.encode(registerDto.getPassword());

        Customer user = Customer.builder()
                .name(registerDto.getName())
                .email(registerDto.getEmail())
                .phone(registerDto.getPhone())
                .address(registerDto.getAddress())
                .password(password)
                .roles(Collections.singleton(role))
                .build();
        userRepository.save(user);
        emailService.sendEmail(registerDto.getEmail(),
                "Welcome to E-Commerce Platform", "Hello "
                + registerDto.getName()
                + ",\n\nWelcome to our platform! We are glad to have you with us.\n\nBest regards,\nE-Commerce Team");


        return new ResponseEntity<>("Username registered successfully", HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<TokenResponse> login(LoginDto loginDto) {
        try {
            Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken
                    (loginDto.getEmail(), loginDto.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(auth);
            UserDetails user = userDetailsService.loadUserByUsername(loginDto.getEmail());
            String accessToken = jwtUtil.generateToken(user);
            String refreshToken = jwtUtil.generateRefreshToken(user);

            TokenResponse response = new TokenResponse(accessToken);
            response.setRefreshToken(refreshToken);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (AuthenticationException e) {
            throw new LoginException("Invalid username or password");
        } catch (Exception e) {
            throw new LoginException("An error occurred during login");
        }
    }

    @Override
    public ResponseEntity<AccessTokenResponse> refreshToken(RefreshTokenRequest request) {
        String refreshToken = request.getRefreshToken();
        String username = jwtUtil.extractUserName(refreshToken, SecurityConstants.REFRESH_SECRET_KEY);
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        if (jwtUtil.isRefreshTokenValidate(refreshToken, userDetails)) {
            String newAccessToken = jwtUtil.generateToken(userDetails);
            AccessTokenResponse tokenResponse = new AccessTokenResponse(newAccessToken);

            return new ResponseEntity<>(tokenResponse, HttpStatus.OK);
        } else {
            throw new TokenValidationException("Invalid refresh token");
        }
    }

    @Override
    public ResponseEntity<String> changePassword(ChangePasswordRequest request, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        Customer user = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new PasswordMismatchException("User not found"));

        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            throw new PasswordMismatchException("Wrong password");
        }

        if (!request.getNewPassword().equals(request.getConfirmationPassword())) {
            throw new PasswordMismatchException("Passwords do not match");
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);

        return new ResponseEntity<>("Password changed successfully", HttpStatus.OK);
    }
}