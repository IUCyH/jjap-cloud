package com.iucyh.jjapcloud.dtomapper;

import com.iucyh.jjapcloud.domain.music.Music;
import com.iucyh.jjapcloud.dto.music.MusicDto;
import com.iucyh.jjapcloud.dto.music.query.MusicSimpleDto;
import com.iucyh.jjapcloud.dto.user.UserDto;

public class MusicDtoMapper {

    private MusicDtoMapper() {}

    public static MusicDto toMusicDto(Music music) {
        UserDto userDto = UserDtoMapper.toUserDto(music.getUser());
        return MusicDto.builder()
                .id(music.getId())
                .name(music.getName())
                .singer(userDto)
                .playTime(music.getPlayTime())
                .build();
    }

    public static MusicDto toMusicDto(MusicSimpleDto music) {
        UserDto userDto = UserDtoMapper.toUserDto(music.getUser());
        return MusicDto.builder()
                .id(music.getId())
                .name(music.getName())
                .singer(userDto)
                .playTime(music.getPlayTime())
                .build();
    }
}
