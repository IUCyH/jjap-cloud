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

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
@Slf4j
public class MusicQueryRepositoryTest {

    @Autowired
    private MusicRepository musicRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MusicQueryRepository musicQueryRepository;

    @Test
    @DisplayName("음악이름으로 검색 -> 검색 결과 있음")
    void searchWithMusicName() {
        saveDummyData();

        userRepository.flush();
        musicRepository.flush();

        LocalDateTime ldt = LocalDateTime.of(9999, 12, 31, 11, 59, 59);
        Date maxDate = Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant());

        // 'test' 가 이름으로 포함된 음악 전체 조회
        List<MusicSimpleDto> results = musicQueryRepository.searchMusics(MusicSearchField.MUSIC_NAME, "test", maxDate);
        assertThat(results).isNotEmpty();
        assertThat(results.stream().map(MusicSimpleDto::getName))
                .containsExactly(
                        "testc3",
                        "test2",
                        "test1"
                );

        // 'a' 가 이름으로 포함된 음악만 조회
        List<MusicSimpleDto> results2 = musicQueryRepository.searchMusics(MusicSearchField.MUSIC_NAME, "a", maxDate);
        assertThat(results2).isNotEmpty();
        assertThat(results2.stream().map(MusicSimpleDto::getName))
                .containsExactly("aes4");
    }

    @Test
    @DisplayName("유저 이름으로 검색 -> 검색결과 있음")
    void searchWithUserName() {
        saveDummyData();

        userRepository.flush();
        musicRepository.flush();

        LocalDateTime ldt = LocalDateTime.of(9999, 12, 31, 11, 59, 59);
        Date maxDate = Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant());

        // 'testSinger1' 이름의 유저가 올린 모든 음악 조회
        List<MusicSimpleDto> results = musicQueryRepository.searchMusics(MusicSearchField.SINGER_NAME, "testSinger1", maxDate);
        assertThat(results).isNotEmpty();
        assertThat(results.stream().map(MusicSimpleDto::getName))
                .containsExactly("test1");
    }

    @Test
    @DisplayName("유저 이름으로 검색 -> 검색결과 없음")
    void searchWithUserNameFail() {
        saveDummyData();

        userRepository.flush();
        musicRepository.flush();

        LocalDateTime ldt = LocalDateTime.of(9999, 12, 31, 11, 59, 59);
        Date maxDate = Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant());

        // 'testSinger' 이름의 유저가 올린 모든 음악 조회 (이름에 해당하는 유저가 없으므로 검색결과는 비어있어야 함)
        List<MusicSimpleDto> results = musicQueryRepository.searchMusics(MusicSearchField.SINGER_NAME, "testSinger", maxDate);
        assertThat(results).isEmpty();
    }

    private void saveDummyData() {
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
                "testc3",
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
                "aes4",
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
    }
}
