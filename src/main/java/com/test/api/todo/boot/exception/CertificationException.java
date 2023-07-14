package com.test.api.todo.boot.exception;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@Getter
@ToString
@NoArgsConstructor
public class CertificationException extends RuntimeException {
    private HttpStatus httpStatus;
    private String message;

    @Builder
    public CertificationException(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }
}