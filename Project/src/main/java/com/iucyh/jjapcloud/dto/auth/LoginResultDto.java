package com.iucyh.jjapcloud.dto.auth;

import com.iucyh.jjapcloud.dto.user.MyUserDto;
import lombok.Getter;

@Getter
public class LoginResultDto {

    private MyUserDto user;
    private String csrfToken;

    public LoginResultDto(MyUserDto user, String csrfToken) {
        this.user = user;
        this.csrfToken = csrfToken;
    }
}
