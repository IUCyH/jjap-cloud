package com.iucyh.jjapcloud.web.service.auth;

import com.iucyh.jjapcloud.web.dto.RequestSuccessDto;
import com.iucyh.jjapcloud.web.dto.user.UserInfoDto;
import jakarta.servlet.http.HttpSession;

public interface AuthService {

    UserInfoDto login(String email, String password);
    RequestSuccessDto logout(HttpSession session);
}
