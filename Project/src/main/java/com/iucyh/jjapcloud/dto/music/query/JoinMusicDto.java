package com.iucyh.jjapcloud.dto.music.query;

import com.iucyh.jjapcloud.dto.user.query.JoinUserDto;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class JoinMusicDto {

    private String publicId;
    private JoinUserDto user;
    private String title;
    private Long playTime;
    private LocalDateTime createdAt;

    @QueryProjection
    public JoinMusicDto(String publicId, String title, Long playTime, LocalDateTime createdAt, JoinUserDto user) {
        this.user = user;
        this.publicId = publicId;
        this.title = title;
        this.playTime = playTime;
        this.createdAt = createdAt;
    }
}
