package com.iucyh.jjapcloud.web.service.auth;

import com.iucyh.jjapcloud.domain.user.User;
import com.iucyh.jjapcloud.domain.user.repository.UserRepository;
import com.iucyh.jjapcloud.web.dto.user.UserInfoDto;
import com.iucyh.jjapcloud.web.exception.BusinessException;
import com.iucyh.jjapcloud.web.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;

    @Override
    public UserInfoDto login(String email, String password) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if(userOptional.isPresent()) {
            User user = userOptional.get();
            if(user.getPassword().equals(password)) {
                return UserInfoDto.from(user);
            }
        }

        throw new BusinessException(ErrorCode.USER_NOT_FOUND);
    }
}
