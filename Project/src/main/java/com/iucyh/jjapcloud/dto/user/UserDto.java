package com.iucyh.jjapcloud.dto.user;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserDto {

    private Integer id;
    private String nickname;
}
