package com.lovecloud.auth.exception;

import com.lovecloud.global.exception.ErrorCode;
import com.lovecloud.global.exception.LoveCloudException;

import static org.springframework.http.HttpStatus.CONFLICT;

public class DuplicatePartnerException extends LoveCloudException {

    public DuplicatePartnerException() {
        super(new ErrorCode(CONFLICT, "이미 파트너가 존재합니다."));
    }
}
