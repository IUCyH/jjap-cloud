package com.iucyh.jjapcloud.web.service.music;

import com.iucyh.jjapcloud.common.exception.ServiceException;
import com.iucyh.jjapcloud.common.exception.errorcode.ServiceErrorCode;
import com.iucyh.jjapcloud.common.util.FileManager;
import com.iucyh.jjapcloud.common.wrapper.LimitedInputStream;
import com.iucyh.jjapcloud.domain.music.Music;
import com.iucyh.jjapcloud.domain.music.repository.MusicRepository;
import com.iucyh.jjapcloud.web.dto.IdDto;
import com.iucyh.jjapcloud.web.dto.RequestSuccessDto;
import com.iucyh.jjapcloud.web.dto.music.CreateMusicDto;
import com.iucyh.jjapcloud.web.dto.music.MusicDto;
import com.iucyh.jjapcloud.web.dto.music.RangeDto;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MusicServiceImpl implements MusicService {

    private final MusicRepository musicRepository;
    private final FileManager fileManager;

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
    public String getMusicStoreName(int id) {
        Optional<Music> music = musicRepository.findById(id);
        if(music.isEmpty()) {
            throw new ServiceException(ServiceErrorCode.MUSIC_NOT_FOUND);
        }

        return music.get().getStoreName();
    }

    @Override
    public File getFile(String fileName) {
        File file = fileManager.getFile("music", fileName);
        if(!file.exists()) {
            throw new ServiceException(ServiceErrorCode.MUSIC_NOT_FOUND);
        }

        return file;
    }

    @Override
    public RangeDto getRange(File file, String rangeHeader) {
        long fileLength = file.length() - 1;
        long start = 0;
        long end = fileLength;

        if(rangeHeader != null && rangeHeader.startsWith("bytes=")) {
            // RANGE bytes=1-100
            String[] splitResult = rangeHeader.replace("bytes=", "").split("-");

            start = Long.parseLong(splitResult[0]);
            if(splitResult.length > 1 && !splitResult[1].isEmpty()) {
                end = Long.parseLong(splitResult[1]);
                if(end >= fileLength) {
                    end = fileLength;
                }
            }
        }

        return new RangeDto(start, end);
    }

    @Override
    public InputStreamResource streamMusic(File file, RangeDto range) throws IOException {
        LimitedInputStream limitedInputStream = fileManager.getLimitedInputStream(file, range.getStart(), range.getEnd());
        return new InputStreamResource(limitedInputStream);
    }

    @Override
    public IdDto createMusic(CreateMusicDto music) throws IOException {
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

        return new IdDto(musicRepository.create(newMusic));
    }

    @Override
    public RequestSuccessDto deleteMusic(int id) {
        musicRepository.delete(id);
        return new RequestSuccessDto("Music Delete Success");
    }
}
