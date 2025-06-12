package com.iucyh.jjapcloud.service.auth;

import lombok.Getter;

@Getter
public class UserLoginResult {

    private Long userId;
    private String csrfToken;

    public UserLoginResult(Long userId, String csrfToken) {
        this.userId = userId;
        this.csrfToken = csrfToken;
    }
}
