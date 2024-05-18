package com.lovecloud.productmanagement.exception;

import static org.springframework.http.HttpStatus.NOT_FOUND;

import com.lovecloud.global.exception.ErrorCode;
import com.lovecloud.global.exception.LoveCloudException;

public class NotFoundProductException extends LoveCloudException {

    public NotFoundProductException() {
        super(new ErrorCode(NOT_FOUND, "존재하지 않는 상품입니다."));
    }
}
