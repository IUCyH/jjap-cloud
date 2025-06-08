package com.iucyh.jjapcloud.dto.playlist.query;

import com.iucyh.jjapcloud.dto.music.query.JoinMusicDto;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

@Getter
public class PlaylistItemSimpleDto {

    private Long id;
    private JoinMusicDto music;
    private Integer position;

    @QueryProjection
    public PlaylistItemSimpleDto(Long id, Integer position, JoinMusicDto music) {
        this.id = id;
        this.music = music;
        this.position = position;
    }
}
