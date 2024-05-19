package com.lovecloud.invitationmanagement.exeption;

import com.lovecloud.global.exception.ErrorCode;
import com.lovecloud.global.exception.LoveCloudException;

import static org.springframework.http.HttpStatus.NOT_FOUND;

public class NotFoundInvitationImageException extends LoveCloudException {
    public NotFoundInvitationImageException(){
        super(new ErrorCode(NOT_FOUND, "존재하지 않는 청첩장 이미지입니다."));
    }
}
