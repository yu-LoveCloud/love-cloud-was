package com.lovecloud.usermanagement.exeption;

import com.lovecloud.global.exception.ErrorCode;
import com.lovecloud.global.exception.LoveCloudException;

import static org.springframework.http.HttpStatus.NOT_FOUND;

public class NotFoundCoupleException extends LoveCloudException {

        public NotFoundCoupleException() {
            super(new ErrorCode(NOT_FOUND, "존재하지 않는 부부입니다."));
        }
}
