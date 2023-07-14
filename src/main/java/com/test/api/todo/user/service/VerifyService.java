package com.test.api.todo.user.service;

import com.test.api.todo.user.dto.response.VerifyResponseDto;

public interface VerifyService {
    VerifyResponseDto accountVerify(String account);
    VerifyResponseDto crnVerify(String crn);
    VerifyResponseDto nickNameVerify(String nickName);
}
