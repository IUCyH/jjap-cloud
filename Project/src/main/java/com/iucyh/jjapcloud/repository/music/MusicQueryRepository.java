package com.iucyh.jjapcloud.repository.music;

import com.iucyh.jjapcloud.domain.music.QMusic;
import com.iucyh.jjapcloud.dto.music.query.MusicSimpleDto;
import com.iucyh.jjapcloud.dto.music.query.QMusicSimpleDto;
import com.iucyh.jjapcloud.dto.user.query.QJoinUserDto;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Repository
public class MusicQueryRepository {

    private final JPAQueryFactory query;

    public MusicQueryRepository(EntityManager em) {
        this.query = new JPAQueryFactory(em);
    }

    public List<MusicSimpleDto> findMusics(Date date) {
        LocalDateTime localDateTime = getDate(date);
        QMusic music = QMusic.music;

        return query.select(
                        new QMusicSimpleDto(
                                music.publicId,
                                music.title,
                                music.playTime,
                                new QJoinUserDto(
                                        music.user.publicId,
                                        music.user.nickname
                                )
                        )
                )
                .from(music)
                .where(music.createdAt.lt(localDateTime))
                .orderBy(music.createdAt.desc())
                .limit(20)
                .fetch();
    }

    public List<MusicSimpleDto> searchMusics(MusicSearchField field, String keyword, Date date) {
        QMusic music = QMusic.music;
        LocalDateTime localDateTime = getDate(date);
        BooleanExpression keywordExpression = switch (field) {
            case MUSIC_NAME -> QMusic.music.title.like("%" + keyword + "%");
            case SINGER_NAME -> QMusic.music.user.nickname.like(keyword);
        };

        return query.select(
                        new QMusicSimpleDto(
                                music.publicId,
                                music.title,
                                music.playTime,
                                new QJoinUserDto(
                                        music.user.publicId,
                                        music.user.nickname
                                )
                        )
                )
                .from(QMusic.music)
                .where(
                        QMusic.music.createdAt.lt(localDateTime),
                        keywordExpression
                )
                .orderBy(QMusic.music.createdAt.desc())
                .limit(20)
                .fetch();
    }

    private LocalDateTime getDate(Date date) {
        return date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }
}
