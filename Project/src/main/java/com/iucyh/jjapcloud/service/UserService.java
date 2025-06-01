package com.iucyh.jjapcloud.service;

import com.iucyh.jjapcloud.domain.user.User;
import com.iucyh.jjapcloud.common.exception.ServiceException;
import com.iucyh.jjapcloud.common.exception.errorcode.ServiceErrorCode;
import com.iucyh.jjapcloud.dto.IdDto;
import com.iucyh.jjapcloud.dto.user.CreateUserDto;
import com.iucyh.jjapcloud.dto.user.MyUserDto;
import com.iucyh.jjapcloud.dto.user.UpdateUserDto;
import com.iucyh.jjapcloud.dto.user.UserDto;
import com.iucyh.jjapcloud.repository.user.UserRepositoryDataJpa;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepositoryDataJpa userRepository;

    public UserDto getUserById(int id) {
        Optional<User> user = userRepository.findById(id);
        return user
                .map(UserDto::from)
                .orElseThrow(() -> new ServiceException(ServiceErrorCode.USER_NOT_FOUND));
    }

    public MyUserDto getMyUserById(int id) {
        Optional<User> user = userRepository.findById(id);
        return user
                .map(MyUserDto::from)
                .orElseThrow(() -> new ServiceException(ServiceErrorCode.USER_NOT_FOUND));
    }

    public IdDto createUser(CreateUserDto userDto) {
        User user = new User();
        user.setNickname(userDto.getNickname());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());

        int id = userRepository.save(user).getId();
        return new IdDto(id);
    }

    public void updateUser(int id, UpdateUserDto userDto) {
        User foundUser = userRepository.findById(id).orElseThrow();
        foundUser.setNickname(userDto.getNickname());
        foundUser.setPassword(userDto.getPassword());
    }

    public void deleteUser(int id) {
        userRepository.deleteById(id);
    }
}
