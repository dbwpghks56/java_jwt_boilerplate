package com.test.api.todo.auth.dto.response;

import lombok.*;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccessTokenRefreshResponseDto {

    private String accessToken;

}
