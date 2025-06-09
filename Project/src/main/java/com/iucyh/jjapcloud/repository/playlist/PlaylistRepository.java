package com.iucyh.jjapcloud.repository.playlist;

import com.iucyh.jjapcloud.domain.playlist.Playlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PlaylistRepository extends JpaRepository<Playlist, Long> {
    @Query("select pl.id from Playlist pl where pl.publicId = :publicId")
    Long findIdByPublicId(@Param("publicId") String publicId);
}