package com.iucyh.jjapcloud.web.controller;

import com.iucyh.jjapcloud.common.annotation.loginuser.UserInfo;
import com.iucyh.jjapcloud.web.dto.RequestSuccessDto;
import com.iucyh.jjapcloud.web.dto.auth.LoginDto;
import com.iucyh.jjapcloud.web.dto.auth.LoginResultDto;
import com.iucyh.jjapcloud.web.dto.user.UserDto;
import com.iucyh.jjapcloud.web.service.auth.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public LoginResultDto login(@Validated @RequestBody LoginDto loginDto, HttpServletRequest request) {
        UserDto user = authService.login(loginDto.getEmail(), loginDto.getPassword());

        HttpSession oldSession = request.getSession(false);
        if(oldSession != null) {
            oldSession.invalidate();
        }

        String csrfToken = authService.createCsrfToken();

        HttpSession newSession = request.getSession();
        UserInfo userInfo = UserInfo.from(user);

        newSession.setAttribute("user", userInfo);
        newSession.setAttribute("csrfToken", csrfToken);

        return new LoginResultDto(user, csrfToken);
    }

    @PostMapping("/logout")
    public RequestSuccessDto logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        return authService.logout(session);
    }
}
