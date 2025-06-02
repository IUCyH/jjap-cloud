package com.iucyh.jjapcloud.repository.music;

import com.iucyh.jjapcloud.domain.music.Music;
import com.iucyh.jjapcloud.domain.music.QMusic;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Repository
public class MusicPagingRepository {

    private final JPAQueryFactory query;

    public MusicPagingRepository(EntityManager em) {
        this.query = new JPAQueryFactory(em);
    }

    public List<Music> findMusics(Date date) {
        LocalDateTime localDateTime = date.toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDateTime();

        return query.select(
                        Projections.fields(
                                Music.class,
                                QMusic.music.id,
                                QMusic.music.originalName,
                                QMusic.music.singer,
                                QMusic.music.playTime
                        ))
                .from(QMusic.music)
                .where(QMusic.music.createTime.lt(localDateTime))
                .orderBy(QMusic.music.createTime.desc())
                .limit(20)
                .fetch();
    }
}
