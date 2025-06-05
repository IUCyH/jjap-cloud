package com.iucyh.jjapcloud.dto.music;

import com.iucyh.jjapcloud.domain.music.Music;
import com.iucyh.jjapcloud.dto.music.query.MusicSimpleDto;
import com.iucyh.jjapcloud.dto.user.UserDto;
import com.iucyh.jjapcloud.dtomapper.UserDtoMapper;
import lombok.Getter;

@Getter
public class MusicDto {

    private Integer id;
    private String name;
    private UserDto singer;
    private Long playTime;

    public static MusicDto from(Music music) {
        MusicDto musicDto = new MusicDto();
        musicDto.id = music.getId();
        musicDto.name = music.getName();
        musicDto.singer = UserDtoMapper.toUserDto(music.getUser());
        musicDto.playTime = music.getPlayTime();
        return musicDto;
    }

    public static MusicDto fromQueryDto(MusicSimpleDto music) {
        MusicDto musicDto = new MusicDto();
        musicDto.id = music.getId();
        musicDto.name = music.getName();
        musicDto.singer = UserDtoMapper.toUserDto(music.getUser());
        musicDto.playTime = music.getPlayTime();
        return musicDto;
    }
}
