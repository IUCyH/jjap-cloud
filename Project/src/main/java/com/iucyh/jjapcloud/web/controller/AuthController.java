package com.iucyh.jjapcloud.web.controller;

import com.iucyh.jjapcloud.web.dto.auth.LoginDto;
import com.iucyh.jjapcloud.web.dto.user.UserInfoDto;
import com.iucyh.jjapcloud.web.service.auth.AuthService;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
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
    public UserInfoDto login(@RequestBody LoginDto loginDto, HttpServletRequest request) {
        UserInfoDto user =  authService.login(loginDto.getEmail(), loginDto.getPassword());

        HttpSession oldSession = request.getSession(false);
        if(oldSession != null) {
            oldSession.invalidate();
        }

        HttpSession newSession = request.getSession();
        newSession.setAttribute("user", user);

        return user;
    }
}
