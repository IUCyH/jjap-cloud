package com.iucyh.jjapcloud.web.dto.music;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateMusicDto {

    private String name;
    private String singer;
    private Integer runtime;

    public CreateMusicDto() {}
}
