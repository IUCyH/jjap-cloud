package com.iucyh.jjapcloud.common.exception.errorcode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ServiceErrorCode implements ErrorCode {

    // ACCOUNT
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "ACCOUNT-001", "User Not Found"),
    EMAIL_EXISTED(HttpStatus.BAD_REQUEST, "ACCOUNT-002", "Email Exists"),
    EMAIL_OR_PASSWORD_WRONG(HttpStatus.BAD_REQUEST, "ACCOUNT-003", "Email or Password Wrong"),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "ACCOUNT-004", "Unauthorized"),

    // MUSIC
    MUSIC_NOT_FOUND(HttpStatus.NOT_FOUND, "MUSIC-001", "Music Not Found"),
    NOT_VALID_MUSIC_FILE(HttpStatus.BAD_REQUEST, "MUSIC-002", "Not Valid Music File"),
    MUSIC_PERMISSION_DENIED(HttpStatus.FORBIDDEN, "MUSIC-003", "Permission Denied");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
