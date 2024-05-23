package com.lovecloud.productmanagement.exception;

import static org.springframework.http.HttpStatus.NOT_FOUND;

import com.lovecloud.global.exception.ErrorCode;
import com.lovecloud.global.exception.LoveCloudException;

public class NotFoundProductOptionsException extends LoveCloudException {

    public NotFoundProductOptionsException() {
        super(new ErrorCode(NOT_FOUND, "존재하지 않는 상품 옵션입니다."));
    }
}
