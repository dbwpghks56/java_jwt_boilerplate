package com.test.api.todo.boot.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.api.todo.boot.dto.response.CommonResponseDto;
import com.test.api.todo.boot.enums.CustomExceptionCodeEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@Slf4j
@Component
@RequiredArgsConstructor
public class FilterErrorUtils {

    private final ObjectMapper objectMapper;

    public CommonResponseDto getExceptionResponseDto(Exception e, Integer status) {
        System.out.println(e.getMessage());
        CommonResponseDto responseDto = CommonResponseDto.builder()
                .success(false)
                .message(e.getMessage())
                .status(status)
                .data(new ArrayList<>())
                .build();

        return responseDto;
    }

    public HttpServletResponse sendUnauthorizedException(Exception e, HttpServletResponse response) throws IOException {
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write(objectMapper.writeValueAsString(getExceptionResponseDto(e, HttpServletResponse.SC_UNAUTHORIZED)));

        return response;
    }

    public HttpServletResponse sendForbiddenException(Exception e, HttpServletResponse response) throws IOException {
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.getWriter().write(objectMapper.writeValueAsString(getExceptionResponseDto(e, HttpServletResponse.SC_FORBIDDEN)));

        return response;
    }

    public HttpServletResponse sendAccessTokenExpiredException(Exception e, HttpServletResponse response) throws IOException {
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json");
        response.setStatus(CustomExceptionCodeEnum.ACCESS_TOKEN_EXPIRED.getCode());
        response.getWriter().write(objectMapper.writeValueAsString(getExceptionResponseDto(e, CustomExceptionCodeEnum.ACCESS_TOKEN_EXPIRED.getCode())));

        return response;
    }

    public HttpServletResponse sendUsernameNotFoundException(Exception e, HttpServletResponse response) throws IOException {
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json");
        response.setStatus(HttpStatus.NOT_FOUND.value());
        response.getWriter().write(objectMapper.writeValueAsString(getExceptionResponseDto(e, HttpStatus.NOT_FOUND.value())));

        return response;
    }

    public HttpServletResponse sendWithdrawalUserException(Exception e, HttpServletResponse response) throws IOException {
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json");
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.getWriter().write(objectMapper.writeValueAsString(getExceptionResponseDto(e, HttpStatus.BAD_REQUEST.value())));

        return response;
    }
}
