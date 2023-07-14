package com.test.api.todo.user.service.impl;

import com.test.api.todo.user.domain.repository.UserRepository;
import com.test.api.todo.user.dto.response.VerifyResponseDto;
import com.test.api.todo.user.service.VerifyService;
import com.test.api.todo.user.validator.SignUpTransValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class VerifyServiceImpl implements VerifyService {
    private final UserRepository userRepository;

    /**
     * 아이디 검증
     * @param account
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public VerifyResponseDto accountVerify(String account) {
        // 해당 아이디가 존재하는지 확인
        Boolean result = userRepository.existsByUsername(account);

        return VerifyResponseDto.builder().result(!result).build();
    }

    /**
     * 사업자 등록 번호 검증
     * @param crn
     * @return
     */
    @Override
    @Transactional
    public VerifyResponseDto crnVerify(String crn) {
        Boolean result = false;

        // 본래 error 를 반환하게 설계했지만 요구사항에 맞게 false 를 반환
        try {
            new SignUpTransValidator().validate(crn);
            result = true;
        } catch (Exception e) {
            result = false;
        } finally {
            return VerifyResponseDto.builder().result(result).build();
        }
    }

    /**
     * 닉네임 검증
     * @param nickName
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public VerifyResponseDto nickNameVerify(String nickName) {
        // 해당 닉네임이 존재하는지 확인
        Boolean result = userRepository.existsByNickName(nickName);

        return VerifyResponseDto.builder().result(!result).build();
    }
}
