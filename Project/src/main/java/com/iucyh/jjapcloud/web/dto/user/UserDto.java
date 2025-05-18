package com.iucyh.jjapcloud.web.dto.user;

import com.iucyh.jjapcloud.domain.user.User;
import lombok.Getter;

@Getter
public class UserDto {

    private Integer id;
    private String nickname;

    public static UserDto from(User user) {
        UserDto userDto = new UserDto();
        userDto.id = user.getId();
        userDto.nickname = user.getNickname();
        return userDto;
    }
}
