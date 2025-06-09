package com.iucyh.jjapcloud.dto.user;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserInfoDto {

    private String publicId;
    private String nickname;
}
