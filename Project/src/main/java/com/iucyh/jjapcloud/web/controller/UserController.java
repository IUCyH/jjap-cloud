package com.iucyh.jjapcloud.web.controller;

import com.iucyh.jjapcloud.common.annotation.loginuser.LoginUser;
import com.iucyh.jjapcloud.common.annotation.loginuser.UserInfo;
import com.iucyh.jjapcloud.web.dto.IdDto;
import com.iucyh.jjapcloud.web.dto.RequestSuccessDto;
import com.iucyh.jjapcloud.web.dto.user.*;
import com.iucyh.jjapcloud.web.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    public MyUserDto getMyUser(@LoginUser UserInfo user) {
        return userService.getMyUserById(user.getId());
    }

    @GetMapping("/{id}")
    public UserDto getUserById(@PathVariable int id) {
        return userService.getUserById(id);
    }

    @PostMapping
    public IdDto createUser(@Validated @RequestBody CreateUserDto userDto) {
        return userService.createUser(userDto);
    }

    @PatchMapping("/me")
    @ResponseStatus(HttpStatus.OK)
    public RequestSuccessDto updateUser(@LoginUser UserInfo user, @Validated @RequestBody UpdateUserDto userDto) {
        return userService.updateUser(user.getId(), userDto);
    }

    @DeleteMapping("/me")
    @ResponseStatus(HttpStatus.OK)
    public RequestSuccessDto deleteUser(@LoginUser UserInfo user) {
        return userService.deleteUser(user.getId());
    }
}
