package com.test.api.todo.user.web;

import com.test.api.todo.user.dto.request.SignUpRequestDto;
import com.test.api.todo.user.dto.response.UserMeResponseDto;
import com.test.api.todo.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Tag(name = "[유저 & 관리자] User", description = "유저 API")
public class UserController {
    private final UserService userService;

    @PostMapping
    @Tag(name = "[유저 & 관리자] User")
    @Operation(summary = "[유저 & 관리자] 회원가입 API", description = "유저를 생성한다.")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> signUp(
        @RequestBody SignUpRequestDto signUpRequestDto
    ) throws Exception {
        userService.signUp(signUpRequestDto);
        return null;
    }

    @GetMapping
    @Tag(name = "[유저 & 관리자] User")
    @Operation(summary = "[유저 & 관리자] 회원조회 API - JWT", description = "accessToken 을 이용해 회원를 조회한다.")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<UserMeResponseDto> userMe() throws Exception {
        return ResponseEntity.ok(userService.userMe());
    }

    @DeleteMapping
    @Tag(name = "[유저 & 관리자] User")
    @Operation(summary = "[유저 & 관리자] 회원탈퇴 API - JWT", description = "accessToken 을 이용해 회원탈퇴를 한다. <br> status 를 -1 로 바꾸고 나머지 칼럼들을 암호화한다.")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> userDelete() throws Exception {
        userService.userDelete();
        return null;
    }
}








