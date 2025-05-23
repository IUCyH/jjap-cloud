package com.iucyh.jjapcloud.web.service.music;

import com.iucyh.jjapcloud.web.dto.IdDto;
import com.iucyh.jjapcloud.web.dto.RequestSuccessDto;
import com.iucyh.jjapcloud.web.dto.music.CreateMusicDto;
import com.iucyh.jjapcloud.web.dto.music.MusicDto;
import com.iucyh.jjapcloud.web.dto.music.RangeDto;
import org.springframework.core.io.InputStreamResource;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

public interface MusicService {

    List<MusicDto> getMusics(Date date);
    MusicDto getMusicById(int id);
    String getMusicStoreName(int id);
    File getFile(String fileName);
    RangeDto getRange(File file, String rangeHeader);
    InputStreamResource streamMusic(File file, RangeDto range) throws IOException;
    IdDto createMusic(CreateMusicDto music) throws IOException;
    RequestSuccessDto deleteMusic(int id);
}
