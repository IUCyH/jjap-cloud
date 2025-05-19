package com.iucyh.jjapcloud.web.dto.user;

import com.iucyh.jjapcloud.domain.user.User;
import lombok.Getter;

@Getter
public class UserInfoDto {

    private Integer id;
    private String nickname;

    public static UserInfoDto from(User user) {
        UserInfoDto userInfoDto = new UserInfoDto();
        userInfoDto.id = user.getId();
        userInfoDto.nickname = user.getNickname();
        return userInfoDto;
    }
}
