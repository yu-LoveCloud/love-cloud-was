package com.lovecloud.global.jwt.exception;

import com.lovecloud.global.exception.ErrorCode;
import com.lovecloud.global.exception.LoveCloudException;
import org.springframework.http.HttpStatus;

public class MalformedJwtException extends LoveCloudException {
    public MalformedJwtException() {
        super(new ErrorCode(HttpStatus.BAD_REQUEST, "잘못된 JWT 서명입니다."));
    }
}