package com.iucyh.jjapcloud.web.dto.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateUserDto {

    private String nickname;
    private String password;

    public UpdateUserDto() {}
}
