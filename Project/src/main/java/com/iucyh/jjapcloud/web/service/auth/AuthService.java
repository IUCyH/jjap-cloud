package com.iucyh.jjapcloud.web.service.auth;

import com.iucyh.jjapcloud.web.dto.ResponseDto;
import com.iucyh.jjapcloud.web.dto.user.UserDto;
import jakarta.servlet.http.HttpSession;

public interface AuthService {

    UserDto login(String email, String password);
    String createCsrfToken();
    void logout(HttpSession session);
}
