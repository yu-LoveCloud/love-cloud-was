package com.lovecloud.auth.exception;

import com.lovecloud.global.exception.ErrorCode;
import com.lovecloud.global.exception.LoveCloudException;

import static org.springframework.http.HttpStatus.CONFLICT;

public class DuplicateEmailException extends LoveCloudException {

    public DuplicateEmailException() {
        super(new ErrorCode(CONFLICT, "이미 존재하는 아이디입니다. 다른 아이디로 가입해주세요."));
    }
}
