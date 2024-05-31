package com.lovecloud.payment.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

import com.lovecloud.global.exception.ErrorCode;
import com.lovecloud.global.exception.LoveCloudException;

public class InvalidPaymentStatusException extends LoveCloudException {

    public InvalidPaymentStatusException() {
        super(new ErrorCode(BAD_REQUEST, "결제 상태가 유효하지 않습니다."));
    }

    public InvalidPaymentStatusException(String status) {
        super(new ErrorCode(BAD_REQUEST, "존재하지 않는 결제 상태: " + status));
    }
}
