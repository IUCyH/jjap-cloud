package com.iucyh.jjapcloud.web.controller;

import com.iucyh.jjapcloud.web.dto.music.MusicDto;
import com.iucyh.jjapcloud.web.service.music.MusicService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

//import java.util.List;
//
////@Controller
////@RequestMapping("/music-view")
////@RequiredArgsConstructor
////public class MusicViewController {
////
////    private final MusicService musicService;
////
////    @GetMapping("/upload")
////    public String showUploadForm() {
////        return "music-upload";
////    }
////
////    @GetMapping("/stream")
////    public String showStreamPage(Model model) {
////        List<MusicDto> musics = musicService.getMusics(null);
////        model.addAttribute("musics", musics);
////        return "music-stream";
////    }
//}
