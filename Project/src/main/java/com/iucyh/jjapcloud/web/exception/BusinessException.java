package com.iucyh.jjapcloud.web.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BusinessException extends RuntimeException {

    private ErrorCode errorCode;
}
