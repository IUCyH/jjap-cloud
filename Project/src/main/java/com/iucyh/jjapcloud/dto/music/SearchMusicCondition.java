package com.iucyh.jjapcloud.dto.music;

import com.iucyh.jjapcloud.repository.music.MusicSearchField;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchMusicCondition {

    @NotNull
    MusicSearchField field;

    @NotBlank
    String keyword;

    public SearchMusicCondition() {}
}
