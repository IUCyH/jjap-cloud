package com.iucyh.jjapcloud.web.service.music;

import com.iucyh.jjapcloud.common.exception.ServiceException;
import com.iucyh.jjapcloud.common.exception.errorcode.ServiceErrorCode;
import com.iucyh.jjapcloud.domain.music.Music;
import com.iucyh.jjapcloud.domain.music.repository.MusicRepository;
import com.iucyh.jjapcloud.web.dto.RequestSuccessDto;
import com.iucyh.jjapcloud.web.dto.music.CreateMusicDto;
import com.iucyh.jjapcloud.web.dto.music.MusicDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MusicServiceImpl implements MusicService {

    private final MusicRepository musicRepository;

    @Override
    public List<MusicDto> getMusics(Date date) {
        List<Music> musics = musicRepository.findMusics(date);
        return musics
                .stream()
                .map(MusicDto::from)
                .toList();
    }

    @Override
    public MusicDto getMusicById(int id) {
        return musicRepository
                .findById(id)
                .map(MusicDto::from)
                .orElseThrow(() -> new ServiceException(ServiceErrorCode.MUSIC_NOT_FOUND));
    }

    @Override
    public int createMusic(CreateMusicDto music) {
        Music newMusic = new Music();
        newMusic.setName(music.getName());
        newMusic.setSinger(music.getSinger());
        newMusic.setRuntime(music.getRuntime());

        return musicRepository.create(newMusic);
    }

    @Override
    public RequestSuccessDto deleteMusic(int id) {
        musicRepository.delete(id);
        return new RequestSuccessDto("Music Delete Success");
    }
}
