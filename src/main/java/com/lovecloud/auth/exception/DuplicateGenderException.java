package com.lovecloud.auth.exception;

import com.lovecloud.global.exception.ErrorCode;
import com.lovecloud.global.exception.LoveCloudException;

import static org.springframework.http.HttpStatus.CONFLICT;

public class DuplicateGenderException extends LoveCloudException {

    public DuplicateGenderException() {
        super(new ErrorCode(CONFLICT, "성별이 중복됩니다. 다른 성별로 선택해주세요."));
    }
}
