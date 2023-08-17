package com.test.api.todo.boot.advisor;

import com.test.api.todo.boot.dto.response.CommonResponseDto;
import com.test.api.todo.boot.exception.CertificationException;
import com.test.api.todo.boot.exception.RestException;
import com.test.api.todo.boot.exception.UserNotFoundException;
import com.test.api.todo.boot.exception.WithdrawalUserException;
import com.test.api.todo.boot.util.FilterErrorUtils;
import io.sentry.Sentry;
import io.sentry.spring.tracing.SentrySpan;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MultipartException;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class CustomExceptionHandler {
    private final FilterErrorUtils filterErrorUtils;

    @ExceptionHandler(RestException.class)
    public ResponseEntity<CommonResponseDto> restExceptionHandler(RestException e) {
        e.printStackTrace();
        CommonResponseDto responseDto = CommonResponseDto.builder()
                .success(false)
                .status(e.getHttpStatus().value())
                .message(e.getMessage())
                .data(new ArrayList<>())
                .build();
        return new ResponseEntity<>(responseDto, e.getHttpStatus());
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<CommonResponseDto> badCredentialExceptionHandler(BadCredentialsException e) {
        Sentry.captureException(e);
        CommonResponseDto responseDto = CommonResponseDto.builder()
                .success(false)
                .status(HttpStatus.UNAUTHORIZED.value())
                .message(e.getMessage())
                .data(new ArrayList<>())
                .build();
        return new ResponseEntity<>(responseDto, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<CommonResponseDto> userNotFoundExceptionHandler(UserNotFoundException e) {
        Sentry.captureException(e);
        CommonResponseDto responseDto = CommonResponseDto.builder()
                .success(false)
                .status(e.getHttpStatus().value())
                .message(e.getMessage())
                .data(new ArrayList<>())
                .build();
        e.printStackTrace();
        return new ResponseEntity<>(responseDto, e.getHttpStatus());
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<CommonResponseDto> accessDeniedHandler(AccessDeniedException e) {
        Sentry.captureException(e);
        CommonResponseDto responseDto = CommonResponseDto.builder()
                .success(false)
                .status(HttpStatus.FORBIDDEN.value())
                .message(e.getMessage())
                .data(new ArrayList<>())
                .build();
        e.printStackTrace();
        return new ResponseEntity<>(responseDto, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(CertificationException.class)
    public ResponseEntity<CommonResponseDto> certificateExceptionHandler(CertificationException e) throws Exception {
        Sentry.captureException(e);
        CommonResponseDto responseDto = CommonResponseDto.builder()
                .success(false)
                .status(HttpStatus.BAD_REQUEST.value())
                .message(e.getMessage())
                .data(new ArrayList<>())
                .build();
        e.printStackTrace();
        return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler()
    public ResponseEntity<CommonResponseDto> allExceptionHandler(Exception e) {
        Sentry.captureException(e);
        CommonResponseDto responseDto = CommonResponseDto.builder()
                .success(false)
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message(e.getMessage())
                .data(new ArrayList<>())
                .build();
        e.printStackTrace();
        return new ResponseEntity<>(responseDto, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(WithdrawalUserException.class)
    public ResponseEntity<CommonResponseDto> withdrawalExceptionHandler(WithdrawalUserException e){
        Sentry.captureException(e);
        CommonResponseDto responseDto = CommonResponseDto.builder()
                .success(false)
                .status(HttpStatus.BAD_REQUEST.value())
                .message(e.getMessage())
                .data(new ArrayList<>())
                .build();
        e.printStackTrace();
        return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
    }
}
