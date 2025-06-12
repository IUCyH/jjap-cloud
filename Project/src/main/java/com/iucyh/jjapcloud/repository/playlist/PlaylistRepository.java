package com.iucyh.jjapcloud.repository.playlist;

import com.iucyh.jjapcloud.domain.playlist.Playlist;
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

    Optional<Playlist> findByUserIdAndPublicId(@Param("userId") Long userId, @Param("publicId") String publicId);

    Optional<List<Playlist>> findByUserId(Long userId);

    @Modifying(clearAutomatically = true)
    @Query("delete from Playlist where user.id = :userId and publicId = :publicId")
    void deleteByPublicId(@Param("userId") Long userId, @Param("publicId") String publicId);
}