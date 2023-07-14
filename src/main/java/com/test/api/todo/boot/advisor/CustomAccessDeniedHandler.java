package com.test.api.todo.boot.advisor;

import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
        HttpStatus exceptionCode;
        exceptionCode = HttpStatus.FORBIDDEN;
        setResponse(response, exceptionCode);
    }

    private void setResponse(HttpServletResponse response, HttpStatus exceptionCode) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);

        JSONObject responseJson = new JSONObject();
        responseJson.put("success", false);
        responseJson.put("status", exceptionCode.value());
        responseJson.put("message", "허용 되지 않은 접근입니다. AccessToken을 확인하세요.");
        responseJson.put("data", new ArrayList<>());

        response.getWriter().print(responseJson);
    }
}
