package com.lovecloud.blockchain.exception;

import com.lovecloud.global.exception.ErrorCode;
import com.lovecloud.global.exception.LoveCloudException;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

public class WalletAlreadyAssignedException extends LoveCloudException {
    public WalletAlreadyAssignedException() {
        super(new ErrorCode(INTERNAL_SERVER_ERROR, "이미 지갑이 할당되어 있습니다."));
    }
}