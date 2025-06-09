package com.iucyh.jjapcloud.repository.playlist;

import com.iucyh.jjapcloud.domain.music.QMusic;
import com.iucyh.jjapcloud.domain.playlist.QPlaylistItem;
import com.iucyh.jjapcloud.domain.user.QUser;
import com.iucyh.jjapcloud.dto.music.query.QJoinMusicDto;
import com.iucyh.jjapcloud.dto.playlist.query.PlaylistItemSimpleDto;
import com.iucyh.jjapcloud.dto.playlist.query.QPlaylistItemSimpleDto;
import com.iucyh.jjapcloud.dto.user.query.QJoinUserDto;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class PlaylistItemQueryRepository {

    private final JPAQueryFactory query;
    private final PlaylistRepository playlistRepository;

    public PlaylistItemQueryRepository(EntityManager em, PlaylistRepository playlistRepository) {
        this.query = new JPAQueryFactory(em);
        this.playlistRepository = playlistRepository;
    }

    public List<PlaylistItemSimpleDto> findPlaylistItems(String playlistPublicId) {
        Long id = playlistRepository.findIdByPublicId(playlistPublicId);
        if(id == null) {
            return new ArrayList<>();
        }

        QPlaylistItem playlistItem = QPlaylistItem.playlistItem;
        QMusic music = QMusic.music;
        QUser user = QUser.user;

        return query
                .select(
                        new QPlaylistItemSimpleDto(
                                playlistItem.id,
                                playlistItem.position,
                                new QJoinMusicDto(
                                        music.publicId,
                                        music.name,
                                        music.playTime,
                                        music.createdAt,
                                        new QJoinUserDto(
                                                user.publicId,
                                                user.nickname
                                        )
                                )
                        )
                )
                .from(playlistItem)
                .join(playlistItem.music, music)
                .join(music.user, user)
                .where(playlistItem.playlist.id.eq(id))
                .orderBy(playlistItem.position.asc())
                .fetch();
    }
}
