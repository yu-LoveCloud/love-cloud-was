package com.lovecloud.fundingmanagement.exception;

import static org.springframework.http.HttpStatus.NOT_FOUND;

import com.lovecloud.global.exception.ErrorCode;
import com.lovecloud.global.exception.LoveCloudException;

public class NotFoundGuestFundingException extends LoveCloudException {

    public NotFoundGuestFundingException() {
        super(new ErrorCode(NOT_FOUND, "존재하지 않는 하객 펀딩입니다."));
    }
}
