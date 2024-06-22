package com.Api.ecommerce.Controller;

import com.Api.ecommerce.Model.Dto.Security.*;
import com.Api.ecommerce.Service.Security.Implementations.AuthServiceImpl;
import com.Api.ecommerce.Service.Security.Implementations.LogoutServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
@Tag(name = "Authentication", description = "APIs for managing security")
public class AuthController {

    private final AuthServiceImpl authService;
    private final LogoutServiceImpl logoutService;

    @Operation(summary = "Register a new user")
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterDto registerDto) {
        return this.authService.register(registerDto);
    }

    @Operation(summary = "User login")
    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody LoginDto loginDto) {
        return this.authService.login(loginDto);
    }

    @Operation(summary = "Refresh access token")
    @PostMapping("/refresh")
    public ResponseEntity<AccessTokenResponse> refreshToken(@RequestBody RefreshTokenRequest request) {
        return this.authService.refreshToken(request);
    }

    @Operation(summary = "Change password")
    @PatchMapping("/reset-password")
    public ResponseEntity<String> changePassword(@RequestBody ChangePasswordRequest request, Authentication authentication) {
        return this.authService.changePassword(request, authentication);
    }

    @Operation(summary = "Forget password")
    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestBody ForgotPasswordRequest forgotPasswordRequest) {
        return this.authService.forgotPassword(forgotPasswordRequest);
    }

    @Operation(summary = "Reset new password")
    @PutMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody ResetPasswordRequest resetPasswordRequest) {
        return this.authService.resetPassword(resetPasswordRequest);
    }

    @Operation(summary = "User logout")
    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestBody LogoutRequest logoutRequest) {
        return this.logoutService.logout(logoutRequest);
    }

}
