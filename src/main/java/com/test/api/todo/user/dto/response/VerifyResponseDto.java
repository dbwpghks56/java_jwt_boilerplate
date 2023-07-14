package com.test.api.todo.user.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class VerifyResponseDto {
    @Schema(description = "검증 결과")
    Boolean verify;

    @Builder
    public VerifyResponseDto(Boolean result) {
        this.verify = result;
    }
}
