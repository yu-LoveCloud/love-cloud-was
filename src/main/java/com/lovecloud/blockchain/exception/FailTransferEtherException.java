package com.lovecloud.blockchain.exception;

import com.lovecloud.global.exception.ErrorCode;
import com.lovecloud.global.exception.LoveCloudException;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

public class FailTransferEtherException extends LoveCloudException {

    public FailTransferEtherException() {
        super(new ErrorCode(INTERNAL_SERVER_ERROR, "이더 전송에 실패했습니다."));
    }
}
