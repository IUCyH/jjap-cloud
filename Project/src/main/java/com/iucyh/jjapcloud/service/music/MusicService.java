package com.iucyh.jjapcloud.service.music;

import com.iucyh.jjapcloud.common.exception.ServiceException;
import com.iucyh.jjapcloud.common.exception.errorcode.ServiceErrorCode;
import com.iucyh.jjapcloud.common.util.FileManager;
import com.iucyh.jjapcloud.domain.music.Music;
import com.iucyh.jjapcloud.domain.user.User;
import com.iucyh.jjapcloud.dto.IdDto;
import com.iucyh.jjapcloud.dto.music.CreateMusicDto;
import com.iucyh.jjapcloud.dto.music.MusicDto;
import com.iucyh.jjapcloud.dto.music.SearchMusicCondition;
import com.iucyh.jjapcloud.dto.music.query.MusicSimpleDto;
import com.iucyh.jjapcloud.dtomapper.MusicDtoMapper;
import com.iucyh.jjapcloud.repository.music.MusicQueryRepository;
import com.iucyh.jjapcloud.repository.music.MusicRepository;
import com.iucyh.jjapcloud.repository.user.UserRepositoryDataJpa;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MusicService {

    private final MusicRepository musicRepository;
    private final UserRepositoryDataJpa userRepository;
    private final MusicQueryRepository musicQueryRepository;
    private final FileManager fileManager;

    public List<MusicDto> getMusics(Date date) {
        List<MusicSimpleDto> musics = musicQueryRepository.findMusics(date);
        return musics
                .stream()
                .map(MusicDtoMapper::toMusicDto)
                .toList();
    }

    public List<MusicDto> searchMusics(SearchMusicCondition condition, Date date) {
        List<Music> musics = musicQueryRepository.searchMusics(condition.getField(), condition.getKeyword(), date);
        return musics
                .stream()
                .map(MusicDtoMapper::toMusicDto)
                .toList();
    }

    public MusicDto getMusicById(int id) {
        return musicRepository
                .findById(id)
                .map(MusicDtoMapper::toMusicDto)
                .orElseThrow(() -> new ServiceException(ServiceErrorCode.MUSIC_NOT_FOUND));
    }

    @Transactional
    public IdDto createMusic(Integer userId, CreateMusicDto music) {
        if(!fileManager.isCorrectMimeType(music.getMusicFile(), "audio/mpeg")) {
            throw new ServiceException(ServiceErrorCode.NOT_VALID_MUSIC_FILE);
        }

        Optional<User> user = userRepository.findById(userId);
        if(user.isEmpty()) {
            throw new ServiceException(ServiceErrorCode.USER_NOT_FOUND);
        }

        String storeName = fileManager.uploadFile(music.getMusicFile(), "music");
        long playTime = fileManager.getPlayTime(music.getMusicFile().getSize(), 320000);

        Music newMusic = new Music();
        newMusic.setName(music.getName());
        newMusic.setStoreName(storeName);
        newMusic.setUser(user.get());
        newMusic.setPlayTime(playTime);
        newMusic.setCreateTime(music.getCreateTime());

        return new IdDto(musicRepository.save(newMusic).getId());
    }

    @Transactional
    public void deleteMusic(int id) {
        musicRepository.deleteById(id);
    }
}
