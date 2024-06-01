package com.lovecloud.fundingmanagement.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

import com.lovecloud.global.exception.ErrorCode;
import com.lovecloud.global.exception.LoveCloudException;

public class InvalidFundingStatusException extends LoveCloudException {

    public InvalidFundingStatusException() {
        super(new ErrorCode(BAD_REQUEST, "펀딩 상태가 유효하지 않습니다."));
    }
}
