package com.iucyh.jjapcloud.web.dto.auth;

import lombok.Getter;

@Getter
public class LoginDto {

    private String email;
    private String password;

    public LoginDto() {}
}
