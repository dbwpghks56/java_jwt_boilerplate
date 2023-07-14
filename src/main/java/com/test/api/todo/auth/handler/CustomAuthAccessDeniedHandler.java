package com.test.api.todo.auth.handler;

import com.test.api.todo.boot.util.FilterErrorUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class CustomAuthAccessDeniedHandler implements AccessDeniedHandler {

    private final FilterErrorUtils filterErrorUtils;

    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
        filterErrorUtils.sendForbiddenException(e, httpServletResponse);
    }
}
