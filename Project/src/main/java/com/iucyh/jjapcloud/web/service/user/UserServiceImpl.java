package com.iucyh.jjapcloud.web.service.user;

import com.iucyh.jjapcloud.domain.user.User;
import com.iucyh.jjapcloud.domain.user.repository.UserRepository;
import com.iucyh.jjapcloud.common.exception.ServiceException;
import com.iucyh.jjapcloud.common.exception.errorcode.ServiceErrorCode;
import com.iucyh.jjapcloud.web.dto.IdDto;
import com.iucyh.jjapcloud.web.dto.user.CreateUserDto;
import com.iucyh.jjapcloud.web.dto.user.MyUserDto;
import com.iucyh.jjapcloud.web.dto.user.UpdateUserDto;
import com.iucyh.jjapcloud.web.dto.user.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserDto getUserById(int id) {
        Optional<User> user = userRepository.find(id);
        return user
                .map(UserDto::from)
                .orElseThrow(() -> new ServiceException(ServiceErrorCode.USER_NOT_FOUND));
    }

    @Override
    public MyUserDto getMyUserById(int id) {
        Optional<User> user = userRepository.find(id);
        return user
                .map(MyUserDto::from)
                .orElseThrow(() -> new ServiceException(ServiceErrorCode.USER_NOT_FOUND));
    }

    @Override
    public IdDto createUser(CreateUserDto userDto) {
        User user = new User();
        user.setNickname(userDto.getNickname());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());

        int id = userRepository.create(user);
        return new IdDto(id);
    }

    @Override
    public void updateUser(int id, UpdateUserDto userDto) {
        User user = new User();
        user.setNickname(userDto.getNickname());
        user.setPassword(userDto.getPassword());

        userRepository.update(id, user);
    }

    @Override
    public void deleteUser(int id) {
        userRepository.delete(id);
    }
}
