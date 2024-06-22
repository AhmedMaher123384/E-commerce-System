package com.Api.ecommerce.Exception.Ecommerce;

import com.Api.ecommerce.Exception.Base.ApiBaseException;
import org.springframework.http.HttpStatus;

public class InsufficientProductQuantityException extends ApiBaseException {

    public InsufficientProductQuantityException(String message) {
        super(message);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.NOT_FOUND;
    }
}
