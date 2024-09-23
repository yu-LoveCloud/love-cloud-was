package com.lovecloud.blockchain.exception;

import com.lovecloud.global.exception.ErrorCode;
import com.lovecloud.global.exception.LoveCloudException;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

public class FailVerifyWalletException extends LoveCloudException {
    public FailVerifyWalletException() {
        super(new ErrorCode(INTERNAL_SERVER_ERROR, "지갑 검증에 실패했습니다."));
    }
}
