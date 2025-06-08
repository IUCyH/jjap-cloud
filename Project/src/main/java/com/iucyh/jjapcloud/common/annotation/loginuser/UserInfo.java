package com.iucyh.jjapcloud.common.annotation.loginuser;

import com.iucyh.jjapcloud.dto.user.UserDto;
import lombok.Getter;

@Getter
public class UserInfo {

    private Long id;
    private String nickname;

    public static UserInfo from(UserDto user) {
        UserInfo userInfo = new UserInfo();
        userInfo.id = user.getId();
        userInfo.nickname = user.getNickname();
        return userInfo;
    }
}
