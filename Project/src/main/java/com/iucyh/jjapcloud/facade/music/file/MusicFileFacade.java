package com.iucyh.jjapcloud.facade.music.file;

import com.iucyh.jjapcloud.common.exception.ServiceException;
import com.iucyh.jjapcloud.domain.music.Music;
import com.iucyh.jjapcloud.dto.IdDto;
import com.iucyh.jjapcloud.dto.music.CreateMusicDto;
import com.iucyh.jjapcloud.service.music.MusicService;
import com.iucyh.jjapcloud.service.music.file.MusicFileService;
import com.iucyh.jjapcloud.service.music.file.MusicUploadResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MusicFileFacade {

    private final MusicFileService musicFileService;
    private final MusicService musicService;

    public IdDto uploadMusic(int userId, CreateMusicDto musicDto) {
        try {
            MusicUploadResult uploadResult = musicFileService.uploadMusic(musicDto.getMusicFile());
            return musicService.createMusic(userId, musicDto, uploadResult);
        } catch (ServiceException e) {
            // TODO: 파일 존재 시 삭제
            throw e;
        }
    }
}
