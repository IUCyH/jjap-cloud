package com.iucyh.jjapcloud.dtomapper;

import com.iucyh.jjapcloud.domain.user.User;
import com.iucyh.jjapcloud.dto.user.MyUserDto;
import com.iucyh.jjapcloud.dto.user.UserDto;
import com.iucyh.jjapcloud.dto.user.UserInfoDto;
import com.iucyh.jjapcloud.dto.user.query.JoinUserDto;

public class UserDtoMapper {

    private UserDtoMapper() {}

    public static UserDto toUserDto(User user) {
        return UserDto.builder()
                .nickname(user.getNickname())
                .build();
    }

    public static UserDto toUserDto(JoinUserDto user) {
        return UserDto.builder()
                .nickname(user.getNickname())
                .build();
    }

    public static MyUserDto toMyUserDto(User user) {
        return MyUserDto.builder()
                .nickname(user.getNickname())
                .email(user.getEmail())
                .build();
    }

    public static UserInfoDto toUserInfoDto(User user) {
        return UserInfoDto.builder()
                .publicId(user.getPublicId())
                .nickname(user.getNickname())
                .build();
    }

    public static UserInfoDto toUserInfoDto(JoinUserDto user) {
        return UserInfoDto.builder()
                .publicId(user.getPublicId())
                .nickname(user.getNickname())
                .build();
    }
}
