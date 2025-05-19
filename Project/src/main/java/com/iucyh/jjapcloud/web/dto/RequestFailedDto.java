package com.iucyh.jjapcloud.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RequestFailedDto {

    private int status;
    private String code;
    private String message;

    public static RequestFailedDto of(int status, String code, String message) {
        return new RequestFailedDto(status, code, message);
    }
}
