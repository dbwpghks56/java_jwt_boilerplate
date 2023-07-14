package com.test.api.todo.auth.dto.request;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class SignInRequestDto {
    @Schema(description = "유저 아이디")
    private String account;
    @Schema(description = "유저 패스워드")
    private String password;
}
