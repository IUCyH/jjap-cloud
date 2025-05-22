package com.iucyh.jjapcloud.web.dto.music;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class CreateMusicDto {

    private String name;
    private String singer;
    private Integer runtime;
    private MultipartFile musicFile;

    public CreateMusicDto() {}
}
