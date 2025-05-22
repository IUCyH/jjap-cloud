package com.iucyh.jjapcloud.web.dto.music;

import com.iucyh.jjapcloud.domain.music.Music;
import lombok.Getter;

@Getter
public class MusicDto {

    private Integer id;
    private String originalName;
    private String singer;
    private Long playTime;

    public static MusicDto from(Music music) {
        MusicDto musicDto = new MusicDto();
        musicDto.id = music.getId();
        musicDto.originalName = music.getOriginalName();
        musicDto.singer = music.getSinger();
        musicDto.playTime = music.getPlayTime();
        return musicDto;
    }
}
