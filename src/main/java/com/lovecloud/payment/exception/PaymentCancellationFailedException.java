package com.lovecloud.payment.exception;

import com.lovecloud.global.exception.ErrorCode;
import com.lovecloud.global.exception.LoveCloudException;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

public class PaymentCancellationFailedException extends LoveCloudException {
    public PaymentCancellationFailedException() {
        super(new ErrorCode(BAD_REQUEST, "결제 취소에 실패했습니다."));
    }
}
