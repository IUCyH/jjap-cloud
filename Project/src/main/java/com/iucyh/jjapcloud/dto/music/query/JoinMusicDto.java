package com.iucyh.jjapcloud.dto.music.query;

import com.iucyh.jjapcloud.dto.user.query.JoinUserDto;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class JoinMusicDto {

    private Long id;
    private JoinUserDto user;
    private String publicId;
    private String title;
    private Long playTime;
    private LocalDateTime createdAt;

    @QueryProjection
    public JoinMusicDto(Long id, String publicId, String title, Long playTime, LocalDateTime createdAt, JoinUserDto user) {
        this.id = id;
        this.user = user;
        this.publicId = publicId;
        this.title = title;
        this.playTime = playTime;
        this.createdAt = createdAt;
    }
}
