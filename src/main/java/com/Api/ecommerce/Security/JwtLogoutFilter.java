package com.Api.ecommerce.Security;


import com.Api.ecommerce.Model.Dto.Security.LogoutRequest;
import com.Api.ecommerce.Service.Security.Implementations.LogoutServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

@Component
public class JwtLogoutFilter implements LogoutHandler {

    @Autowired
    private LogoutServiceImpl authService;

    public JwtLogoutFilter(LogoutServiceImpl authService) {
        this.authService = authService;
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            String accessToken = header.substring(7);
            String refreshToken = request.getParameter("refreshToken");
            LogoutRequest logoutRequest =new LogoutRequest(accessToken,refreshToken);
            authService.logout(logoutRequest);
        }
    }
}