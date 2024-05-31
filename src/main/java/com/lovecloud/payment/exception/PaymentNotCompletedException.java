package com.lovecloud.payment.exception;

import com.lovecloud.global.exception.ErrorCode;
import com.lovecloud.global.exception.LoveCloudException;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

public class PaymentNotCompletedException extends LoveCloudException {
    public PaymentNotCompletedException(){
        super(new ErrorCode(BAD_REQUEST,"결제가 완료되지 않은 거래입니다."));
    }
}
