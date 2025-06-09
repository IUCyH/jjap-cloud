package com.iucyh.jjapcloud.service;

import com.iucyh.jjapcloud.common.exception.ServiceException;
import com.iucyh.jjapcloud.common.exception.errorcode.ServiceErrorCode;
import com.iucyh.jjapcloud.domain.playlist.Playlist;
import com.iucyh.jjapcloud.domain.user.User;
import com.iucyh.jjapcloud.dto.playlist.CreatePlaylistDto;
import com.iucyh.jjapcloud.dto.playlist.PlaylistDto;
import com.iucyh.jjapcloud.dtomapper.PlaylistDtoMapper;
import com.iucyh.jjapcloud.repository.playlist.PlaylistRepository;
import com.iucyh.jjapcloud.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.rmi.ServerException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PlaylistService {

    private final PlaylistRepository playlistRepository;
    private final UserRepository userRepository;

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
    public void deletePlaylist(Long userId, String publicId) {
        Long id = playlistRepository.findIdByUserIdAndPublicId(userId, publicId)
                        .orElseThrow(() -> new ServiceException(ServiceErrorCode.PLAYLIST_NOT_FOUND));
        playlistRepository.deleteById(id);
    }
}
