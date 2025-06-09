package com.iucyh.jjapcloud.service.auth;

import com.iucyh.jjapcloud.dto.user.MyUserDto;
import lombok.Getter;

@Getter
public class UserLoginResult {

    private Long userId;
    private MyUserDto user;

    public UserLoginResult(Long userId, MyUserDto user) {
        this.userId = userId;
        this.user = user;
    }
}
