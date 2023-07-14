package com.test.api.todo.auth.web;

import com.test.api.todo.auth.dto.request.SignInRequestDto;
import com.test.api.todo.auth.dto.response.AccessTokenRefreshResponseDto;
import com.test.api.todo.auth.dto.response.SignInResponseDto;
import com.test.api.todo.auth.service.AuthService;
import com.test.api.todo.user.dto.request.SignUpRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "[유저 & 관리자] Auth", description = "인증 API")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    @Tag(name = "[유저 & 관리자] Auth")
    @Operation(summary = "[유저 & 관리자] 로그인 API", description = "유저에게 전달받은 데이터를 이용해 로그인을 진행한다.")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<SignInResponseDto> signIng(
            @RequestBody SignInRequestDto signInRequestDto
            ) throws Exception {
        return ResponseEntity.ok(authService.signIn(signInRequestDto));
    }

    @PostMapping("/logout")
    @Tag(name = "[유저 & 관리자] Auth")
    @Operation(summary = "[유저 & 관리자] 로그아웃 API", description = "클라이언트에서 token을 제거하여 로그아웃 한다.")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> logOut(
    ) throws Exception {
        return null;
    }

    @GetMapping("/token")
    @Tag(name = "[유저 & 관리자] Auth")
    @Operation(summary = "[유저 & 관리자] 토큰 리프레시 API", description = "리프레시 토큰을 받아 accessToken을 리프레시한다.")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<AccessTokenRefreshResponseDto> accessTokenRefresh(
    ) throws Exception {
        return ResponseEntity.ok(authService.accessTokenRefresh());
    }
}











