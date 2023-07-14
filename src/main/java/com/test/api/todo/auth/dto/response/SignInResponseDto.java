package com.test.api.todo.auth.dto.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.Set;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SignInResponseDto {
    private String accessToken;
}
