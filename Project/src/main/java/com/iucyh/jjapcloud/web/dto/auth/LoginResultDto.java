package com.iucyh.jjapcloud.web.dto.auth;

import com.iucyh.jjapcloud.web.dto.user.UserInfoDto;
import lombok.Getter;

@Getter
public class LoginResultDto {

    private UserInfoDto user;
    private String csrfToken;

    public LoginResultDto(UserInfoDto user, String csrfToken) {
        this.user = user;
        this.csrfToken = csrfToken;
    }
}
