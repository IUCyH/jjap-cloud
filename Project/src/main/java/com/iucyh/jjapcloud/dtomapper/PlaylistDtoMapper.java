package com.iucyh.jjapcloud.dtomapper;

import com.iucyh.jjapcloud.domain.playlist.Playlist;
import com.iucyh.jjapcloud.domain.playlist.PlaylistItem;
import com.iucyh.jjapcloud.dto.music.MusicDto;
import com.iucyh.jjapcloud.dto.playlist.PlaylistDto;
import com.iucyh.jjapcloud.dto.playlist.PlaylistItemDto;
import com.iucyh.jjapcloud.dto.playlist.query.PlaylistItemSimpleDto;

public class PlaylistDtoMapper {

    private PlaylistDtoMapper() {}

    public static PlaylistDto toPlaylistDto(Playlist playlist) {
        return PlaylistDto.builder()
                .publicId(playlist.getPublicId())
                .name(playlist.getName())
                .createdAt(playlist.getCreatedAt())
                .updatedAt(playlist.getUpdatedAt())
                .build();
    }

    public static PlaylistItemDto toPlaylistItemDto(PlaylistItemSimpleDto playlistItem) {
        MusicDto musicDto = MusicDtoMapper.toMusicDto(playlistItem.getMusic());
        return PlaylistItemDto.builder()
                .id(playlistItem.getId())
                .music(musicDto)
                .position(playlistItem.getPosition())
                .build();
    }
}
