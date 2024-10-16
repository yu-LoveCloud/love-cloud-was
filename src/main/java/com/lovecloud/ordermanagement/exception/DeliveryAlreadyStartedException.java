package com.lovecloud.ordermanagement.exception;

import com.lovecloud.global.exception.ErrorCode;
import com.lovecloud.global.exception.LoveCloudException;
import org.springframework.http.HttpStatus;

public class DeliveryAlreadyStartedException extends LoveCloudException {
    public DeliveryAlreadyStartedException() {
        super(new ErrorCode(HttpStatus.BAD_REQUEST, "이미 배송이 시작된 주문입니다."));
    }
}
