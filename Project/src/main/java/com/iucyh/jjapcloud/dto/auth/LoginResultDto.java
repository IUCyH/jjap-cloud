package com.iucyh.jjapcloud.dto.auth;

import lombok.Getter;

@Getter
public class LoginResultDto {

    private String csrfToken;

    public LoginResultDto(String csrfToken) {
        this.csrfToken = csrfToken;
    }
}
