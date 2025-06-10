package com.iucyh.jjapcloud.repository.playlist;

import com.iucyh.jjapcloud.domain.playlist.Playlist;
import com.iucyh.jjapcloud.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PlaylistRepository extends JpaRepository<Playlist, Long> {
    Optional<Playlist> findByPublicId(String publicId);

    Optional<PlaylistInfo> findByUserIdAndPublicId(Long userId, String publicId);

    @Query("select pl.id from Playlist pl where pl.publicId = :publicId")
    Optional<Long> findIdByPublicId(@Param("publicId") String publicId);

    @Query("select pl.id from Playlist pl where pl.user.id = :userId and pl.publicId = :publicId")
    Optional<Long> findIdByUserIdAndPublicId(@Param("userId") Long userId, @Param("publicId") String publicId);

    Optional<List<Playlist>> findByUserId(Long userId);

    @Modifying(clearAutomatically = true)
    @Query(value = "update playlists pl set pl.item_count = pl.item_count + 1 where pl.id = :id returning item_count", nativeQuery = true)
    int increaseItemCount(@Param("id") Long id);
}