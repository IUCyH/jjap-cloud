package com.iucyh.jjapcloud.service.music;

import com.iucyh.jjapcloud.common.exception.ServiceException;
import com.iucyh.jjapcloud.common.exception.errorcode.ServiceErrorCode;
import com.iucyh.jjapcloud.common.util.FileManager;
import com.iucyh.jjapcloud.domain.music.Music;
import com.iucyh.jjapcloud.dto.IdDto;
import com.iucyh.jjapcloud.dto.music.CreateMusicDto;
import com.iucyh.jjapcloud.dto.music.MusicDto;
import com.iucyh.jjapcloud.dto.music.SearchMusicCondition;
import com.iucyh.jjapcloud.repository.music.MusicQueryRepository;
import com.iucyh.jjapcloud.repository.music.MusicRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MusicServiceImpl implements MusicService {

    private final MusicRepository musicRepository;
    private final MusicQueryRepository musicQueryRepository;
    private final FileManager fileManager;

    public List<MusicDto> getMusics(Date date) {
        List<Music> musics = musicQueryRepository.findMusics(date);
        return musics
                .stream()
                .map(MusicDto::from)
                .toList();
    }

    public List<MusicDto> searchMusics(SearchMusicCondition condition, Date date) {
        List<Music> musics = musicQueryRepository.searchMusics(condition.getField(), condition.getKeyword(), date);
        return musics
                .stream()
                .map(MusicDto::from)
                .toList();
    }

    public MusicDto getMusicById(int id) {
        return musicRepository
                .findById(id)
                .map(MusicDto::from)
                .orElseThrow(() -> new ServiceException(ServiceErrorCode.MUSIC_NOT_FOUND));
    }

    public IdDto createMusic(CreateMusicDto music) {
        if(!fileManager.isCorrectMimeType(music.getMusicFile(), "audio/mpeg")) {
            throw new ServiceException(ServiceErrorCode.NOT_VALID_MUSIC_FILE);
        }

        String storeName = fileManager.uploadFile(music.getMusicFile(), "music");
        long playTime = fileManager.getPlayTime(music.getMusicFile().getSize(), 320000);

        Music newMusic = new Music();
        newMusic.setOriginalName(music.getName());
        newMusic.setStoreName(storeName);
        newMusic.setSinger(music.getSinger());
        newMusic.setPlayTime(playTime);
        newMusic.setCreateTime(music.getCreateTime());

        return new IdDto(musicRepository.save(newMusic).getId());
    }

    public void deleteMusic(int id) {
        musicRepository.deleteById(id);
    }
}
