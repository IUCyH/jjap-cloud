package com.iucyh.jjapcloud.dto.user.query;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

@Getter
public class JoinUserDto {

    private String publicId;
    private String nickname;

    @QueryProjection
    public JoinUserDto(String publicId, String nickname) {
        this.publicId = publicId;
        this.nickname = nickname;
    }
}
