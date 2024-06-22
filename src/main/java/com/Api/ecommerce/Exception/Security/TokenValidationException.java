package com.Api.ecommerce.Exception.Security;

import com.Api.ecommerce.Exception.Base.ApiBaseException;
import org.springframework.http.HttpStatus;

public class TokenValidationException extends ApiBaseException {

    public TokenValidationException(String message) {
        super(message);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.UNAUTHORIZED;
    }
}