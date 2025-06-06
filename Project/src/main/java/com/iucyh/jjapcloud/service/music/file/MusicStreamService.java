package com.iucyh.jjapcloud.service.music.file;

import com.iucyh.jjapcloud.dto.music.RangeDto;
import org.springframework.core.io.InputStreamResource;

import java.io.File;

public interface MusicStreamService {

    String getMusicStoreName(int id);
    File getFile(String fileName);
    RangeDto getRange(File file, String rangeHeader);
    InputStreamResource streamMusic(File file, RangeDto range);
}
