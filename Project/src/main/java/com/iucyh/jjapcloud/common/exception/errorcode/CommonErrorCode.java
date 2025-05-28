package com.iucyh.jjapcloud.common.exception.errorcode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CommonErrorCode implements ErrorCode {

    // CLIENT
    INVALID_PROPERTY(HttpStatus.BAD_REQUEST, "CLIENT-001", "Invalid Property"),
    INVALID_PARAMETER(HttpStatus.BAD_REQUEST, "CLIENT-002", "Invalid Parameter"),
    INVALID_METHOD(HttpStatus.METHOD_NOT_ALLOWED, "CLIENT-003", "Invalid Method"),
    NO_HANDLER_FOUND(HttpStatus.NOT_FOUND, "CLIENT-004", "No Handler Found"),

    // Server
    SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "SERVER-001", "Internal Server Error");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
