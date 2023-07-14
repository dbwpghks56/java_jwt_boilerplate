package com.test.api.todo.boot.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@Getter
@ToString
@AllArgsConstructor
@Builder
public class WithdrawalUserException extends RuntimeException {
    private HttpStatus httpStatus;
    private String message;
}
