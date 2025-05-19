package com.iucyh.jjapcloud.web.common.exception.errorcode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ServiceErrorCode {

    // ACCOUNT
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "ACCOUNT-001", "User Not Found"),
    EMAIL_EXISTED(HttpStatus.BAD_REQUEST, "ACCOUNT-002", "Email Exists"),
    EMAIL_OR_PASSWORD_WRONG(HttpStatus.BAD_REQUEST, "ACCOUNT-003", "Email or Password Wrong"),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "ACCOUNT-004", "Unauthorized");
    
    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
