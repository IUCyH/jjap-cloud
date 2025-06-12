package com.iucyh.jjapcloud.controller;

import com.iucyh.jjapcloud.common.annotation.loginuser.LoginUserId;
import com.iucyh.jjapcloud.dto.IdDto;
import com.iucyh.jjapcloud.dto.ResponseDto;
import com.iucyh.jjapcloud.dto.playlist.*;
import com.iucyh.jjapcloud.service.music.MusicService;
import com.iucyh.jjapcloud.service.playlist.PlaylistItemService;
import com.iucyh.jjapcloud.service.playlist.PlaylistService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/playlists")
@RequiredArgsConstructor
public class PlaylistController {

    private final PlaylistService playlistService;
    private final PlaylistItemService playlistItemService;

    @GetMapping
    public ResponseDto<List<PlaylistDto>> getPlaylists(@LoginUserId Long userId) {
        return ResponseDto
                .success("get playlist success", playlistService.findPlaylists(userId));
    }

    @GetMapping("/{publicId}")
    public ResponseDto<List<PlaylistItemDto>> getPlaylistItems(@LoginUserId Long userId, @PathVariable String publicId) {
        return ResponseDto
                .success("get items success", playlistItemService.getPlaylistItems(userId, publicId));
    }

    @PostMapping
    public ResponseDto<IdDto> createPlaylist(@LoginUserId Long userId, @Validated @RequestBody CreatePlaylistDto createPlaylistDto) {
       IdDto idDto = playlistService.createPlaylist(userId, createPlaylistDto);
       return ResponseDto
               .success("create playlist success", idDto);
    }

    @PostMapping("/{publicId}/musics")
    public ResponseDto<Void> addMusicToPlaylist(
            @LoginUserId Long userId,
            @PathVariable String publicId,
            @Validated @RequestBody AddPlaylistItemDto playlistItemDto) {
        playlistService.addMusicToPlaylist(userId, publicId, playlistItemDto);
        return ResponseDto
                .success("add playlist item success", null);
    }

    @PatchMapping("/{publicId}")
    public ResponseDto<Void> updatePlaylist(@PathVariable String publicId, @Validated @RequestBody UpdatePlaylistDto playlistDto) {
        playlistService.updatePlaylistName(publicId, playlistDto.getName());
        return ResponseDto
                .success("update playlist success", null);
    }

    @DeleteMapping("/{publicId}/musics/{musicPublicId}")
    public ResponseDto<Void> removeMusicFromPlaylist(
            @LoginUserId Long userId,
            @PathVariable String publicId,
            @PathVariable String musicPublicId) {
        playlistService.removeMusicFromPlaylist(userId, publicId, musicPublicId);
        return ResponseDto
                .success("remove playlist item success", null);
    }

    @DeleteMapping("/{publicId}")
    public ResponseDto<Void> deletePlaylist(@LoginUserId Long userId, @PathVariable String publicId) {
        playlistService.deletePlaylist(userId, publicId);
        return ResponseDto
                .success("delete playlist success", null);
    }
}
