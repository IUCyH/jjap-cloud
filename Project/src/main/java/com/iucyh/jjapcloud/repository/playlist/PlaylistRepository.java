package com.iucyh.jjapcloud.repository.playlist;

import com.iucyh.jjapcloud.domain.playlist.Playlist;
import com.iucyh.jjapcloud.domain.user.User;
import com.iucyh.jjapcloud.repository.playlist.projection.PlaylistIdAndMusicIdProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PlaylistRepository extends JpaRepository<Playlist, Long> {
    Optional<Playlist> findByPublicId(String publicId);

    @Query("select pl.id from Playlist pl where pl.publicId = :publicId")
    Optional<Long> findIdByPublicId(String publicId);

    @Query("select p.itemCount from Playlist p where p.id = :id")
    Optional<Integer> findItemCountById(Long id);

    @Query("select pl.id from Playlist pl where pl.user.id = :userId and pl.publicId = :publicId")
    Optional<Long> findIdByUserIdAndPublicId(@Param("userId") Long userId, @Param("publicId") String publicId);

    Optional<List<Playlist>> findByUserId(Long userId);

    @Modifying(clearAutomatically = true)
    @Query(value = "update Playlist pl set pl.itemCount = pl.itemCount + 1 where pl.id = :id")
    void increaseItemCountWithoutReturning(Long id);

    @Modifying(clearAutomatically = true)
    @Query(value = "update playlists pl set pl.item_count = pl.item_count + 1 where pl.id = :id returning pl.item_count", nativeQuery = true)
    Integer increaseItemCount(Long id);

    @Modifying(clearAutomatically = true)
    @Query("update Playlist pl set pl.itemCount = (case when pl.itemCount - 1 < 0 then 0 else pl.itemCount - 1 end) where pl.id = :id and pl.user.id = :userId")
    void decreaseItemCount(@Param("id") Long id, @Param("userId") Long userId);

    Long user(User user);
}