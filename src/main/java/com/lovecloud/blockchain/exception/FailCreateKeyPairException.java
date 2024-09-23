package com.lovecloud.blockchain.exception;

import com.lovecloud.global.exception.ErrorCode;
import com.lovecloud.global.exception.LoveCloudException;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;


public class FailCreateKeyPairException extends LoveCloudException{
    public FailCreateKeyPairException() {
        super(new ErrorCode(INTERNAL_SERVER_ERROR, "키 페어 생성에 실패했습니다."));
    }
}

