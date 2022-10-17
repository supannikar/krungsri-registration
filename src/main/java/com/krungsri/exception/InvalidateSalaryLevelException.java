package com.krungsri.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidateSalaryLevelException extends RuntimeException {

    public InvalidateSalaryLevelException(String param) {
        super(String.format("Invalid salary %s: need salary more than 15000", param));
    }
}
