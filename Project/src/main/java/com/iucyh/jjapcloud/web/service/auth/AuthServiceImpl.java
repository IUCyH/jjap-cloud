package com.iucyh.jjapcloud.web.service.auth;

import com.iucyh.jjapcloud.domain.user.User;
import com.iucyh.jjapcloud.domain.user.repository.UserRepository;
import com.iucyh.jjapcloud.common.exception.ServiceException;
import com.iucyh.jjapcloud.common.exception.errorcode.ServiceErrorCode;
import com.iucyh.jjapcloud.web.dto.ResponseDto;
import com.iucyh.jjapcloud.web.dto.user.UserDto;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;

    @Override
    public UserDto login(String email, String password) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if(userOptional.isPresent()) {
            User user = userOptional.get();
            if(user.getPassword().equals(password)) {
                return UserDto.from(user);
            }
        }

        throw new ServiceException(ServiceErrorCode.UNAUTHORIZED);
    }

    @Override
    public String createCsrfToken() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    @Override
    public void logout(HttpSession session) {
        session.invalidate();
    }
}
