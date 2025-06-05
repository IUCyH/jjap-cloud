package com.iucyh.jjapcloud.dto.music.query;

import com.iucyh.jjapcloud.dto.user.query.JoinUserDto;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

@Getter
public class MusicSimpleDto {

    private Integer id;
    private String name;
    private Long playTime;
    private JoinUserDto user;

    @QueryProjection
    public MusicSimpleDto(Integer id, String name, Long playTime, JoinUserDto user) {
        this.id = id;
        this.name = name;
        this.playTime = playTime;
        this.user = user;
    }
}
