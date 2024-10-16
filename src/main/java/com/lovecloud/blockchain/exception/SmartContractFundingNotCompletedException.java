package com.lovecloud.blockchain.exception;

import com.lovecloud.global.exception.ErrorCode;
import com.lovecloud.global.exception.LoveCloudException;
import org.springframework.http.HttpStatus;

public class SmartContractFundingNotCompletedException extends LoveCloudException {
    public SmartContractFundingNotCompletedException() {
        super(new ErrorCode(HttpStatus.BAD_REQUEST, "스마트 컨트랙트 펀딩이 완료되지 않았습니다."));
    }

}
