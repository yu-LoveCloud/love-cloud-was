package com.lovecloud.ordermanagement.exception;

import com.lovecloud.global.exception.ErrorCode;
import com.lovecloud.global.exception.LoveCloudException;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

public class NoAvailableFundingsException extends LoveCloudException {
    public NoAvailableFundingsException() {
        super(new ErrorCode(BAD_REQUEST,"주문가능한 펀딩이 없습니다."));
    }
}
