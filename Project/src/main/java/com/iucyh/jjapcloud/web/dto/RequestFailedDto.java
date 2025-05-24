package com.iucyh.jjapcloud.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class RequestFailedDto {

    private int status;
    private String code;
    private String message;

    public RequestFailedDto(int status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}
