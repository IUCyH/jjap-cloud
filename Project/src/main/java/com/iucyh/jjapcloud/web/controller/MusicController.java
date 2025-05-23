package com.iucyh.jjapcloud.web.controller;

import com.iucyh.jjapcloud.common.wrapper.LimitedInputStream;
import com.iucyh.jjapcloud.web.dto.IdDto;
import com.iucyh.jjapcloud.web.dto.RequestSuccessDto;
import com.iucyh.jjapcloud.web.dto.music.CreateMusicDto;
import com.iucyh.jjapcloud.web.dto.music.MusicDto;
import com.iucyh.jjapcloud.web.dto.music.RangeDto;
import com.iucyh.jjapcloud.web.service.music.MusicService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/musics")
@RequiredArgsConstructor
public class MusicController {

    private final MusicService musicService;

    @GetMapping
    public List<MusicDto> getMusics(@RequestParam(required = false) Date date) {
        return musicService.getMusics(date);
    }

    @GetMapping("/{id}")
    public MusicDto getMusicById(@PathVariable int id) {
        return musicService.getMusicById(id);
    }

    @GetMapping("/stream/{id}")
    public ResponseEntity<InputStreamResource> streamMusic(
            @PathVariable int id,
            @RequestParam(name = "Range", required = false) String rangeHeader
    ) throws IOException {
        String fileName = musicService.getMusicStoreName(id);
        File file = musicService.getFile(fileName);
        RangeDto range = musicService.getRange(file, rangeHeader);
        InputStreamResource resource = musicService.streamMusic(file, range);

        return ResponseEntity
                .status(rangeHeader != null ? HttpStatus.PARTIAL_CONTENT : HttpStatus.OK)
                .header("Content-Type", "audio/mpeg")
                .header("Accept-Ranges", "bytes")
                .header("Content-Length", String.valueOf(range.getEnd() - range.getStart() + 1))
                .header("Content-Range", String.format("bytes %d-%d/%d", range.getStart(), range.getEnd(), file.length()))
                .body(resource);
    }

    @PostMapping
    public IdDto createMusic(@ModelAttribute CreateMusicDto music) throws IOException {
        return musicService.createMusic(music);
    }

    @DeleteMapping("/{id}")
    public RequestSuccessDto deleteMusic(@PathVariable int id) {
        return musicService.deleteMusic(id);
    }
}
