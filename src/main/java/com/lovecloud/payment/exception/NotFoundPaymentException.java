package com.lovecloud.payment.exception;

import static org.springframework.http.HttpStatus.NOT_FOUND;

import com.lovecloud.global.exception.ErrorCode;
import com.lovecloud.global.exception.LoveCloudException;

public class NotFoundPaymentException extends LoveCloudException {

    public NotFoundPaymentException() {
        super(new ErrorCode(NOT_FOUND, "존재하지 않는 결제입니다."));
    }
}
