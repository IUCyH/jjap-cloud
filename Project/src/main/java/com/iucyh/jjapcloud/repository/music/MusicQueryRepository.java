package com.iucyh.jjapcloud.repository.music;

import com.iucyh.jjapcloud.domain.music.Music;
import com.iucyh.jjapcloud.domain.music.QMusic;
import com.querydsl.core.types.Projections;
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

    public List<Music> findMusics(Date date) {
        LocalDateTime localDateTime = getDate(date);

        return query.select(
                        Projections.fields(
                                Music.class,
                                QMusic.music.id,
                                QMusic.music.name,
                                QMusic.music.singer,
                                QMusic.music.playTime
                        ))
                .from(QMusic.music)
                .where(QMusic.music.createTime.lt(localDateTime))
                .orderBy(QMusic.music.createTime.desc())
                .limit(20)
                .fetch();
    }

    public List<Music> searchMusics(MusicSearchField field, String keyword, Date date) {
        LocalDateTime localDateTime = getDate(date);
        BooleanExpression keywordExpression = switch (field) {
            case MUSIC_NAME -> QMusic.music.name.like("%" + keyword + "%");
            case SINGER_NAME -> QMusic.music.singer.like("%" + keyword + "%");
        };

        return query
                .select(
                        Projections.fields(
                                Music.class,
                                QMusic.music.id,
                                QMusic.music.name,
                                QMusic.music.singer,
                                QMusic.music.playTime
                        ))
                .from(QMusic.music)
                .where(
                        QMusic.music.createTime.lt(localDateTime),
                        keywordExpression
                )
                .orderBy(QMusic.music.createTime.desc())
                .limit(20)
                .fetch();
    }

    private LocalDateTime getDate(Date date) {
        return date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }
}
