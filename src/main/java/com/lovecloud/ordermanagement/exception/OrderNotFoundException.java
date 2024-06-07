package com.lovecloud.ordermanagement.exception;

import com.lovecloud.global.exception.ErrorCode;
import com.lovecloud.global.exception.LoveCloudException;

import static org.springframework.http.HttpStatus.NOT_FOUND;

public class OrderNotFoundException extends LoveCloudException {
    public OrderNotFoundException() {
        super(new ErrorCode(NOT_FOUND,"존재하지 않는 주문입니다."));
    }
}
