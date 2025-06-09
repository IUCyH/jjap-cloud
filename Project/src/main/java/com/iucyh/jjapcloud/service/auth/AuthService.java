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
        Optional<User> userOptional = userRepository.findByEmail(email);
        if(userOptional.isPresent()) {
            User user = userOptional.get();
            if(user.getPassword().equals(password)) {
                MyUserDto userDto = UserDtoMapper.toMyUserDto(user);
                return new UserLoginResult(user.getId(), userDto);
            }
        }

        throw new ServiceException(ServiceErrorCode.UNAUTHORIZED);
    }

    public String createCsrfToken() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    public void logout(HttpSession session) {
        session.invalidate();
    }
}
