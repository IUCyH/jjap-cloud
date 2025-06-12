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
import com.iucyh.jjapcloud.repository.user.UserRepository;
import com.iucyh.jjapcloud.service.music.file.MusicUploadResult;
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
    private final UserRepository userRepository;
    private final MusicQueryRepository musicQueryRepository;

    public Music getMusicEntityByPublicId(String publicId) {
        return musicRepository.findByPublicId(publicId)
                .orElseThrow(() -> new ServiceException(ServiceErrorCode.MUSIC_NOT_FOUND));
    }

    public List<MusicDto> getMusics(Date date) {
        List<MusicSimpleDto> musics = musicQueryRepository.findMusics(date);
        return musics
                .stream()
                .map(MusicDtoMapper::toMusicDto)
                .toList();
    }

    public List<MusicDto> searchMusics(SearchMusicCondition condition, Date date) {
        List<MusicSimpleDto> musics = musicQueryRepository.searchMusics(condition.getField(), condition.getKeyword(), date);
        return musics
                .stream()
                .map(MusicDtoMapper::toMusicDto)
                .toList();
    }

    public MusicDto getMusicById(String publicId) {
        return musicRepository
                .findByPublicId(publicId)
                .map(MusicDtoMapper::toMusicDto)
                .orElseThrow(() -> new ServiceException(ServiceErrorCode.MUSIC_NOT_FOUND));
    }

    /**
     * Don't use it directly, Please use it through MusicFileFacade
     * @param userId
     * @param musicDto
     * @param uploadResult
     * @return
     */
    @Transactional
    public IdDto createMusic(Long userId, CreateMusicDto musicDto, MusicUploadResult uploadResult) {
        Optional<User> user = userRepository.findById(userId);
        if(user.isEmpty()) {
            throw new ServiceException(ServiceErrorCode.USER_NOT_FOUND);
        }

        Music music = new Music(
                musicDto.getTitle(),
                uploadResult.getStoreName(),
                user.get(),
                uploadResult.getPlaytime()
        );
        Music savedMusic = musicRepository.save(music);
        return new IdDto(savedMusic.getPublicId());
    }

    /**
     * Don't use it directly, please use it through MusicFileFacade
     * @param userId
     * @param publicId
     * @return deleted music's file name
     */
    @Transactional
    public String deleteMusic(Long userId, String publicId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            throw new ServiceException(ServiceErrorCode.USER_NOT_FOUND);
        }

        Optional<Music> music = musicRepository.findByPublicId(publicId);
        if (music.isEmpty()) {
            throw new ServiceException(ServiceErrorCode.MUSIC_NOT_FOUND);
        }

        User singer = music.get().getUser();
        if (!singer.getId().equals(userId)) {
            throw new ServiceException(ServiceErrorCode.MUSIC_PERMISSION_DENIED);
        }

        String storeName = music.get().getStoreName();
        musicRepository.deleteById(music.get().getId());
        return storeName;
    }
}
