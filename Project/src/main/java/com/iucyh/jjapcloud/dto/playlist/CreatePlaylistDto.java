package com.iucyh.jjapcloud.dto.playlist;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreatePlaylistDto {

    @NotBlank
    private String name;

    public CreatePlaylistDto(String name) {
        this.name = name;
    }
}
