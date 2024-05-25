package com.lovecloud.global.jwt.exception;

import com.lovecloud.global.exception.ErrorCode;
import com.lovecloud.global.exception.LoveCloudException;
import org.springframework.http.HttpStatus;

public class UnsupportedJwtException extends LoveCloudException {
    public UnsupportedJwtException() {
        super(new ErrorCode(HttpStatus.BAD_REQUEST, "지원되지 않는 JWT 토큰입니다."));
    }
}
