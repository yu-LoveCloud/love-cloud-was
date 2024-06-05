package com.lovecloud.global.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@RestControllerAdvice
public class ExceptionControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(LoveCloudException.class)
    public ResponseEntity<ErrorCode> handleException(LoveCloudException e) {
        ErrorCode errorCode = e.getErrorCode();
        log.info("잘못될 요청 들어옴", e);
        return ResponseEntity.status(errorCode.status())
                .body(errorCode);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorCode> handleException(Exception e) {
        log.error("예상치 못한 예외 발생: ", e);
        return ResponseEntity.internalServerError()
                .body(ErrorCode.INTERNAL_SERVER_ERROR_CODE);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorCode> handleRuntimeException(RuntimeException e) {
        log.error("런타임 예외 발생", e);

        ErrorCode errorCode = SpringSecurityErrorCode.getErrorCode(e);

        return ResponseEntity.status(errorCode.status())
                .body(errorCode);
    }
}
