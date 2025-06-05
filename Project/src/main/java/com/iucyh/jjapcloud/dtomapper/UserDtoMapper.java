package com.iucyh.jjapcloud.dtomapper;

import com.iucyh.jjapcloud.domain.user.User;
import com.iucyh.jjapcloud.dto.user.MyUserDto;
import com.iucyh.jjapcloud.dto.user.UserDto;
import com.iucyh.jjapcloud.dto.user.query.JoinUserDto;

public class UserDtoMapper {

    private UserDtoMapper() {}

    public static UserDto toUserDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .nickname(user.getNickname())
                .build();
    }

    public static UserDto toUserDto(JoinUserDto user) {
        return UserDto.builder()
                .id(user.getId())
                .nickname(user.getNickname())
                .build();
    }

    public static MyUserDto toMyUserDto(User user) {
        return MyUserDto.builder()
                .id(user.getId())
                .nickname(user.getNickname())
                .email(user.getEmail())
                .build();
    }
}
