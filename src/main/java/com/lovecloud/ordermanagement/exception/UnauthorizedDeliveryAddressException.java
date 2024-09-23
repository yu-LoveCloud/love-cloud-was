package com.lovecloud.ordermanagement.exception;

import com.lovecloud.global.exception.ErrorCode;
import com.lovecloud.global.exception.LoveCloudException;

import static org.springframework.http.HttpStatus.FORBIDDEN;

public class UnauthorizedDeliveryAddressException extends LoveCloudException {
    public UnauthorizedDeliveryAddressException() {
        super(new ErrorCode(FORBIDDEN,"배송지 접근 권한이 없습니다."));
    }
}
