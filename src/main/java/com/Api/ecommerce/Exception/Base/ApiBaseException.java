package com.Api.ecommerce.Exception.Base;

import org.springframework.http.HttpStatus;

public abstract class ApiBaseException extends  RuntimeException {

    public ApiBaseException(String message) {
        super(message);
    }

    public abstract HttpStatus getHttpStatus();
}
