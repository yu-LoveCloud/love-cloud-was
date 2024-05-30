package com.lovecloud.auth.exception;

import com.lovecloud.global.exception.ErrorCode;
import com.lovecloud.global.exception.LoveCloudException;

import static org.springframework.http.HttpStatus.NOT_FOUND;

public class NotFoundUserException extends LoveCloudException {

    public NotFoundUserException() {
        super(new ErrorCode(NOT_FOUND, "존재하지 않는 회원입니다."));
    }

}
