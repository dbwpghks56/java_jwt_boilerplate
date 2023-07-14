package com.test.api.todo.auth.service.impl;

import com.test.api.todo.auth.domain.model.AuthToken;
import com.test.api.todo.auth.domain.repository.AuthTokenRepository;
import com.test.api.todo.auth.dto.request.AccessTokenRefreshRequestDto;
import com.test.api.todo.auth.dto.request.SignInRequestDto;
import com.test.api.todo.auth.dto.response.AccessTokenRefreshResponseDto;
import com.test.api.todo.auth.dto.response.SignInResponseDto;
import com.test.api.todo.auth.service.AuthService;
import com.test.api.todo.boot.exception.RestException;
import com.test.api.todo.boot.exception.UserNotFoundException;
import com.test.api.todo.boot.exception.WithdrawalUserException;
import com.test.api.todo.boot.util.JwtUtils;
import com.test.api.todo.user.domain.model.User;
import com.test.api.todo.user.domain.repository.UserRepository;
import com.test.api.todo.user.service.impl.UserDetailsImpl;
import com.test.api.todo.user.service.impl.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
@Service
@Slf4j
public class AuthServiceImpl implements AuthService {
    private final AuthTokenRepository authTokenRepository;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final UserDetailsServiceImpl userDetailsService;
    private final PasswordEncoder passwordEncoder;

    /**
     * 로그인
     * @param signInRequestDto
     * @return
     * @throws Exception
     */
    @Override
    @Transactional
    public SignInResponseDto signIn(SignInRequestDto signInRequestDto) throws Exception {
        // 전달 받은 Request 로 사용자 인증 후, Context Holder 에 저장한다.
        try{
            Authentication authentication = null;
            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signInRequestDto.getAccount(), signInRequestDto.getPassword()));

            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

            SecurityContextHolder.getContext().setAuthentication(authentication);

            // 토큰 발급
            String accessToken = jwtUtils.generateAccessToken(authentication);
            String refreshToken = jwtUtils.generateRefreshToken(authentication);

            if (userDetails.getStatus().equals(-1))
                throw new WithdrawalUserException(HttpStatus.BAD_REQUEST, "탈퇴 신청된 사용자입니다.");

            /**
             * Refresh Token 을 한 번 더 암호화 한 후, Fake Refresh Token 을 사용자 응답으로, 그리고 DB 내에 Index 로서 저장한다.
             * 만일, DB 내에 해당 사용자가 이미 존재할 경우 기존 값을 대체한다.
             */
            String fakeRefreshToken = passwordEncoder.encode(refreshToken);

            Boolean isLogin = authTokenRepository.existsByUserSeq(userDetails.getSeq());
            if(isLogin) {
                log.info("기존에 로그인된 일반 사용자입니다. DB 값을 제거 후 재삽입합니다. userSeq=" + userDetails.getSeq());
                authTokenRepository.deleteByUserSeq(userDetails.getSeq());
            }

            AuthToken authTokenEntity = AuthToken.builder()
                    .seq(fakeRefreshToken)
                    .userSeq(userDetails.getSeq())
                    .refreshToken(refreshToken)
                    .accessToken(accessToken)
                    .build();

            authTokenRepository.save(authTokenEntity);

            User userEntity = userRepository.findBySeq(userDetails.getSeq()).orElseThrow(() -> new RestException(HttpStatus.NOT_FOUND, "존재하는 회원이 없습니다."));

            SignInResponseDto signInResponseDto = SignInResponseDto.builder().accessToken(accessToken).build();

            //쿠키 값 세팅
            /**
             * path : 쿠키가 적용될 url path를 지정한다.
             * httpOnly : http 방식으로만 Cookie 접근을 허용한다.
             * maxAge : 쿠키의 만료기간을 설정한다.
             * secure :  현재 ssl 등이 적용된 Secure Connection일 경우에 동작 시킨다.
             */
            ResponseCookie responseCookie = ResponseCookie.from("RefreshToken", refreshToken)
                    .path("/")
                    .httpOnly(true)
                    .maxAge(60 * 60 * 24 * 7)
                    .sameSite("None")
                    .secure(true)
                    .build();

            HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
            response.setHeader("Set-Cookie",responseCookie.toString());

            return signInResponseDto;
        } catch (WithdrawalUserException e) {
            throw new WithdrawalUserException(e.getHttpStatus(), e.getMessage());
        }
    }


    /**
     * 토큰 리프레시
     * @return
     */
    @Override
    @Transactional
    public AccessTokenRefreshResponseDto accessTokenRefresh() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        String refreshToken = null;
        String accessToken = jwtUtils.getAccessTokenFromBearer(request.getHeader("Authorization"));

        Cookie[] cookies = request.getCookies();

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("RefreshToken")) {
                    refreshToken = cookie.getValue();
                    break;
                }
            }
        }

        String username = jwtUtils.getUsernameFromAccessToken(accessToken);

        if(!userRepository.existsByUsername(username))
            throw new UserNotFoundException(username);

        /**
         * 전달받은 Access Token 과 Refresh Token 이 DB 내에 존재하는지 확인한다.
         * 만일, 존재하지 않을 경우 위조/예전 사용자라고 판단하고 예외를 발생시킨다.
         */
        if(!authTokenRepository.existsByAccessTokenAndRefreshToken(accessToken, refreshToken)) {
            throw new RestException(HttpStatus.NOT_FOUND, "일치하는 토큰 정보를 DB 에서 찾을 수 없습니다.");
        }

        // Refresh Token 의 유효기간을 검증한다. 유효 기간이 만료되었을 경우, 예외를 발생시키고, 해당 토큰 정보를 DB 에서 제거한다.
        if(!jwtUtils.validateRefreshToken(refreshToken)) {
            log.error("Refresh Token 이 만료되었습니다. 해당 토큰 정보를 DB 에서 제거합니다.");
            authTokenRepository.deleteByRefreshToken(refreshToken);
            throw new RestException(HttpStatus.valueOf(401), "Refresh Token 이 만료되었습니다. 해당 토큰 정보를 DB 에서 제거합니다.");
        }

        // 새 Access Token 을 발급한다. 이때, 새 aT 를 발급하기 위해 authentication 객체를 얻어 인자로 전달한다.
        UserDetailsImpl userDetails = userDetailsService.loadUserByUsername(username);
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        String newAccessToken = jwtUtils.generateAccessToken(authenticationToken);

        AuthToken authTokenEntity = authTokenRepository.findByRefreshToken(refreshToken)
                .orElseThrow(() -> new RestException(HttpStatus.NOT_FOUND, "Access Token 발급 중, 일치하는 Refresh Token 을 찾을 수 없습니다."));

        authTokenEntity.updateAccessToken(newAccessToken);

        return AccessTokenRefreshResponseDto.builder()
                .accessToken(newAccessToken)
                .build();
    }
}











