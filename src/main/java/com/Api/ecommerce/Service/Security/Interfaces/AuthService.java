package com.Api.ecommerce.Service.Security.Interfaces;

import com.Api.ecommerce.Model.Dto.Security.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

public interface AuthService {

    ResponseEntity<String> register( RegisterDto registerDto);
    ResponseEntity<TokenResponse> login(LoginDto loginDto);
    ResponseEntity<AccessTokenResponse> refreshToken( RefreshTokenRequest request);
    ResponseEntity<String> changePassword(ChangePasswordRequest request, Authentication authentication);


    }
