package com.lovecloud.global.exception;

import static com.lovecloud.global.exception.ErrorCode.INTERNAL_SERVER_ERROR_CODE;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

import lombok.Getter;

@Getter
public class LoveCloudException extends RuntimeException {

    private final ErrorCode errorCode;

    public LoveCloudException(ErrorCode errorCode) {
        super(errorCode.message());
        this.errorCode = errorCode;
    }

    public LoveCloudException(String message) {
        super(message);
        this.errorCode = new ErrorCode(INTERNAL_SERVER_ERROR, message);
    }

    public LoveCloudException() {
        this.errorCode = INTERNAL_SERVER_ERROR_CODE;
    }

    public LoveCloudException(ErrorCode errorCode, Throwable cause) {
        super(errorCode.message(), cause);
        this.errorCode = errorCode;
    }
}
