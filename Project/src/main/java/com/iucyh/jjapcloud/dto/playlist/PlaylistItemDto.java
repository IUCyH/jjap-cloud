package com.iucyh.jjapcloud.dto.playlist;

import com.iucyh.jjapcloud.dto.music.MusicDto;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PlaylistItemDto {

    private Long id;
    private MusicDto music;
    private Integer position;
}
