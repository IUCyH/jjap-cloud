package com.iucyh.jjapcloud.facade.music.stream;

import com.iucyh.jjapcloud.dto.music.RangeDto;
import com.iucyh.jjapcloud.service.music.file.MusicStreamService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
@RequiredArgsConstructor
public class MusicStreamFacade {

    private final MusicStreamService musicStreamService;

    public MusicStreamResult stream(String publicId, String rangeHeader) {
        String storeName = musicStreamService.getMusicStoreName(publicId);
        File file = musicStreamService.getFile(storeName);
        RangeDto range = musicStreamService.getRange(file, rangeHeader);
        InputStreamResource resource = musicStreamService.streamMusic(file, range);

        return new MusicStreamResult(resource, range, file.length());
    }
}
