package com.lovecloud.fundingmanagement.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

import com.lovecloud.global.exception.ErrorCode;
import com.lovecloud.global.exception.LoveCloudException;

public class MismatchedMerchantUidsException extends LoveCloudException {

    public MismatchedMerchantUidsException() {
        super(new ErrorCode(BAD_REQUEST, "merchant_uid가 일치하지 않습니다."));
    }
}
