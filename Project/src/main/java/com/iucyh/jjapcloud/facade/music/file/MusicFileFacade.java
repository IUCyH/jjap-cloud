package com.iucyh.jjapcloud.facade.music.file;

import com.iucyh.jjapcloud.common.exception.ServiceException;
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

    public IdDto uploadMusic(long userId, CreateMusicDto musicDto) {
        MusicUploadResult uploadResult = null;
        try {
            uploadResult = musicFileService.uploadMusic(musicDto.getMusicFile());
            return musicService.createMusic(userId, musicDto, uploadResult);
        } catch (ServiceException e) {
            if (uploadResult != null) {
                musicFileService.deleteMusic(uploadResult.getStoreName());
            }
            throw e;
        }
    }

    public void deleteMusic(long userId, int musicId) {
        String storeName = musicService.deleteMusic(userId, musicId);
        musicFileService.deleteMusic(storeName);
    }
}
