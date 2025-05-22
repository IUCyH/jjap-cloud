package com.iucyh.jjapcloud.domain.music;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Music {

    private Integer id;
    private String name;
    private String originalName;
    private String singer;
    private Integer runtime;

    public Music() {}
}
