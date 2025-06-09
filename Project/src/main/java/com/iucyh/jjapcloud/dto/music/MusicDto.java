package com.iucyh.jjapcloud.dto.music;

import com.iucyh.jjapcloud.domain.music.Music;
import com.iucyh.jjapcloud.dto.music.query.MusicSimpleDto;
import com.iucyh.jjapcloud.dto.user.UserDto;
import com.iucyh.jjapcloud.dto.user.UserInfoDto;
import com.iucyh.jjapcloud.dtomapper.UserDtoMapper;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MusicDto {

    private String publicId;
    private String name;
    private UserInfoDto singer;
    private Long playTime;
}
