package com.lovecloud.fundingmanagement.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

import com.lovecloud.global.exception.ErrorCode;
import com.lovecloud.global.exception.LoveCloudException;

public class FundingTargetExceededException extends LoveCloudException {

    public FundingTargetExceededException() {
        super(new ErrorCode(BAD_REQUEST, "펀딩 목표 금액을 초과했습니다."));
    }
}
