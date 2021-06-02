package com.example.jwt.authprojectjwt.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
public class JwtException extends RuntimeException{
    public JwtException(String message) {
        super(message);
    }
}
