package com.iucyh.jjapcloud.web.dto.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateUserDto {

    private String nickname;
    private String email;
    private String password;

    public CreateUserDto() {}
}
