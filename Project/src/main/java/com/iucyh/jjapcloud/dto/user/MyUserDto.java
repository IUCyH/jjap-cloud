package com.iucyh.jjapcloud.dto.user;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MyUserDto {

    private String nickname;
    private String email;
}
