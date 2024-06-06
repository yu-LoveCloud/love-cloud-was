package com.lovecloud.ordermanagement.exception;

import com.lovecloud.global.exception.ErrorCode;
import com.lovecloud.global.exception.LoveCloudException;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

public class DuplicateOrderFundingException extends LoveCloudException {
    public DuplicateOrderFundingException() {
        super(new ErrorCode(BAD_REQUEST,"이미 주문한 펀딩이 포함되어 있습니다."));
    }
}
