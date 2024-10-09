package com.lovecloud.blockchain.exception;

import com.lovecloud.global.exception.ErrorCode;
import com.lovecloud.global.exception.LoveCloudException;

import static org.springframework.http.HttpStatus.NOT_FOUND;

public class NotFoundWalletException extends LoveCloudException {

    public NotFoundWalletException() {
        super(new ErrorCode(NOT_FOUND, "해당 사용자의 지갑 정보를 찾을 수 없습니다."));
    }
}