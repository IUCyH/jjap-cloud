package com.iucyh.jjapcloud.dto;

import lombok.Getter;

@Getter
public class IdDto {

    private String publicId;

    public IdDto(String publicId) {
        this.publicId = publicId;
    }
}
