package com.iucyh.jjapcloud.dtomapper;

import com.iucyh.jjapcloud.domain.music.Music;
import com.iucyh.jjapcloud.dto.music.MusicDto;
import com.iucyh.jjapcloud.dto.music.query.MusicSimpleDto;
import com.iucyh.jjapcloud.dto.user.UserDto;
import com.iucyh.jjapcloud.dto.user.UserInfoDto;

public class MusicDtoMapper {

    private MusicDtoMapper() {}

    public static MusicDto toMusicDto(Music music) {
        UserInfoDto userDto = UserDtoMapper.toUserInfoDto(music.getUser());
        return MusicDto.builder()
                .publicId(music.getPublicId())
                .name(music.getTitle())
                .singer(userDto)
                .playTime(music.getPlayTime())
                .build();
    }

    public static MusicDto toMusicDto(MusicSimpleDto music) {
        UserInfoDto userDto = UserDtoMapper.toUserInfoDto(music.getUser());
        return MusicDto.builder()
                .publicId(music.getPublicId())
                .name(music.getTitle())
                .singer(userDto)
                .playTime(music.getPlayTime())
                .build();
    }
}
