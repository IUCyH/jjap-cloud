package com.iucyh.jjapcloud.web.dto;

import lombok.Getter;

@Getter
public class RequestSuccessDto {

    private String message;

    public RequestSuccessDto(String message) {
        this.message = message;
    }
}
