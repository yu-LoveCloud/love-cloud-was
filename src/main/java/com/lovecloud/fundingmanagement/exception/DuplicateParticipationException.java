package com.lovecloud.fundingmanagement.exception;

import com.lovecloud.global.exception.ErrorCode;
import com.lovecloud.global.exception.LoveCloudException;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

public class DuplicateParticipationException extends LoveCloudException {

    public DuplicateParticipationException() {
        super(new ErrorCode(BAD_REQUEST, "이미 해당 펀딩에 참여하였습니다."));
    }
}
