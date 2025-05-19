package com.iucyh.jjapcloud.web.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    // Application Error
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "ACCOUNT-001", "사용자를 찾을 수 없습니다."),
    EMAIL_OR_PASSWORD_WRONG(HttpStatus.BAD_REQUEST, "ACCOUNT-002", "이메일 혹은 비밀번호가 잘못되었습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
