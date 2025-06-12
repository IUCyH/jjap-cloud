package com.iucyh.jjapcloud.facade.playlist;

import com.iucyh.jjapcloud.common.exception.ServiceException;
import com.iucyh.jjapcloud.common.exception.errorcode.ServiceErrorCode;
import com.iucyh.jjapcloud.service.music.MusicService;
import com.iucyh.jjapcloud.service.playlist.PlaylistIdWithMusicIdResult;
import com.iucyh.jjapcloud.service.playlist.PlaylistItemService;
import com.iucyh.jjapcloud.service.playlist.PlaylistService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class PlaylistItemFacade {

    private final PlaylistService playlistService;
    private final PlaylistItemService playlistItemService;
    private final MusicService musicService;

    public void addMusicToPlaylist(Long userId, String playlistPublicId, String musicPublicId) {
        // 유저가 해당 플레이리스트를 소유하고 있는 지 확인 및 id 반환
        Long playlistId = playlistService.getPlaylistId(userId, playlistPublicId);
        Long musicId = musicService.getMusicId(musicPublicId);

        boolean exists = playlistItemService.isMusicExistsInPlaylist(playlistPublicId, musicPublicId);
        if (exists) {
            throw new ServiceException(ServiceErrorCode.PLAYLIST_MUSIC_EXISTS);
        }

        int count = playlistService.increaseItemCount(playlistId);
        playlistItemService.addMusicToPlaylist(playlistId, musicId, count);
    }

    public void removeMusicFromPlaylist(Long userId, String playlistPublicId, String musicPublicId) {
        // 아이템들 중 주어진 플레이리스트의 id를 가진 아이템이 있는 지 확인
        // 아이템들 중 주어진 음악의 id를 가진 아이템이 있는 지 확인
        // 플레이리스트 중 주어진 유저의 id를 가진 플레이리스트가 있는 지 확인
        PlaylistIdWithMusicIdResult result = playlistItemService.getPlaylistIdAndMusicId(userId, playlistPublicId, musicPublicId);

        playlistService.decreaseItemCount(result.getPlaylistId(), userId);
        playlistItemService.deleteMusicFromPlaylist(result.getPlaylistId(), result.getMusicId());
    }
}
