package com.Api.ecommerce.Exception.Security;

import com.Api.ecommerce.Exception.Base.ApiBaseException;
import org.springframework.http.HttpStatus;

public class VerificationCodeException extends ApiBaseException {

    public VerificationCodeException(String message) {
        super(message);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }
}
