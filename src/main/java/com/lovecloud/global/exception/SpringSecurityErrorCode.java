package com.lovecloud.global.exception;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;

import java.util.Arrays;

@RequiredArgsConstructor
public enum SpringSecurityErrorCode {

    ACCESS_DENIED(AccessDeniedException.class, 403, "권한이 없는 사용자"),
    UNAUTHORIZED(AuthenticationException.class, 401, "인증되지 않은 사용자"),

    ETC(RuntimeException.class, 500, "서버에서 알 수 없는 오류가 발생했습니다.");

    private final Class<? extends RuntimeException> exceptionClass;
    private final int statusCode;
    private final String message;

    public static ErrorCode getErrorCode(RuntimeException springException) {
        return Arrays.stream(values())
                .filter(e -> e.exceptionClass.isAssignableFrom(springException.getClass()))
                .findAny()
                .map(e -> new ErrorCode(HttpStatus.valueOf(e.statusCode), e.message))
                .orElse(new ErrorCode(HttpStatus.INTERNAL_SERVER_ERROR, "서버에서 알 수 없는 오류가 발생했습니다."));
    }
}
