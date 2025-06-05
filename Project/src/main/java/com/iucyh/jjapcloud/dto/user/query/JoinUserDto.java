package com.iucyh.jjapcloud.dto.user.query;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

@Getter
public class JoinUserDto {

    private Integer id;
    private String nickname;

    @QueryProjection
    public JoinUserDto(Integer id, String nickname) {
        this.id = id;
        this.nickname = nickname;
    }
}
