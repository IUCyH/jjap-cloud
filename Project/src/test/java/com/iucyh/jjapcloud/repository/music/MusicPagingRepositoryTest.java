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
        Music music1 = new Music();
        User user1 = new User();

        user1.setNickname("testSinger1");
        user1.setEmail("abc@abc.com");
        user1.setPassword("abc");

        music1.setName("test1");
        music1.setUser(user1);

        Music music2 = new Music();

        User user2 = new User();
        user2.setNickname("testSinger2");
        user2.setEmail("abc@abc.com");
        user2.setPassword("abc");

        music2.setName("test2");
        music2.setUser(user2);

        Music music3 = new Music();
        User user3 = new User();

        user3.setNickname("testSinger3");
        user3.setEmail("abc@abc.com");
        user3.setPassword("abc");

        music3.setName("test3");
        music3.setUser(user3);

        Music music4 = new Music();
        User user4 = new User();

        user4.setNickname("testSinger4");
        user4.setEmail("abc@abc.com");
        user4.setPassword("abc");

        music4.setName("test4");
        music4.setUser(user4);

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
        musics.forEach(m -> log.info("music: {}", m.getName()));

        assertThat(musics).isNotEmpty();
        assertThat(musics.stream().map(MusicSimpleDto::getName))
                .containsExactly(
                        music4.getName(),
                        music3.getName(),
                        music2.getName(),
                        music1.getName()
                );

        // music2 이전에 등록된 음악만 조회
        Date music2Date = Date.from(music2.getCreateTime().atZone(ZoneId.systemDefault()).toInstant());
        List<MusicSimpleDto> musics2 = musicPagingRepository.findMusics(music2Date);

        assertThat(musics2).isNotEmpty();
        assertThat(musics2.stream().map(MusicSimpleDto::getName))
                .containsExactly(music1.getName());
    }
}
