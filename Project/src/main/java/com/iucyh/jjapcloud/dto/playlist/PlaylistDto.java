package com.iucyh.jjapcloud.dto.playlist;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class PlaylistDto {

    private String publicId;
    private String name;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
