package com.iucyh.jjapcloud.web.dto.music;

import com.iucyh.jjapcloud.domain.music.Music;
import lombok.Getter;

@Getter
public class MusicDto {

    private Integer id;
    private String name;
    private String singer;
    private Integer runtime;
    private String url;

    public static MusicDto from(Music music) {
        MusicDto musicDto = new MusicDto();
        musicDto.id = music.getId();
        musicDto.name = music.getName();
        musicDto.singer = music.getSinger();
        musicDto.runtime = music.getRuntime();
        musicDto.url = music.getUrl();
        return musicDto;
    }
}
