package com.iucyh.jjapcloud.service.playlist;

import com.iucyh.jjapcloud.common.exception.ServiceException;
import com.iucyh.jjapcloud.common.exception.errorcode.ServiceErrorCode;
import com.iucyh.jjapcloud.domain.music.Music;
import com.iucyh.jjapcloud.domain.playlist.Playlist;
import com.iucyh.jjapcloud.domain.playlist.PlaylistItem;
import com.iucyh.jjapcloud.dto.playlist.PlaylistItemDto;
import com.iucyh.jjapcloud.dto.playlist.query.PlaylistItemSimpleDto;
import com.iucyh.jjapcloud.dtomapper.PlaylistDtoMapper;
import com.iucyh.jjapcloud.repository.playlist.PlaylistItemQueryRepository;
import com.iucyh.jjapcloud.repository.playlist.PlaylistItemRepository;
import com.iucyh.jjapcloud.repository.playlist.projection.PlaylistIdAndMusicIdProjection;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PlaylistItemService {

    private final PlaylistItemRepository plItemRepository;
    private final PlaylistItemQueryRepository plItemQueryRepository;

    public List<PlaylistItemDto> getPlaylistItems(String playlistId) {
        List<PlaylistItemSimpleDto> playlistItems = plItemQueryRepository.findPlaylistItems(playlistId);
        if(playlistItems.isEmpty()) {
            throw new ServiceException(ServiceErrorCode.PLAYLIST_ITEM_NOT_FOUND);
        }

        return playlistItems
                .stream()
                .map(PlaylistDtoMapper::toPlaylistItemDto)
                .toList();
    }
}
