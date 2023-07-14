package com.test.api.todo.auth.handler;

import com.test.api.todo.boot.util.FilterErrorUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class CustomAuthEntryPoint implements AuthenticationEntryPoint {

    private final FilterErrorUtils filterErrorUtils;

    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        filterErrorUtils.sendUnauthorizedException(e, httpServletResponse);
//        httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED);
    }
}
