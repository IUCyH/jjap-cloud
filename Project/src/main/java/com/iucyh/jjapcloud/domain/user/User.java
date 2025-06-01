package com.iucyh.jjapcloud.domain.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {

    private Integer id;
    private String nickname;
    private String email;
    private String password;

    public User() {}
}
