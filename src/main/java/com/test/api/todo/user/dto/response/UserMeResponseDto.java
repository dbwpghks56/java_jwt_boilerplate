package com.test.api.todo.user.dto.response;

import com.test.api.todo.user.domain.model.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class UserMeResponseDto {
    @Schema(description = "조회한 유저 닉네임")
    private String nickname;
    @Schema(description = "조회한 유저 핸드폰 번호")
    private String phone;
    @Schema(description = "조회한 유저 사업자 등록 번호")
    private String crn;
    @Schema(description = "조회한 유저 생성 시간")
    private LocalDateTime createdAt;

    @Builder
    public UserMeResponseDto(User entity) {
        this.nickname = entity.getNickName();
        this.phone = entity.getPhone();
        this.crn = entity.getCrn();
        this.createdAt = entity.getCreatedDTime();
    }
}
