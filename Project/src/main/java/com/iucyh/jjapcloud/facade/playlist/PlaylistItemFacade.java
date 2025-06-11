package com.iucyh.jjapcloud.facade.playlist;

import com.iucyh.jjapcloud.common.exception.ServiceException;
import com.iucyh.jjapcloud.common.exception.errorcode.ServiceErrorCode;
import com.iucyh.jjapcloud.service.music.MusicService;
import com.iucyh.jjapcloud.service.playlist.PlaylistItemService;
import com.iucyh.jjapcloud.service.playlist.PlaylistService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.DefaultTransactionAttribute;

@Service
@Transactional
@RequiredArgsConstructor
public class PlaylistItemFacade {

    private final PlatformTransactionManager txManager;
    private final PlaylistService playlistService;
    private final PlaylistItemService playlistItemService;
    private final MusicService musicService;

    public void addMusicToPlaylist(Long userId, String playlistPublicId, String musicPublicId) {
        Long musicId = musicService.getMusicId(musicPublicId);
        Long playlistId = playlistService.getPlaylistId(playlistPublicId);

        boolean exists = playlistItemService.isMusicExistsInPlaylist(musicId, playlistId);
        if (exists) {
            throw new ServiceException(ServiceErrorCode.PLAYLIST_MUSIC_EXISTS);
        }

        TransactionStatus status = txManager.getTransaction(new DefaultTransactionAttribute());
        try {
            int count = playlistService.increaseItemCount(playlistId);
            playlistItemService.addMusicToPlaylist(playlistId, musicId, count);

            txManager.commit(status);
        } catch (RuntimeException e) {
            txManager.rollback(status);
            throw e;
        }
    }
}
