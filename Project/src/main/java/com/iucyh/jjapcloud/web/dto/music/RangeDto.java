package com.iucyh.jjapcloud.web.dto.music;

import lombok.Getter;

@Getter
public class RangeDto {

    private long start;
    private long end;

    public RangeDto(long start, long end) {
        this.start = start;
        this.end = end;
    }
}
