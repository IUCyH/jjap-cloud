package com.iucyh.jjapcloud.service;

import com.iucyh.jjapcloud.domain.user.User;
import com.iucyh.jjapcloud.common.exception.ServiceException;
import com.iucyh.jjapcloud.common.exception.errorcode.ServiceErrorCode;
import com.iucyh.jjapcloud.dto.IdDto;
import com.iucyh.jjapcloud.dto.user.CreateUserDto;
import com.iucyh.jjapcloud.dto.user.MyUserDto;
import com.iucyh.jjapcloud.dto.user.UpdateUserDto;
import com.iucyh.jjapcloud.dto.user.UserDto;
import com.iucyh.jjapcloud.dtomapper.UserDtoMapper;
import com.iucyh.jjapcloud.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

    public UserDto getUserById(long id) {
        Optional<User> user = userRepository.findById(id);
        return user
                .map(UserDtoMapper::toUserDto)
                .orElseThrow(() -> new ServiceException(ServiceErrorCode.USER_NOT_FOUND));
    }

    public MyUserDto getMyUserById(long id) {
        Optional<User> user = userRepository.findById(id);
        return user
                .map(UserDtoMapper::toMyUserDto)
                .orElseThrow(() -> new ServiceException(ServiceErrorCode.USER_NOT_FOUND));
    }

    @Transactional
    public IdDto createUser(CreateUserDto userDto) {
        User user = new User(
                userDto.getNickname(),
                userDto.getEmail(),
                userDto.getPassword()
        );

        long id = userRepository.save(user).getId();
        return new IdDto(id);
    }

    @Transactional
    public void updateUser(long id, UpdateUserDto userDto) {
        User foundUser = userRepository.findById(id).orElseThrow();
        foundUser.setNickname(userDto.getNickname());
        foundUser.setPassword(userDto.getPassword());
    }

    @Transactional
    public void deleteUser(long id) {
        userRepository.deleteById(id);
    }
}
