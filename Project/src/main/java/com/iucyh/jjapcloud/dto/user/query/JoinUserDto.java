package com.iucyh.jjapcloud.dto.user.query;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

@Getter
public class JoinUserDto {

    private Long id;
    private String nickname;

    @QueryProjection
    public JoinUserDto(Long id, String nickname) {
        this.id = id;
        this.nickname = nickname;
    }
}
