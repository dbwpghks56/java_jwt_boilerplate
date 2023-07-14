package com.test.api.todo.auth.service;

import com.test.api.todo.auth.dto.request.AccessTokenRefreshRequestDto;
import com.test.api.todo.auth.dto.request.SignInRequestDto;
import com.test.api.todo.auth.dto.response.AccessTokenRefreshResponseDto;
import com.test.api.todo.auth.dto.response.SignInResponseDto;

public interface AuthService {
    SignInResponseDto signIn(SignInRequestDto signInRequestDto) throws Exception;
    AccessTokenRefreshResponseDto accessTokenRefresh();
}
