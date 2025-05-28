package com.iucyh.jjapcloud.web.controller;

import com.iucyh.jjapcloud.common.annotation.loginuser.LoginUser;
import com.iucyh.jjapcloud.common.annotation.loginuser.UserInfo;
import com.iucyh.jjapcloud.web.dto.IdDto;
import com.iucyh.jjapcloud.web.dto.ResponseDto;
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
    public ResponseDto<MyUserDto> getMyUser(@LoginUser UserInfo user) {
        MyUserDto result = userService.getMyUserById(user.getId());
        return ResponseDto
                .success("Get my user success", result);
    }

    @GetMapping("/{id}")
    public ResponseDto<UserDto> getUserById(@PathVariable int id) {
        UserDto result = userService.getUserById(id);
        return ResponseDto
                .success("Get user success", result);
    }

    @PostMapping
    public ResponseDto<IdDto> createUser(@Validated @RequestBody CreateUserDto userDto) {
        IdDto id = userService.createUser(userDto);
        return ResponseDto
                .success("Create user success", id);
    }

    @PatchMapping("/me")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDto<Void> updateUser(@LoginUser UserInfo user, @Validated @RequestBody UpdateUserDto userDto) {
        userService.updateUser(user.getId(), userDto);
        return ResponseDto
                .success("Update user success", null);
    }

    @DeleteMapping("/me")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDto<Void> deleteUser(@LoginUser UserInfo user) {
        userService.deleteUser(user.getId());
        return ResponseDto
                .success("Delete user success", null);
    }
}
