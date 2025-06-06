package com.iucyh.jjapcloud.service.music.file;

import org.springframework.web.multipart.MultipartFile;

public interface MusicFileService {

    MusicUploadResult uploadMusic(MultipartFile musicFile);
}
