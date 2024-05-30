package com.lovecloud.usermanagement.exception;

import static org.springframework.http.HttpStatus.NOT_FOUND;

import com.lovecloud.global.exception.ErrorCode;
import com.lovecloud.global.exception.LoveCloudException;

public class NotFoundCoupleException extends LoveCloudException {

    public NotFoundCoupleException() {
        super(new ErrorCode(NOT_FOUND, "존재하지 않는 커플입니다."));
    }
}
