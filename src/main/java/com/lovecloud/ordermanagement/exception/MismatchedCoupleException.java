package com.lovecloud.ordermanagement.exception;

import com.lovecloud.global.exception.ErrorCode;
import com.lovecloud.global.exception.LoveCloudException;

import static org.springframework.http.HttpStatus.FORBIDDEN;

public class MismatchedCoupleException extends LoveCloudException {
    public MismatchedCoupleException() {
        super(new ErrorCode(FORBIDDEN,"커플이 일치하지 않습니다."));
    }
}
