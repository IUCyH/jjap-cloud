package com.iucyh.jjapcloud.repository.playlist;

import com.iucyh.jjapcloud.domain.playlist.Playlist;
import com.iucyh.jjapcloud.domain.playlist.PlaylistItem;
import com.iucyh.jjapcloud.repository.playlist.projection.PlaylistIdAndMusicIdProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PlaylistItemRepository extends JpaRepository<PlaylistItem, Long> {
    @Query("""
            select p
            from PlaylistItem pl
            join pl.playlist p
            join pl.music m
            where p.publicId = :playlistPublicId
                  and m.publicId = :musicPublicId
                  and p.user.id = :userId
        """)
    Optional<Playlist> findPlaylist(@Param("userId") Long userId, @Param("playlistPublicId") String playlistPublicId, @Param("musicPublicId") String musicPublicId);

    @Query(value = "select exists (select 1 from playlist_items pli join playlists p on pli.playlist_id = p.id join musics m on pli.music_id = m.id where p.public_id = :playlistPublicId and m.public_id = :musicPublicId)", nativeQuery = true)
    Boolean isMusicExistsInPlaylist(@Param("playlistPublicId") String playlistPublicId, @Param("musicPublicId") String musicPublicId);
}
