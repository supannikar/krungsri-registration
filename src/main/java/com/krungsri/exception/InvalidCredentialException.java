package com.krungsri.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidCredentialException extends RuntimeException {

    public InvalidCredentialException(String param) {
        super(String.format("Invalid credential %s ", param));
    }
}
