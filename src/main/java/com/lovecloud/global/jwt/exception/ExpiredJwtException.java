package com.lovecloud.global.jwt.exception;

import com.lovecloud.global.exception.ErrorCode;
import com.lovecloud.global.exception.LoveCloudException;
import org.springframework.http.HttpStatus;

public class ExpiredJwtException extends LoveCloudException {
    public ExpiredJwtException() {
        super(new ErrorCode(HttpStatus.UNAUTHORIZED, "만료된 JWT 토큰입니다."));
    }
}
