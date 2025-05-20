package com.iucyh.jjapcloud.domain.music.repository;

import com.iucyh.jjapcloud.domain.music.Music;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface MusicRepository {

    List<Music> findMusics(Date date);
    Optional<Music> findById(int id);
    int create(Music music);
    void delete(int id);
}
