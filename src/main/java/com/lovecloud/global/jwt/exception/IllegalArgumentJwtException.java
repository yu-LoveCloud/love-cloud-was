package com.lovecloud.global.jwt.exception;

import com.lovecloud.global.exception.ErrorCode;
import com.lovecloud.global.exception.LoveCloudException;
import org.springframework.http.HttpStatus;

public class IllegalArgumentJwtException extends LoveCloudException {
    public IllegalArgumentJwtException() {
        super(new ErrorCode(HttpStatus.BAD_REQUEST, "JWT 토큰이 잘못되었습니다."));
    }
}
