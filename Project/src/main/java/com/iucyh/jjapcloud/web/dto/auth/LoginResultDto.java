package com.iucyh.jjapcloud.web.dto.auth;

import com.iucyh.jjapcloud.web.dto.user.UserDto;
import lombok.Getter;

@Getter
public class LoginResultDto {

    private UserDto user;
    private String csrfToken;

    public LoginResultDto(UserDto user, String csrfToken) {
        this.user = user;
        this.csrfToken = csrfToken;
    }
}
