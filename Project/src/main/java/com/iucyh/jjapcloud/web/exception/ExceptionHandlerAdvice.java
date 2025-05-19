package com.iucyh.jjapcloud.web.exception;

import com.iucyh.jjapcloud.web.dto.RequestFailedDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice("com.iucyh.jjapcloud.web.controller")
public class ExceptionHandlerAdvice {

    @ExceptionHandler
    public ResponseEntity<RequestFailedDto> handleException(Exception e) {
        log.warn("Exception: message - {}", e.getMessage());

        if(e instanceof BusinessException businessException) {
            return handleBusinessException(businessException);
        } else {
            return ResponseEntity.internalServerError().build();
        }
    }

    private ResponseEntity<RequestFailedDto> handleBusinessException(BusinessException e) {
        ErrorCode errorCode = e.getErrorCode();
        RequestFailedDto dto = new RequestFailedDto(
                errorCode.getStatus().value(),
                errorCode.name(),
                errorCode.getCode(),
                errorCode.getMessage()
        );
        return new ResponseEntity<>(dto, errorCode.getStatus());
    }
}
