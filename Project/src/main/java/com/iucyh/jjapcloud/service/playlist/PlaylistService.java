package com.iucyh.jjapcloud.service.playlist;

import com.iucyh.jjapcloud.common.exception.ServiceException;
import com.iucyh.jjapcloud.common.exception.errorcode.ServiceErrorCode;
import com.iucyh.jjapcloud.domain.music.Music;
import com.iucyh.jjapcloud.domain.playlist.Playlist;
import com.iucyh.jjapcloud.domain.playlist.PlaylistItem;
import com.iucyh.jjapcloud.domain.user.User;
import com.iucyh.jjapcloud.dto.playlist.CreatePlaylistDto;
import com.iucyh.jjapcloud.dto.playlist.PlaylistDto;
import com.iucyh.jjapcloud.dtomapper.PlaylistDtoMapper;
import com.iucyh.jjapcloud.repository.playlist.PlaylistRepository;
import com.iucyh.jjapcloud.repository.playlist.projection.PlaylistIdAndMusicIdProjection;
import com.iucyh.jjapcloud.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PlaylistService {

    private final Environment environment;
    private final PlaylistRepository playlistRepository;
    private final UserRepository userRepository;

    public Long getPlaylistId(Long userId, String publicId) {
        return playlistRepository.findIdByUserIdAndPublicId(userId, publicId)
                .orElseThrow(() -> new ServiceException(ServiceErrorCode.PLAYLIST_NOT_FOUND));
    }

    public List<PlaylistDto> findPlaylists(Long userId) {
        List<Playlist> results = playlistRepository.findByUserId(userId)
                .orElseThrow(() -> new ServiceException(ServiceErrorCode.PLAYLIST_NOT_FOUND));

        return results
                .stream()
                .map(PlaylistDtoMapper::toPlaylistDto)
                .toList();
    }

    @Transactional
    public void createPlaylist(Long userId, CreatePlaylistDto playlist) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ServiceException(ServiceErrorCode.USER_NOT_FOUND));

        Playlist savePlaylist = new Playlist(playlist.getName(), user);
        playlistRepository.save(savePlaylist);
    }

    @Transactional
    public void updatePlaylistName(String publicId, String newName) {
        Playlist playlist = playlistRepository.findByPublicId(publicId)
                .orElseThrow(() -> new ServiceException(ServiceErrorCode.PLAYLIST_NOT_FOUND));
        playlist.setName(newName);
    }

    @Transactional
    public int increaseItemCount(Long id) {
        if (Arrays.asList(environment.getActiveProfiles()).contains("test")) {
            playlistRepository.increaseItemCountWithoutReturning(id);
            return playlistRepository.findItemCountById(id).get(); // 테스트용
        } else {
            return playlistRepository.increaseItemCount(id);
        }
    }

    @Transactional
    public void decreaseItemCount(Long id, Long userId) {
        playlistRepository.decreaseItemCount(id, userId);
    }

    @Transactional
    public void deletePlaylist(Long userId, String publicId) {
        // TODO: 연관된 playlist item 들도 삭제 or 논리 삭제 처리
        Long id = playlistRepository.findIdByUserIdAndPublicId(userId, publicId)
                        .orElseThrow(() -> new ServiceException(ServiceErrorCode.PLAYLIST_NOT_FOUND));
        playlistRepository.deleteById(id);
    }
}
