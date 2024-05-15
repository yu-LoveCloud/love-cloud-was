package com.lovecloud.global.jwt.exception;

import com.lovecloud.global.exception.ErrorCode;
import com.lovecloud.global.exception.LoveCloudException;
import org.springframework.http.HttpStatus;

public class RefreshTokenNotFoundException extends LoveCloudException {
    public RefreshTokenNotFoundException() {
        super(new ErrorCode(HttpStatus.NOT_FOUND, "RefreshToken not found"));
    }
}
