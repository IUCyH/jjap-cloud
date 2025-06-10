package com.iucyh.jjapcloud.facade.playlist;

import com.iucyh.jjapcloud.common.exception.ServiceException;
import com.iucyh.jjapcloud.common.exception.errorcode.ServiceErrorCode;
import com.iucyh.jjapcloud.repository.playlist.PlaylistInfo;
import com.iucyh.jjapcloud.service.music.MusicService;
import com.iucyh.jjapcloud.service.playlist.PlaylistItemService;
import com.iucyh.jjapcloud.service.playlist.PlaylistService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class PlaylistItemFacade {

    private final PlaylistService playlistService;
    private final PlaylistItemService playlistItemService;
    private final MusicService musicService;

    public void addMusicToPlaylist(Long userId, String playlistPublicId, String musicPublicId) {
        Long musicId = musicService.getMusicId(musicPublicId);
        PlaylistInfo playlistInfo = playlistService.getPlaylistInfo(userId, playlistPublicId);

        boolean exists = playlistItemService.isMusicExistsInPlaylist(musicId, playlistInfo.getId());
        if (exists) {
            throw new ServiceException(ServiceErrorCode.PLAYLIST_MUSIC_EXISTS);
        }

        playlistItemService.addMusicToPlaylist(playlistInfo, musicId);
    }
}
