package com.test.api.todo.user.web;

import com.test.api.todo.user.dto.response.VerifyResponseDto;
import com.test.api.todo.user.service.VerifyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/verify")
@Tag(name = "[유저 & 관리자] Verify", description = "검증 API")
public class VerifyController {
    private final VerifyService verifyService;

    @GetMapping("/account")
    @Tag(name = "[유저 & 관리자] Verify")
    @Operation(summary = "[유저 & 관리자] 아이디 중복 체크 API", description = "입력 받은 아이디가 DB에 존재하는지 중복 체크한다.")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<VerifyResponseDto> accountVerify(
            @RequestParam String account
    ) {
        return ResponseEntity.ok(verifyService.accountVerify(account));
    }
    @GetMapping("/crn")
    @Tag(name = "[유저 & 관리자] Verify")
    @Operation(summary = "[유저 & 관리자] 사업자 등록번호 유효성 체크 API", description = "입력 받은 사업자 번호가 유효성에 맞는지 체크한다.")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<VerifyResponseDto> crnVerify(
            @RequestParam String crn
    ) {
        return ResponseEntity.ok(verifyService.crnVerify(crn));
    }
    @GetMapping("/nickname")
    @Tag(name = "[유저 & 관리자] Verify")
    @Operation(summary = "[유저 & 관리자] 닉네임 중복 체크 API", description = "입력 받은 닉네임이 DB에 존재하는지 중복 체크한다.")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<VerifyResponseDto> nickNameVerify(
            @RequestParam String nickName
    ) {
        return ResponseEntity.ok(verifyService.nickNameVerify(nickName));
    }

}
