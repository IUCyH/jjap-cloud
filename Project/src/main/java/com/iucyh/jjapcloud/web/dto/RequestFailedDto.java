package com.iucyh.jjapcloud.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RequestFailedDto {

    private int status;
    private String name;
    private String code;
    private String message;

    public RequestFailedDto() {}
}
