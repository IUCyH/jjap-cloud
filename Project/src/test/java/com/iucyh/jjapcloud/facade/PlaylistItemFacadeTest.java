package com.iucyh.jjapcloud.facade;

import com.iucyh.jjapcloud.domain.music.Music;
import com.iucyh.jjapcloud.domain.playlist.Playlist;
import com.iucyh.jjapcloud.domain.playlist.PlaylistItem;
import com.iucyh.jjapcloud.domain.user.User;
import com.iucyh.jjapcloud.facade.playlist.PlaylistItemFacade;
import com.iucyh.jjapcloud.repository.music.MusicRepository;
import com.iucyh.jjapcloud.repository.playlist.PlaylistItemRepository;
import com.iucyh.jjapcloud.repository.playlist.PlaylistRepository;
import com.iucyh.jjapcloud.repository.user.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class PlaylistItemFacadeTest {

    @Autowired
    private PlaylistItemFacade playlistItemFacade;
    @Autowired
    private PlaylistItemRepository playlistItemRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PlaylistRepository playlistRepository;
    @Autowired
    private MusicRepository musicRepository;

    @Test
    @DisplayName("플레이 리스트에 아이템 추가")
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

        playlistItemFacade.addMusicToPlaylist(user.getId(), playlist.getPublicId(), music1.getPublicId());
        playlistItemFacade.addMusicToPlaylist(user.getId(), playlist.getPublicId(), music2.getPublicId());

        List<PlaylistItem> items = playlistItemRepository.findAll();
        assertThat(items).hasSize(2);
        assertThat(items.stream().map(i -> i.getMusic().getPublicId())).containsExactly(
                music1.getPublicId(),
                music2.getPublicId()
        );
    }
}
