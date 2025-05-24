package com.iucyh.jjapcloud.web.service.user;

import com.iucyh.jjapcloud.web.dto.IdDto;
import com.iucyh.jjapcloud.web.dto.RequestSuccessDto;
import com.iucyh.jjapcloud.web.dto.user.CreateUserDto;
import com.iucyh.jjapcloud.web.dto.user.MyUserDto;
import com.iucyh.jjapcloud.web.dto.user.UpdateUserDto;
import com.iucyh.jjapcloud.web.dto.user.UserDto;

public interface UserService {

    UserDto getUserById(int id);
    MyUserDto getMyUserById(int id);
    IdDto createUser(CreateUserDto userDto);
    RequestSuccessDto updateUser(int id, UpdateUserDto userDto);
    RequestSuccessDto deleteUser(int id);
}
