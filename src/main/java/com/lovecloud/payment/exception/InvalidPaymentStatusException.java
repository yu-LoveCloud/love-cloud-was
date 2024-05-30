package com.lovecloud.payment.exception;

import com.lovecloud.global.exception.ErrorCode;
import com.lovecloud.global.exception.LoveCloudException;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

public class InvalidPaymentStatusException extends LoveCloudException {
    public InvalidPaymentStatusException(String status) {
        super(new ErrorCode(BAD_REQUEST, "존재하지 않는 결제 상태: "+status));
    }
}
