package com.Api.ecommerce.Service.Security.Interfaces;

import com.Api.ecommerce.Model.Dto.Security.LogoutRequest;
import org.springframework.http.ResponseEntity;

public interface LogoutService {
    ResponseEntity<String> logout(LogoutRequest logoutRequestDto) ;

}
