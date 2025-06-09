package com.iucyh.jjapcloud.repository.playlist;

import com.iucyh.jjapcloud.domain.music.Music;
import com.iucyh.jjapcloud.domain.playlist.Playlist;
import com.iucyh.jjapcloud.domain.playlist.PlaylistItem;
import com.iucyh.jjapcloud.domain.user.User;
import com.iucyh.jjapcloud.dto.playlist.query.PlaylistItemSimpleDto;
import com.iucyh.jjapcloud.repository.music.MusicRepository;
import com.iucyh.jjapcloud.repository.user.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

@Transactional
@SpringBootTest
@Slf4j
public class PlaylistItemQueryRepositoryTest {

    @Autowired
    private MusicRepository musicRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PlaylistRepository playlistRepository;

    @Autowired
    private PlaylistItemRepository playlistItemRepository;

    @Autowired
    private PlaylistItemQueryRepository playlistItemQueryRepository;

    @Test
    @DisplayName("플레이 리스트 조회 - 1개 이상 조회 성공")
    void findPlaylistItems() {
        String publicId = UUID.randomUUID().toString().replace("-", "");

        User user1 = new User();
        user1.setNickname("user1");
        user1.setEmail("abc@abc.com");
        user1.setPassword("abc123");
        user1.setPublicId(publicId);

        userRepository.save(user1);

        Music music1 = new Music("music1", "abc", user1, 111L, UUID.randomUUID().toString().replace("-", ""));
        Music music2 = new Music("music2", "abc", user1, 111L, UUID.randomUUID().toString().replace("-", ""));
        Music music3 = new Music("music3", "abc", user1, 111L, UUID.randomUUID().toString().replace("-", ""));

        musicRepository.save(music1);
        musicRepository.save(music2);
        musicRepository.save(music3);

        Playlist playlist1 = new Playlist(publicId, "playlist1", user1);
        PlaylistItem item1 = new PlaylistItem(1, music1, playlist1);
        PlaylistItem item2 = new PlaylistItem(2, music2, playlist1);
        PlaylistItem item3 = new PlaylistItem(3, music3, playlist1);

        playlistRepository.save(playlist1);
        playlistItemRepository.save(item1);
        playlistItemRepository.save(item2);
        playlistItemRepository.save(item3);

        log.info("==================================");

        List<PlaylistItemSimpleDto> results = playlistItemQueryRepository.findPlaylistItems(publicId);

        assertThat(results).isNotEmpty();
        assertThat(results.stream().map(r -> r.getMusic().getTitle())).containsExactly(
                music1.getName(),
                music2.getName(),
                music3.getName()
        );
    }

    @Test
    @DisplayName("플레이 리스트 조회 - 조회 실패")
    void findPlaylistItemsFail() {
        String publicId = UUID.randomUUID().toString().replace("-", "");

        User user1 = new User();
        user1.setNickname("user1");
        user1.setEmail("abc@abc.com");
        user1.setPassword("abc123");
        user1.setPublicId(publicId);

        userRepository.save(user1);

        Music music1 = new Music("music1", "abc", user1, 111L, UUID.randomUUID().toString().replace("-", ""));
        Music music2 = new Music("music2", "abc", user1, 111L, UUID.randomUUID().toString().replace("-", ""));
        Music music3 = new Music("music3", "abc", user1, 111L, UUID.randomUUID().toString().replace("-", ""));

        musicRepository.save(music1);
        musicRepository.save(music2);
        musicRepository.save(music3);

        Playlist playlist1 = new Playlist(publicId, "playlist1", user1);
        PlaylistItem item1 = new PlaylistItem(1, music1, playlist1);
        PlaylistItem item2 = new PlaylistItem(2, music2, playlist1);
        PlaylistItem item3 = new PlaylistItem(3, music3, playlist1);

        playlistRepository.save(playlist1);
        playlistItemRepository.save(item1);
        playlistItemRepository.save(item2);
        playlistItemRepository.save(item3);

        String wrongPublicId = UUID.randomUUID().toString().replace("-", "");
        List<PlaylistItemSimpleDto> results = playlistItemQueryRepository.findPlaylistItems(wrongPublicId);

        assertThat(results).isEmpty();
    }
}
