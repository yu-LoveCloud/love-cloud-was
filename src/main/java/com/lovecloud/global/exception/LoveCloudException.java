package com.lovecloud.global.exception;

import lombok.Getter;

@Getter
public class LoveCloudException extends RuntimeException {

//    private final ErrorCode errorCode;
private ErrorCode errorCode;

    public LoveCloudException(ErrorCode errorCode) {
        super(errorCode.message());
        this.errorCode = errorCode;
    }

//    public LoveCloudException(String message) {
//        super(message);
//        this.errorCode = new Err;
//    }

}
