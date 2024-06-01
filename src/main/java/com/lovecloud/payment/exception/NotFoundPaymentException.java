package com.lovecloud.payment.exception;

import static org.springframework.http.HttpStatus.NOT_FOUND;

import com.lovecloud.global.exception.ErrorCode;
import com.lovecloud.global.exception.LoveCloudException;

public class NotFoundPaymentException extends LoveCloudException {

    public NotFoundPaymentException() {
        super(new ErrorCode(NOT_FOUND, "결제 정보를 찾을 수 없습니다."));
    }
}
