package com.test.api.todo.user.dto.request;

import com.test.api.todo.user.domain.model.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class SignUpRequestDto {
    @Schema(description = "회원가입할 유저 아이디")
    private String account;
    @Schema(description = "회원가입할 유저 비밀번호")
    private String password;
    @Schema(description = "회원가입할 유저 닉네임")
    private String nickname;
    @Schema(description = "회원가입할 유저 핸드폰 번호")
    private String phone;
    @Schema(description = "회원가입할 유저 사업자 등록 번호")
    private String crn;

    public User toEntity() {
        return User.builder()
                .username(account)
                .password(password)
                .nickName(nickname)
                .phone(phone)
                .crn(crn)
                .build();
    }
}
