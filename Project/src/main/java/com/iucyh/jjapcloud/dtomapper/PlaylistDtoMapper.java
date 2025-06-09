package com.iucyh.jjapcloud.dtomapper;

import com.iucyh.jjapcloud.domain.playlist.Playlist;
import com.iucyh.jjapcloud.dto.playlist.PlaylistDto;

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
}
