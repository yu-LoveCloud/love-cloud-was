package com.lovecloud.blockchain.exception;

import com.lovecloud.global.exception.ErrorCode;
import com.lovecloud.global.exception.LoveCloudException;
import org.springframework.http.HttpStatus;

public class FundingBlockchainException extends LoveCloudException {

    public FundingBlockchainException(String message) {
        super(new ErrorCode(HttpStatus.INTERNAL_SERVER_ERROR, message));
    }
}
