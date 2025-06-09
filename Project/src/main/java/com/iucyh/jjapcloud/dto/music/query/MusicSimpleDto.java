package com.iucyh.jjapcloud.dto.music.query;

import com.iucyh.jjapcloud.dto.user.query.JoinUserDto;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

@Getter
public class MusicSimpleDto {

    private String publicId;
    private String name;
    private Long playTime;
    private JoinUserDto user;

    @QueryProjection
    public MusicSimpleDto(String publicId, String name, Long playTime, JoinUserDto user) {
        this.publicId = publicId;
        this.name = name;
        this.playTime = playTime;
        this.user = user;
    }
}
