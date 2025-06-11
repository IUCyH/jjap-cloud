package com.iucyh.jjapcloud.service.playlist;

import lombok.Getter;

@Getter
public class PlaylistIdWithMusicIdResult {

    private Long playlistId;
    private Long musicId;

    public PlaylistIdWithMusicIdResult(Long playlistId, Long musicId) {
        this.playlistId = playlistId;
        this.musicId = musicId;
    }
}
