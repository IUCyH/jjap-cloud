package com.iucyh.jjapcloud.service.music;

import com.iucyh.jjapcloud.common.exception.ServiceException;
import com.iucyh.jjapcloud.common.exception.errorcode.ServiceErrorCode;
import com.iucyh.jjapcloud.common.util.FileManager;
import com.iucyh.jjapcloud.common.wrapper.LimitedInputStream;
import com.iucyh.jjapcloud.domain.music.Music;
import com.iucyh.jjapcloud.dto.music.RangeDto;
import com.iucyh.jjapcloud.repository.music.MusicRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MusicFileServiceImpl implements MusicStreamService {

    private final MusicRepository musicRepository;
    private final FileManager fileManager;

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
    public InputStreamResource streamMusic(File file, RangeDto range) {
        LimitedInputStream limitedInputStream = fileManager.getLimitedInputStream(file, range.getStart(), range.getEnd());
        return new InputStreamResource(limitedInputStream);
    }
}
