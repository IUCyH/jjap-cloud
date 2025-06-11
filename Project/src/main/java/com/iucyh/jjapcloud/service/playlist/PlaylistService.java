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
import com.iucyh.jjapcloud.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PlaylistService {

    private final PlaylistRepository playlistRepository;
    private final UserRepository userRepository;

    public Long getPlaylistId(String publicId) {
        return playlistRepository.findIdByPublicId(publicId)
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
        return playlistRepository.increaseItemCount(id);
    }

    @Transactional
    public void deletePlaylist(Long userId, String publicId) {
        Long id = playlistRepository.findIdByUserIdAndPublicId(userId, publicId)
                        .orElseThrow(() -> new ServiceException(ServiceErrorCode.PLAYLIST_NOT_FOUND));
        playlistRepository.deleteById(id);
    }
}
