package com.lovecloud.fundingmanagement.exception;

import static org.springframework.http.HttpStatus.NOT_FOUND;

import com.lovecloud.global.exception.ErrorCode;
import com.lovecloud.global.exception.LoveCloudException;

public class NotFoundFundingException extends LoveCloudException {

    public NotFoundFundingException() {
        super(new ErrorCode(NOT_FOUND, "존재하지 않는 펀딩입니다."));
    }
}
