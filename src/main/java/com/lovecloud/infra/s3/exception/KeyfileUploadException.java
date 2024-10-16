package com.lovecloud.infra.s3.exception;

import com.lovecloud.global.exception.ErrorCode;
import com.lovecloud.global.exception.LoveCloudException;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

public class KeyfileUploadException extends LoveCloudException {

    public KeyfileUploadException(String message, Throwable cause) {
        super(new ErrorCode(INTERNAL_SERVER_ERROR, message), cause);
    }
}
