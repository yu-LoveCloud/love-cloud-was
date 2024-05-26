package com.lovecloud.auth.exception;

import com.lovecloud.global.exception.ErrorCode;
import com.lovecloud.global.exception.LoveCloudException;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;

public class NotMatchPasswordException extends LoveCloudException {

    public NotMatchPasswordException() {
        super(new ErrorCode(UNAUTHORIZED, "비밀번호가 일치하지 않습니다."));
    }
}

