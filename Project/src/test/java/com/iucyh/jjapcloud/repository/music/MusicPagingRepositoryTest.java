package com.iucyh.jjapcloud.repository.music;

import com.iucyh.jjapcloud.domain.music.Music;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.DateFormatter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@Transactional
@SpringBootTest
@Slf4j
class MusicPagingRepositoryTest {

    @Autowired
    private MusicRepository musicRepository;
    @Autowired
    private MusicPagingRepository musicPagingRepository;
    @Autowired
    private EntityManager em;

    @Test
    @DisplayName("페이징 성공")
    void paging() {
        Music music1 = new Music();
        music1.setOriginalName("test1");
        music1.setSinger("testSinger1");

        Music music2 = new Music();
        music2.setOriginalName("test2");
        music2.setSinger("testSinger2");

        Music music3 = new Music();
        music3.setOriginalName("test3");
        music3.setSinger("testSinger3");

        Music music4 = new Music();
        music4.setOriginalName("test4");
        music4.setSinger("testSinger4");

        musicRepository.save(music1);
        musicRepository.save(music2);
        musicRepository.save(music3);
        musicRepository.save(music4);

        LocalDateTime ldt = LocalDateTime.of(9999, 12, 31, 11, 59, 59);
        Date maxDate = Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant());;

        // 전체 음악 조회
        List<Music> musics = musicPagingRepository.findMusics(maxDate);
        musics.forEach(m -> log.info("music: {}", m.getOriginalName()));

        assertThat(musics).isNotEmpty();
        assertThat(musics.stream().map(Music::getOriginalName))
                .containsExactly(
                        music4.getOriginalName(),
                        music3.getOriginalName(),
                        music2.getOriginalName(),
                        music1.getOriginalName()
                );

        // music2 이전에 등록된 음악만 조회
        Date music2Date = Date.from(music2.getCreateTime().atZone(ZoneId.systemDefault()).toInstant());
        List<Music> musics2 = musicPagingRepository.findMusics(music2Date);

        assertThat(musics2).isNotEmpty();
        assertThat(musics2.stream().map(Music::getOriginalName))
                .containsExactly(music1.getOriginalName());
    }
}
