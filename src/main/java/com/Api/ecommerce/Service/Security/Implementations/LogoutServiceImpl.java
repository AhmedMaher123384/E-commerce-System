package com.Api.ecommerce.Service.Security.Implementations;


import com.Api.ecommerce.Exception.Security.TokenValidationException;
import com.Api.ecommerce.Model.Dto.Security.LogoutRequest;
import com.Api.ecommerce.Model.Entity.Security.AccessTokenBlackList;
import com.Api.ecommerce.Model.Entity.Security.RefreshTokenBlackList;
import com.Api.ecommerce.Repository.Security.AccessTokenBlackListRepository;
import com.Api.ecommerce.Repository.Security.RefreshTokenBlackListRepository;
import com.Api.ecommerce.Security.CustomeUserDetailsService;
import com.Api.ecommerce.Security.JwtUtil;
import com.Api.ecommerce.Security.SecurityConstants;
import com.Api.ecommerce.Service.Security.Interfaces.LogoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
@Service
public class LogoutServiceImpl implements LogoutService {

    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private CustomeUserDetailsService userDetailsService;
    @Autowired
    private AccessTokenBlackListRepository accessTokenBlackListRepository;
    @Autowired
    private RefreshTokenBlackListRepository refreshTokenBlackListRepository;

    @Override
    public ResponseEntity<String> logout(LogoutRequest logoutRequest) {
        if (!jwtUtil.isTokenValidate(logoutRequest.getAccessToken(), userDetailsService.loadUserByUsername(jwtUtil.extractUserName(logoutRequest.getAccessToken(), SecurityConstants.SECRET_KEY)), SecurityConstants.SECRET_KEY) ||
                !jwtUtil.isTokenValidate(logoutRequest.getRefreshToken(), userDetailsService.loadUserByUsername(jwtUtil.extractUserName(logoutRequest.getRefreshToken(), SecurityConstants.REFRESH_SECRET_KEY)), SecurityConstants.REFRESH_SECRET_KEY)) {
            throw new TokenValidationException("Invalid token");
        }

        Date accessTokenExpiry = jwtUtil.extractExpirationDate(logoutRequest.getAccessToken(), SecurityConstants.SECRET_KEY);
        Date refreshTokenExpiry = jwtUtil.extractExpirationDate(logoutRequest.getRefreshToken(), SecurityConstants.REFRESH_SECRET_KEY);

        AccessTokenBlackList blacklistedAccessToken = new AccessTokenBlackList(null, logoutRequest.getAccessToken(), accessTokenExpiry);
        RefreshTokenBlackList blacklistedRefreshToken = new RefreshTokenBlackList(null, logoutRequest.getRefreshToken(), refreshTokenExpiry);

        accessTokenBlackListRepository.save(blacklistedAccessToken);
        refreshTokenBlackListRepository.save(blacklistedRefreshToken);
        return new ResponseEntity<>("Logout Success", HttpStatus.OK);
    }
}
