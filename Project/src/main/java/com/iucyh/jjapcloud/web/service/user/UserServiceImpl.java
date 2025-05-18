package com.iucyh.jjapcloud.web.service.user;

import com.iucyh.jjapcloud.domain.user.User;
import com.iucyh.jjapcloud.domain.user.repository.UserRepository;
import com.iucyh.jjapcloud.web.dto.user.CreateUserDto;
import com.iucyh.jjapcloud.web.dto.user.MyUserDto;
import com.iucyh.jjapcloud.web.dto.user.UpdateUserDto;
import com.iucyh.jjapcloud.web.dto.user.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserDto getUserById(int id) {
        User user = userRepository.find(id);
        return UserDto.from(user);
    }

    @Override
    public MyUserDto getMyUserById(int id) {
        User user = userRepository.find(id);
        return MyUserDto.from(user);
    }

    @Override
    public int createUser(CreateUserDto userDto) {
        User user = new User();
        user.setNickname(userDto.getNickname());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());

        return userRepository.create(user);
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
