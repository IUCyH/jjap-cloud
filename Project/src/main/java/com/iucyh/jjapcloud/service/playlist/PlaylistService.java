package com.iucyh.jjapcloud.service.playlist;

import com.iucyh.jjapcloud.common.exception.ServiceException;
import com.iucyh.jjapcloud.common.exception.errorcode.ServiceErrorCode;
import com.iucyh.jjapcloud.domain.music.Music;
import com.iucyh.jjapcloud.domain.playlist.Playlist;
import com.iucyh.jjapcloud.domain.user.User;
import com.iucyh.jjapcloud.dto.IdDto;
import com.iucyh.jjapcloud.dto.playlist.AddPlaylistItemDto;
import com.iucyh.jjapcloud.dto.playlist.CreatePlaylistDto;
import com.iucyh.jjapcloud.dto.playlist.PlaylistDto;
import com.iucyh.jjapcloud.dtomapper.PlaylistDtoMapper;
import com.iucyh.jjapcloud.repository.music.MusicRepository;
import com.iucyh.jjapcloud.repository.playlist.PlaylistItemRepository;
import com.iucyh.jjapcloud.repository.playlist.PlaylistRepository;
import com.iucyh.jjapcloud.repository.user.UserRepository;
import com.iucyh.jjapcloud.service.music.MusicService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PlaylistService {

    private final PlaylistRepository playlistRepository;
    private final PlaylistItemRepository playlistItemRepository;
    private final UserRepository userRepository;
    private final MusicService musicService;

    public List<PlaylistDto> findPlaylists(Long userId) {
        List<Playlist> results = playlistRepository.findByUserId(userId)
                .orElseThrow(() -> new ServiceException(ServiceErrorCode.PLAYLIST_NOT_FOUND));

        return results
                .stream()
                .map(PlaylistDtoMapper::toPlaylistDto)
                .toList();
    }

    @Transactional
    public void addMusicToPlaylist(Long userId, String playlistPublicId, AddPlaylistItemDto dto) {
        Music music = musicService.getMusicEntityByPublicId(dto.getMusicPublicId());
        // 유저가 해당 플레이리스트를 소유하고 있는 지 확인 및 Playlist 객체 반환
        Playlist playlist = playlistRepository.findByUserIdAndPublicId(userId, playlistPublicId)
                .orElseThrow(() -> new ServiceException(ServiceErrorCode.PLAYLIST_NOT_FOUND));

        boolean exists = playlistItemRepository.isMusicExistsInPlaylist(playlistPublicId, music.getPublicId());
        if (exists) {
            throw new ServiceException(ServiceErrorCode.PLAYLIST_MUSIC_EXISTS);
        }

        playlist.addMusic(music);
    }

    @Transactional
    public void removeMusicFromPlaylist(Long userId, String playlistPublicId, String musicPublicId) {
        Music music = musicService.getMusicEntityByPublicId(musicPublicId);
        // userId, playlistPublicId, musicPublicId 가 전부 일치하는 item이 있을때만 playlist 반환
        Playlist playlist = playlistItemRepository.findPlaylist(userId, playlistPublicId, music.getPublicId())
                .orElseThrow(() -> new ServiceException(ServiceErrorCode.PLAYLIST_ITEM_NOT_FOUND));
        playlist.removeMusic(music);
    }

    @Transactional
    public IdDto createPlaylist(Long userId, CreatePlaylistDto playlist) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ServiceException(ServiceErrorCode.USER_NOT_FOUND));

        Playlist savePlaylist = new Playlist(playlist.getName(), user);

        Playlist result = playlistRepository.save(savePlaylist);
        return new IdDto(result.getPublicId());
    }

    @Transactional
    public void updatePlaylistName(String publicId, String newName) {
        Playlist playlist = playlistRepository.findByPublicId(publicId)
                .orElseThrow(() -> new ServiceException(ServiceErrorCode.PLAYLIST_NOT_FOUND));
        playlist.setName(newName);
    }

    @Transactional
    public void deletePlaylist(Long userId, String publicId) {
        playlistRepository.deleteByPublicId(userId, publicId);
    }
}
