package com.lovecloud.productmanagement.exception;

import static org.springframework.http.HttpStatus.NOT_FOUND;

import com.lovecloud.global.exception.ErrorCode;
import com.lovecloud.global.exception.LoveCloudException;

public class NotFoundCategoryException extends LoveCloudException {

    public NotFoundCategoryException() {
        super(new ErrorCode(NOT_FOUND, "존재하지 않는 카테고리입니다."));
    }
}
