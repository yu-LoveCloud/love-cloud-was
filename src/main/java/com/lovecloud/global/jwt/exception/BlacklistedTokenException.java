package com.lovecloud.global.jwt.exception;

import com.lovecloud.global.exception.ErrorCode;
import com.lovecloud.global.exception.LoveCloudException;
import org.springframework.http.HttpStatus;

public class BlacklistedTokenException extends LoveCloudException{
    public BlacklistedTokenException() {
        super(new ErrorCode(HttpStatus.UNAUTHORIZED, "블랙리스트에 등록된 토큰입니다."));
    }
}

