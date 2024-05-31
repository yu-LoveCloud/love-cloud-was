package com.lovecloud.payment.exception;

import com.lovecloud.global.exception.ErrorCode;
import com.lovecloud.global.exception.LoveCloudException;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

public class DuplicatePaymentException extends LoveCloudException {
    public DuplicatePaymentException() {
        super(new ErrorCode(BAD_REQUEST, "이미 결제가 완료된 거래입니다."));
    }
}
