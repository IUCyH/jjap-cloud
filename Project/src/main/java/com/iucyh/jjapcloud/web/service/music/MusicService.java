package com.iucyh.jjapcloud.web.service.music;

import com.iucyh.jjapcloud.web.dto.RequestSuccessDto;
import com.iucyh.jjapcloud.web.dto.music.CreateMusicDto;
import com.iucyh.jjapcloud.web.dto.music.MusicDto;

import java.util.Date;
import java.util.List;

public interface MusicService {

    List<MusicDto> getMusics(Date date);
    MusicDto getMusicById(int id);
    int createMusic(CreateMusicDto music);
    RequestSuccessDto deleteMusic(int id);
}
