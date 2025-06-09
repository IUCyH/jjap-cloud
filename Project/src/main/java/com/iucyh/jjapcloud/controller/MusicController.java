package com.iucyh.jjapcloud.controller;

import com.iucyh.jjapcloud.common.annotation.loginuser.LoginUserId;
import com.iucyh.jjapcloud.dto.IdDto;
import com.iucyh.jjapcloud.dto.ResponseDto;
import com.iucyh.jjapcloud.dto.music.CreateMusicDto;
import com.iucyh.jjapcloud.dto.music.MusicDto;
import com.iucyh.jjapcloud.dto.music.RangeDto;
import com.iucyh.jjapcloud.dto.music.SearchMusicCondition;
import com.iucyh.jjapcloud.facade.music.file.MusicFileFacade;
import com.iucyh.jjapcloud.facade.music.stream.MusicStreamFacade;
import com.iucyh.jjapcloud.facade.music.stream.MusicStreamResult;
import com.iucyh.jjapcloud.service.music.MusicService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/musics")
@RequiredArgsConstructor
public class MusicController {

    private final MusicService musicService;
    private final MusicFileFacade musicFileFacade;
    private final MusicStreamFacade musicStreamFacade;

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

    @GetMapping("/{publicId}")
    public ResponseDto<MusicDto> getMusicById(@PathVariable String publicId) {
        return ResponseDto
                .success("Get music success", musicService.getMusicById(publicId));
    }

    @GetMapping("/stream/{publicId}")
    public ResponseEntity<InputStreamResource> streamMusic(
            @PathVariable String publicId,
            @RequestHeader(name = "Range", required = false) String rangeHeader
    ) {
        MusicStreamResult result = musicStreamFacade.stream(publicId, rangeHeader);
        RangeDto range = result.getRange();

        return ResponseEntity
                .status(rangeHeader != null ? HttpStatus.PARTIAL_CONTENT : HttpStatus.OK)
                .header("Content-Type", "audio/mpeg")
                .header("Accept-Ranges", "bytes")
                .header("Content-Length", String.valueOf(range.getEnd() - range.getStart() + 1))
                .header("Content-Range", String.format("bytes %d-%d/%d", range.getStart(), range.getEnd(), result.getFileLength()))
                .body(result.getResource());
    }

    @PostMapping
    public ResponseDto<IdDto> createMusic(@LoginUserId Long userId, @ModelAttribute CreateMusicDto music) {
        IdDto id = musicFileFacade.uploadMusic(userId, music);
        return ResponseDto
                .success("Create music success", id);
    }

    @DeleteMapping("/{publicId}")
    public ResponseDto<Void> deleteMusic(@LoginUserId Long userId, @PathVariable String publicId) {
        musicFileFacade.deleteMusic(userId, publicId);
        return ResponseDto
                .success("Delete music success", null);
    }

    private Date getMaxDate() {
        LocalDateTime ldt = LocalDateTime.of(9999, 12, 31, 11, 59, 59);
        return Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant());
    }
}
