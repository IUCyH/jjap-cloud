package com.iucyh.jjapcloud.repository.music;

import com.iucyh.jjapcloud.domain.music.Music;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface MusicRepository extends JpaRepository<Music, Long> {

    Optional<Music> findByPublicId(String publicId);

    @Query("select m.id from Music m where m.publicId = :publicId")
    Optional<Long> findIdByPublicId(String publicId);
}
