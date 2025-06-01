package com.iucyh.jjapcloud.dto.user.query;

import com.iucyh.jjapcloud.domain.user.User;

public class UserSimpleDto {

    private Integer id;
    private String nickname;
    private String email;

    public UserSimpleDto(Integer id, String nickname, String email) {
        this.id = id;
        this.nickname = nickname;
        this.email = email;
    }

    public User toEntity() {
        User user = new User();
        user.setId(id);
        user.setNickname(nickname);
        user.setEmail(email);
        return user;
    }
}
