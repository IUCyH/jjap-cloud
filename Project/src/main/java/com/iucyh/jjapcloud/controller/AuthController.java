package com.iucyh.jjapcloud.controller;

import com.iucyh.jjapcloud.dto.ResponseDto;
import com.iucyh.jjapcloud.dto.auth.LoginDto;
import com.iucyh.jjapcloud.dto.auth.LoginResultDto;
import com.iucyh.jjapcloud.service.auth.AuthService;
import com.iucyh.jjapcloud.service.auth.UserLoginResult;
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
    public ResponseDto<LoginResultDto> login(@Validated @RequestBody LoginDto loginDto, HttpServletRequest request) {
        UserLoginResult result = authService.login(loginDto.getEmail(), loginDto.getPassword());

        HttpSession oldSession = request.getSession(false);
        if (oldSession != null) {
            oldSession.invalidate();
        }

        HttpSession newSession = request.getSession();
        newSession.setAttribute("userId", result.getUserId());
        newSession.setAttribute("csrfToken", result.getCsrfToken());

        LoginResultDto dto = new LoginResultDto(result.getCsrfToken());
        return ResponseDto.success("Login success", dto);
    }

    @PostMapping("/logout")
    public ResponseDto<Void> logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        authService.logout(session);

        return ResponseDto.success("Logout success", null);
    }
}
