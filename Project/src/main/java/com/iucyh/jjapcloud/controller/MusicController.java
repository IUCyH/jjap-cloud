package com.iucyh.jjapcloud.controller;

import com.iucyh.jjapcloud.dto.IdDto;
import com.iucyh.jjapcloud.dto.ResponseDto;
import com.iucyh.jjapcloud.dto.music.CreateMusicDto;
import com.iucyh.jjapcloud.dto.music.MusicDto;
import com.iucyh.jjapcloud.dto.music.RangeDto;
import com.iucyh.jjapcloud.dto.music.SearchMusicCondition;
import com.iucyh.jjapcloud.service.MusicService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/musics")
@RequiredArgsConstructor
public class MusicController {

    private final MusicService musicService;

    @GetMapping
    public ResponseDto<List<MusicDto>> getMusics(@RequestParam(required = false) Date date) {
        if(date == null) {
            date = getMaxDate();
        }
        return ResponseDto
                .success("Get musics success", musicService.getMusics(date));
    }

    @GetMapping("/search")
    public ResponseDto<List<MusicDto>> searchMusics(@ModelAttribute SearchMusicCondition condition, @RequestParam(required = false) Date date) {
        if(date == null) {
            date = getMaxDate();
        }
        return ResponseDto
                .success("Search musics success", musicService.searchMusics(condition, date));
    }

    @GetMapping("/{id}")
    public ResponseDto<MusicDto> getMusicById(@PathVariable int id) {
        return ResponseDto
                .success("Get music success", musicService.getMusicById(id));
    }

    @GetMapping("/stream/{id}")
    public ResponseEntity<InputStreamResource> streamMusic(
            @PathVariable int id,
            @RequestParam(name = "Range", required = false) String rangeHeader
    ) throws IOException {
        String storeName = musicService.getMusicStoreName(id);
        File file = musicService.getFile(storeName);
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
    public ResponseDto<IdDto> createMusic(@ModelAttribute CreateMusicDto music) throws IOException {
        IdDto id = musicService.createMusic(music);
        return ResponseDto
                .success("Create music success", id);
    }

    @DeleteMapping("/{id}")
    public ResponseDto<Void> deleteMusic(@PathVariable int id) {
        musicService.deleteMusic(id);
        return ResponseDto
                .success("Delete music success", null);
    }

    private Date getMaxDate() {
        LocalDateTime ldt = LocalDateTime.of(9999, 12, 31, 11, 59, 59);
        return Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant());
    }
}
