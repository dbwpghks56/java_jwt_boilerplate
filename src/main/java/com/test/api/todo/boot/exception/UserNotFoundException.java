package com.test.api.todo.boot.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@Getter
@ToString
@NoArgsConstructor
public class UserNotFoundException extends RuntimeException {
    private HttpStatus httpStatus;
    private String username;
    private String message;

    public UserNotFoundException(String username) {
        this.httpStatus = HttpStatus.NOT_FOUND;
        this.username = username;
        this.message = "해당하는 유저를 찾을 수 없습니다. username=" + username;
    }
}
