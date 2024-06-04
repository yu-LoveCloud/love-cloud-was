package com.lovecloud.ordermanagement.exception;

import com.lovecloud.global.exception.ErrorCode;
import com.lovecloud.global.exception.LoveCloudException;

import static org.springframework.http.HttpStatus.FORBIDDEN;

public class UnauthorizedOrderAccessException extends LoveCloudException {
    public UnauthorizedOrderAccessException() {
        super(new ErrorCode(FORBIDDEN, "주문 접근 권한이 없습니다."));
    }
}
