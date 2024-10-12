package com.lovecloud.infra.s3;

import com.lovecloud.global.exception.ErrorCode;
import com.lovecloud.global.exception.LoveCloudException;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

public class KeyfileReadException extends LoveCloudException {

    public KeyfileReadException(String path) {
        super(new ErrorCode(INTERNAL_SERVER_ERROR, "Keyfile을 읽는 데 실패했습니다. 경로: " + path));
    }
}
