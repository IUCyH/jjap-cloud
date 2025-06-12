package com.iucyh.jjapcloud.dto.playlist;

import com.iucyh.jjapcloud.domain.music.Music;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddPlaylistItemDto {

    @NotBlank
    private String musicPublicId;

    public AddPlaylistItemDto() {}
}
