package com.iucyh.jjapcloud.repository.music;

import com.iucyh.jjapcloud.domain.music.Music;
import com.iucyh.jjapcloud.domain.user.User;
import com.iucyh.jjapcloud.dto.music.query.MusicSimpleDto;
import com.iucyh.jjapcloud.repository.user.UserRepository;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

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
    private UserRepository userRepository;
    @Autowired
    private MusicQueryRepository musicPagingRepository;
    @Autowired
    private EntityManager em;

    @Test
    @DisplayName("페이징 성공")
    void paging() {
        User user1 = new User(
                "testSinger1",
                "abc@abc.com",
                "abc"
        );

        Music music1 = new Music(
                "test1",
                "storename",
                user1,
                1000L
        );


        User user2 = new User(
                "testSinger2",
                "abc@abc.com",
                "abc"
        );

        Music music2 = new Music(
                "test2",
                "storename",
                user2,
                1000L
        );

        User user3 = new User(
                "testSinger3",
                "abc@abc.com",
                "abc"
        );

        Music music3 = new Music(
                "test3",
                "storename",
                user3,
                1000L
        );

        User user4 = new User(
                "testSinger4",
                "abc@abc.com",
                "abc"
        );

        Music music4 = new Music(
                "test4",
                "storename",
                user4,
                1000L
        );

        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);
        userRepository.save(user4);

        musicRepository.save(music1);
        musicRepository.save(music2);
        musicRepository.save(music3);
        musicRepository.save(music4);

        userRepository.flush();
        musicRepository.flush();

        LocalDateTime ldt = LocalDateTime.of(9999, 12, 31, 11, 59, 59);
        Date maxDate = Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant());

        // 전체 음악 조회
        List<MusicSimpleDto> musics = musicPagingRepository.findMusics(maxDate);
        musics.forEach(m -> log.info("music: {}", m.getTitle()));

        assertThat(musics).isNotEmpty();
        assertThat(musics.stream().map(MusicSimpleDto::getTitle))
                .containsExactly(
                        music4.getTitle(),
                        music3.getTitle(),
                        music2.getTitle(),
                        music1.getTitle()
                );

        // music2 이전에 등록된 음악만 조회
        Date music2Date = Date.from(music2.getCreatedAt().atZone(ZoneId.systemDefault()).toInstant());
        List<MusicSimpleDto> musics2 = musicPagingRepository.findMusics(music2Date);

        assertThat(musics2).isNotEmpty();
        assertThat(musics2.stream().map(MusicSimpleDto::getTitle))
                .containsExactly(music1.getTitle());
    }
}
