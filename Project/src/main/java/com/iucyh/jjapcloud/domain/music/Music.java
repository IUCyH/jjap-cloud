package com.iucyh.jjapcloud.domain.music;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class Music {

    private Integer id;
    private String originalName;
    private String singer;
    private Long playTime;
    private LocalDateTime createTime;

    public Music() {}
}
