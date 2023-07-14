package com.test.api.todo.user.service;

import com.test.api.todo.user.domain.model.User;
import com.test.api.todo.user.dto.request.SignUpRequestDto;
import com.test.api.todo.user.dto.response.UserMeResponseDto;

import java.util.Optional;

public interface UserService {
    void signUp(SignUpRequestDto signUpRequestDto) throws Exception;
    UserMeResponseDto userMe() throws Exception;
    void userDelete() throws Exception;
}
