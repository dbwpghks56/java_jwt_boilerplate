package com.test.api.todo.boot.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
@Builder
public class CustomUnauthorizedException extends RuntimeException {
    private Integer httpStatus;
    private String message;
}
