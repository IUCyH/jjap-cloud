package com.iucyh.jjapcloud.web.controller;

import com.iucyh.jjapcloud.web.dto.RequestSuccessDto;
import com.iucyh.jjapcloud.web.dto.music.CreateMusicDto;
import com.iucyh.jjapcloud.web.dto.music.MusicDto;
import com.iucyh.jjapcloud.web.service.music.MusicService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.ModelAttribute;

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

    @PostMapping
    public int createMusic(@ModelAttribute CreateMusicDto music) throws IOException {
        return musicService.createMusic(music);
    }

    @DeleteMapping("/{id}")
    public RequestSuccessDto deleteMusic(@PathVariable int id) {
        return musicService.deleteMusic(id);
    }
}
