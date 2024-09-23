package com.lovecloud.ordermanagement.exception;

import com.lovecloud.global.exception.ErrorCode;
import com.lovecloud.global.exception.LoveCloudException;

import static org.springframework.http.HttpStatus.NOT_FOUND;

public class DeliveryAddressNotFoundException extends LoveCloudException {
    public DeliveryAddressNotFoundException() {
        super(new ErrorCode(NOT_FOUND, "존재하지 않는 배송지입니다."));
    }

}
