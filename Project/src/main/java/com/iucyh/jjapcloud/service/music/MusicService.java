package com.iucyh.jjapcloud.service.music;

import com.iucyh.jjapcloud.common.exception.ServiceException;
import com.iucyh.jjapcloud.common.exception.errorcode.ServiceErrorCode;
import com.iucyh.jjapcloud.common.wrapper.LimitedInputStream;
import com.iucyh.jjapcloud.domain.music.Music;
import com.iucyh.jjapcloud.dto.IdDto;
import com.iucyh.jjapcloud.dto.music.CreateMusicDto;
import com.iucyh.jjapcloud.dto.music.MusicDto;
import com.iucyh.jjapcloud.dto.music.RangeDto;
import com.iucyh.jjapcloud.dto.music.SearchMusicCondition;
import org.springframework.core.io.InputStreamResource;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface MusicService {

    List<MusicDto> getMusics(Date date);
    List<MusicDto> searchMusics(SearchMusicCondition condition, Date date);
    MusicDto getMusicById(int id);
    IdDto createMusic(CreateMusicDto music);
    void deleteMusic(int id);
}
