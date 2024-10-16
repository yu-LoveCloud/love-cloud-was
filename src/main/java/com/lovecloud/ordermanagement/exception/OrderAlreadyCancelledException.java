package com.lovecloud.ordermanagement.exception;

import com.lovecloud.global.exception.ErrorCode;
import com.lovecloud.global.exception.LoveCloudException;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

public class OrderAlreadyCancelledException extends LoveCloudException {
    public OrderAlreadyCancelledException(){
        super(new ErrorCode(BAD_REQUEST,"이미 취소된 주문입니다."));
    }
}
