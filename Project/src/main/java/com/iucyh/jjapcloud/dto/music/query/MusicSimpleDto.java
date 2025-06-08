package com.iucyh.jjapcloud.dto.music.query;

import com.iucyh.jjapcloud.dto.user.query.JoinUserDto;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

@Getter
public class MusicSimpleDto {

    private Long id;
    private String name;
    private Long playTime;
    private JoinUserDto user;

    @QueryProjection
    public MusicSimpleDto(Long id, String name, Long playTime, JoinUserDto user) {
        this.id = id;
        this.name = name;
        this.playTime = playTime;
        this.user = user;
    }
}
