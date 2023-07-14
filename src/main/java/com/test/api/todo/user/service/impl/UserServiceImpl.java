package com.test.api.todo.user.service.impl;

import com.test.api.todo.boot.exception.RestException;
import com.test.api.todo.boot.util.CipherUtils;
import com.test.api.todo.user.domain.model.User;
import com.test.api.todo.user.domain.repository.UserRepository;
import com.test.api.todo.user.dto.request.SignUpRequestDto;
import com.test.api.todo.user.dto.response.UserMeResponseDto;
import com.test.api.todo.user.service.UserService;
import com.test.api.todo.user.service.VerifyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final CipherUtils cipherUtils;
    private final VerifyService verifyService;

    /**
     * 회원가입
     * @param signUpRequestDto
     * @throws Exception
     */
    @Override
    @Transactional
    public void signUp(SignUpRequestDto signUpRequestDto) throws Exception {
        // 비밀번호 암호화 ( 단방향 암호화 )
        String password = passwordEncoder.encode(signUpRequestDto.getPassword());
        // 핸드폰 번호 암호화 ( 복호화 가능 )
        String phone = cipherUtils.encrypt(signUpRequestDto.getPhone());
        signUpRequestDto.setPassword(password);
        signUpRequestDto.setPhone(phone);

        if (verifyService.accountVerify(signUpRequestDto.getAccount()).getVerify() && verifyService.crnVerify(signUpRequestDto.getCrn()).getVerify()
                && verifyService.nickNameVerify(signUpRequestDto.getNickname()).getVerify()) {
            User userEntity = signUpRequestDto.toEntity();
            userRepository.save(userEntity);
        } else {
            throw new RestException(HttpStatus.BAD_REQUEST, "올바르지 않은 요청입니다.");
        }
    }

    /**
     * 유저 조회
     * @return
     * @throws Exception
     */
    @Override
    @Transactional(readOnly = true)
    public UserMeResponseDto userMe() throws Exception {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        User user = userRepository.findByUsername(userDetails.getUsername()).orElseThrow(() -> new RestException(HttpStatus.NOT_FOUND, "존재하지 않는 유저입니다."));

        UserMeResponseDto userMeResponseDto = UserMeResponseDto.builder().entity(user).build();

        // response 를 이용해 유저에게 보여줄 때는 핸드폰 번호를 복호화해서 전달
        userMeResponseDto.setPhone(cipherUtils.decrypt(userMeResponseDto.getPhone()));

        return userMeResponseDto;
    }

    /**
     * 회원탈퇴
     * @throws Exception
     */
    @Override
    @Transactional
    public void userDelete() throws Exception {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        User user = userRepository.findByUsername(userDetails.getUsername()).orElseThrow(() -> new RestException(HttpStatus.NOT_FOUND, "존재하지 않는 유저입니다."));
        // 회원의 상태를 -1 로 만들어 탈퇴회원으로 만든다.
        user.setStatus(-1);
        // 회원의 핸드폰 번호, 비밀번호는 DB에 암호화되어있는 상태이므로 사업자번호만 암호화하여 update한다.
        user.deleteUser(cipherUtils.encrypt(user.getCrn()));
    }
}












