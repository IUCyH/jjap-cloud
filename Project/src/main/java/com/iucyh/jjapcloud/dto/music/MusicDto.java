package com.iucyh.jjapcloud.dto.music;

import com.iucyh.jjapcloud.domain.music.Music;
import com.iucyh.jjapcloud.dto.music.query.MusicSimpleDto;
import com.iucyh.jjapcloud.dto.user.UserDto;
import com.iucyh.jjapcloud.dtomapper.UserDtoMapper;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MusicDto {

    private Long id;
    private String name;
    private UserDto singer;
    private Long playTime;
}
