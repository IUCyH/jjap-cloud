package com.iucyh.jjapcloud.web.dto.user;

import com.iucyh.jjapcloud.domain.user.User;
import lombok.Getter;

@Getter
public class MyUserDto {

    private Integer id;
    private String nickname;
    private String email;

    public MyUserDto() {}

    public static MyUserDto from(User user) {
        MyUserDto myUserDto = new MyUserDto();
        myUserDto.id = user.getId();
        myUserDto.nickname = user.getNickname();
        myUserDto.email = user.getEmail();
        return myUserDto;
    }
}
