package com.lovecloud.global.jwt.exception;

import com.lovecloud.global.exception.ErrorCode;
import com.lovecloud.global.exception.LoveCloudException;
import org.springframework.http.HttpStatus;

public class InvalidRefreshTokenException extends LoveCloudException {
    public InvalidRefreshTokenException() {
        super(new ErrorCode(HttpStatus.BAD_REQUEST, "RefreshToken invalid"));
    }
}
