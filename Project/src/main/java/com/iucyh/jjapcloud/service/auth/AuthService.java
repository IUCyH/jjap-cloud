package com.iucyh.jjapcloud.service.auth;

import com.iucyh.jjapcloud.domain.user.User;
import com.iucyh.jjapcloud.common.exception.ServiceException;
import com.iucyh.jjapcloud.common.exception.errorcode.ServiceErrorCode;
import com.iucyh.jjapcloud.dto.user.MyUserDto;
import com.iucyh.jjapcloud.dtomapper.UserDtoMapper;
import com.iucyh.jjapcloud.repository.user.UserRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    public UserLoginResult login(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ServiceException(ServiceErrorCode.UNAUTHORIZED));

        if (user.isCorrectPassword(password)) {
            return new UserLoginResult(user.getId(), createCsrfToken());
        }
        throw new ServiceException(ServiceErrorCode.UNAUTHORIZED);
    }

    public void logout(HttpSession session) {
        session.invalidate();
    }

    private String createCsrfToken() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
