package com.iucyh.jjapcloud.repository.music;

import com.iucyh.jjapcloud.domain.music.Music;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class MusicRepositoryMemoryImpl implements MusicRepository {

    private static final Map<Integer, Music> musicStore = new HashMap<>();
    private static Integer sequence = 0;

    @Override
    public List<Music> findMusics(Date date) {
        return musicStore
                .values().stream().toList();
    }

    @Override
    public Optional<Music> findById(int id) {
        return Optional.ofNullable(musicStore.get(id));
    }

    @Override
    public int create(Music music) {
        music.setId(++sequence);
        musicStore.put(music.getId(), music);
        return music.getId();
    }

    @Override
    public void delete(int id) {
        musicStore.remove(id);
    }
}
