package com.lovecloud.fundingmanagement.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

import com.lovecloud.global.exception.ErrorCode;
import com.lovecloud.global.exception.LoveCloudException;

public class MismatchedAmountsException extends LoveCloudException {

    public MismatchedAmountsException() {
        super(new ErrorCode(BAD_REQUEST, "금액이 일치하지 않습니다."));
    }
}
