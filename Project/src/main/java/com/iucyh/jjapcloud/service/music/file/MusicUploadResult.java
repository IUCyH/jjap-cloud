package com.iucyh.jjapcloud.service.music.file;

import lombok.Getter;

@Getter
public class MusicUploadResult {

    private String storeName;
    private long playtime;

    public MusicUploadResult(String storeName, long playtime) {
        this.storeName = storeName;
        this.playtime = playtime;
    }
}
