package com.test.api.todo.boot.enums;

public enum CustomExceptionCodeEnum {
    ACCESS_TOKEN_EXPIRED(999),
    REFRESH_TOKEN_EXPIRED(998);

    private final int code;
    CustomExceptionCodeEnum(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
