package com.lovecloud.blockchain.exception;

import com.lovecloud.global.exception.ErrorCode;
import com.lovecloud.global.exception.LoveCloudException;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

public class BlockchainException extends LoveCloudException {

    public BlockchainException(String message, Throwable cause) {
        super(new ErrorCode(INTERNAL_SERVER_ERROR, message), cause);
    }

    public BlockchainException(String message) {
        super(new ErrorCode(INTERNAL_SERVER_ERROR, message));
    }
}
