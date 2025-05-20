package com.iucyh.jjapcloud.common.exception.errorcode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    // CLIENT
    INVALID_PROPERTY(HttpStatus.BAD_REQUEST, "CLIENT-001"),
    INVALID_PARAMETER(HttpStatus.BAD_REQUEST, "CLIENT-002"),
    INVALID_METHOD(HttpStatus.METHOD_NOT_ALLOWED, "CLIENT-003"),
    NO_HANDLER_FOUND(HttpStatus.NOT_FOUND, "CLIENT-004"),

    // Server
    SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "SERVER-001");

    private final HttpStatus httpStatus;
    private final String code;
}
