package com.lovecloud.auth.exception;

import com.lovecloud.global.exception.ErrorCode;
import com.lovecloud.global.exception.LoveCloudException;

import static org.springframework.http.HttpStatus.NOT_FOUND;

public class NotFoundWeddingUserException extends LoveCloudException {

    public NotFoundWeddingUserException() {
        super(new ErrorCode(NOT_FOUND, "존재하지 않는 웨딩 유저입니다."));
    }
}
