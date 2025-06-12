package com.iucyh.jjapcloud.facade;

import com.iucyh.jjapcloud.common.exception.ServiceException;
import com.iucyh.jjapcloud.common.exception.errorcode.ServiceErrorCode;
import com.iucyh.jjapcloud.domain.music.Music;
import com.iucyh.jjapcloud.domain.playlist.Playlist;
import com.iucyh.jjapcloud.domain.playlist.PlaylistItem;
import com.iucyh.jjapcloud.domain.user.User;
import com.iucyh.jjapcloud.repository.music.MusicRepository;
import com.iucyh.jjapcloud.repository.playlist.PlaylistItemRepository;
import com.iucyh.jjapcloud.repository.playlist.PlaylistRepository;
import com.iucyh.jjapcloud.repository.user.UserRepository;
import com.iucyh.jjapcloud.service.playlist.PlaylistService;
import jakarta.persistence.EntityManager;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
@Slf4j
class PlaylistItemFacadeTest {

    @Autowired
    private EntityManager em;
    @Autowired
    private PlaylistService playlistService;
    @Autowired
    private PlaylistItemRepository playlistItemRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PlaylistRepository playlistRepository;
    @Autowired
    private MusicRepository musicRepository;

    @Test
    @DisplayName("플레이 리스트에 아이템 추가 - 성공")
    void addPlaylistItem() {
        User user = new User("owner", "abc@abc.com", "abc123");
        User singer1 = new User("singer1", "abc@abc.com", "abc123");
        User singer2 = new User("singer2", "abc@abc.com", "abc123");

        Music music1 = new Music("music1", "storename", singer1, 1000L);
        Music music2 = new Music("music2", "storename", singer2, 1000L);

        Playlist playlist = new Playlist("playlist1", user);

        userRepository.save(user);
        userRepository.save(singer1);
        userRepository.save(singer2);

        musicRepository.save(music1);
        musicRepository.save(music2);

        playlistRepository.save(playlist);

        playlistService.addMusicToPlaylist(user.getId(), playlist.getPublicId(), music1);
        playlistService.addMusicToPlaylist(user.getId(), playlist.getPublicId(), music2);

        List<PlaylistItem> items = playlistItemRepository.findAll();
        assertThat(items).hasSize(2);
        assertThat(items.stream().map(i -> i.getMusic().getPublicId())).containsExactly(
                music1.getPublicId(),
                music2.getPublicId()
        );
    }

    @Test
    @DisplayName("플레이리스트에 아이템 추가 - 실패 (유저 소유의 플레이리스트가 아님)")
    void addPlaylistItemFailWithUserNotOwner() {
        User owner = new User("owner", "abc@abc.com", "abc123");
        User another = new User("not owner", "abc@abc.com", "abc123");
        User singer1 = new User("singer1", "abc@abc.com", "abc123");
        User singer2 = new User("singer2", "abc@abc.com", "abc123");

        Music music1 = new Music("music1", "storename", singer1, 1000L);
        Music music2 = new Music("music2", "storename", singer2, 1000L);

        Playlist playlist = new Playlist("playlist1", owner);

        userRepository.save(owner);
        userRepository.save(another);
        userRepository.save(singer1);
        userRepository.save(singer2);

        musicRepository.save(music1);
        musicRepository.save(music2);

        playlistRepository.save(playlist);

        assertThatThrownBy(
                () -> playlistService.addMusicToPlaylist(another.getId(), playlist.getPublicId(), music1)
        ).isInstanceOf(ServiceException.class);
    }

    @Test
    @DisplayName("플레이리스트에 아이템 추가 - 실패 (중복된 음악)")
    void addPlaylistItemFailWithDuplicateMusic() {
        PlaylistAddResult result = addPlaylistItems();

        em.flush();
        em.clear();

        // 똑같은 음악을 중복 삽입
        assertThatThrownBy(
                () -> playlistService.addMusicToPlaylist(
                        result.getOwner().getId(),
                        result.getPlaylist().getPublicId(),
                        result.getMusics().get(0)
                )
        ).isInstanceOf(ServiceException.class);
    }

    @Test
    @DisplayName("플레이리스트에 아이템 삭제 - 성공")
    void removePlaylistItem() {
        PlaylistAddResult result = addPlaylistItems();
        Integer beforeCount = playlistRepository.findItemCountById(result.getPlaylist().getId()).get();
        assertThat(beforeCount).isEqualTo(1);

        playlistService.removeMusicFromPlaylist(result.getOwner().getId(), result.getPlaylist().getPublicId(), result.getMusics().get(0));

        Integer afterCount = playlistRepository.findItemCountById(result.getPlaylist().getId()).get();
        assertThat(afterCount).isEqualTo(0);

        List<PlaylistItem> items = playlistItemRepository.findAll();
        assertThat(items).isEmpty();
    }

    @Test
    @DisplayName("플레이리스트에 아이템 삭제 - 실패")
    void removePlaylistItemFail() {
        PlaylistAddResult result = addPlaylistItems();

        // 쿼리 조건 중 하나만 틀리면 됨 - 유저 id를 대표로 틀림
        assertThatThrownBy(
                () -> playlistService.removeMusicFromPlaylist(1111L, result.getPlaylist().getPublicId(), result.getMusics().get(0))
        ).isInstanceOf(ServiceException.class);
    }

    private PlaylistAddResult addPlaylistItems() {
        User owner = new User(UUID.randomUUID().toString().replace("-", ""), "abc@abc.com", "abc123");
        User singer1 = new User(UUID.randomUUID().toString().replace("-", ""), "abc@abc.com", "abc123");
        User singer2 = new User(UUID.randomUUID().toString().replace("-", ""), "abc@abc.com", "abc123");

        Music music1 = new Music(UUID.randomUUID().toString().replace("-", ""), "storename", singer1, 1000L);

        Playlist playlist = new Playlist(UUID.randomUUID().toString().replace("-", ""), owner);

        userRepository.save(owner);
        userRepository.save(singer1);
        userRepository.save(singer2);

        musicRepository.save(music1);

        playlistRepository.save(playlist);

        playlistService.addMusicToPlaylist(owner.getId(), playlist.getPublicId(), music1);

        log.info("{}", music1.getPublicId());
        PlaylistAddResult result = new PlaylistAddResult();
        result.setOwner(owner);
        result.getMusics().add(music1);
        result.setPlaylist(playlist);
        return result;
    }

    @Getter
    @Setter
    public static class PlaylistAddResult {
        private User owner;
        private Playlist playlist;
        private List<Music> musics = new ArrayList<>();
    }
}
