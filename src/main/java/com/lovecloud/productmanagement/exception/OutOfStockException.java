package com.lovecloud.productmanagement.exception;

import com.lovecloud.global.exception.ErrorCode;
import com.lovecloud.global.exception.LoveCloudException;

import static org.springframework.http.HttpStatus.CONFLICT;

public class OutOfStockException extends LoveCloudException {
    public OutOfStockException() {
        super(new ErrorCode(CONFLICT, "재고가 부족합니다."));
    }

}
