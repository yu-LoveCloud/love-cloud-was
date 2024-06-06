package com.lovecloud.ordermanagement.exception;

import com.lovecloud.global.exception.ErrorCode;
import com.lovecloud.global.exception.LoveCloudException;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

public class FundingNotCompletedException extends LoveCloudException {
    public FundingNotCompletedException() {
        super(new ErrorCode(BAD_REQUEST,"펀딩이 완료되지 않았습니다."));
    }
}
