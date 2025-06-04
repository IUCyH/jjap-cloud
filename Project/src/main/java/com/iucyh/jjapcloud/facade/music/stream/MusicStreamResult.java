package com.iucyh.jjapcloud.facade.music.stream;

import com.iucyh.jjapcloud.dto.music.RangeDto;
import lombok.Getter;
import org.springframework.core.io.InputStreamResource;

import java.io.File;

@Getter
public class MusicStreamResult {

    InputStreamResource resource;
    RangeDto range;
    Long fileLength;

    public MusicStreamResult(InputStreamResource resource, RangeDto range, Long fileLength) {
        this.resource = resource;
        this.range = range;
        this.fileLength = fileLength;
    }
}
