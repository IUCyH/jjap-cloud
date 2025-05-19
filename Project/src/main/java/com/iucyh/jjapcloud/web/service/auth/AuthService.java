package com.iucyh.jjapcloud.web.service.auth;

import com.iucyh.jjapcloud.web.dto.RequestSuccessDto;
import com.iucyh.jjapcloud.web.dto.user.UserInfoDto;

public interface AuthService {

    UserInfoDto login(String email, String password);
}
