package com.iucyh.jjapcloud.controller;

import com.iucyh.jjapcloud.dto.user.CreateUserDto;
import com.iucyh.jjapcloud.dto.user.MyUserDto;
import com.iucyh.jjapcloud.dto.user.UpdateUserDto;
import com.iucyh.jjapcloud.dto.user.UserDto;
import com.iucyh.jjapcloud.common.annotation.loginuser.LoginUserId;
import com.iucyh.jjapcloud.dto.IdDto;
import com.iucyh.jjapcloud.dto.ResponseDto;
import com.iucyh.jjapcloud.service.UserService;
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
    public ResponseDto<MyUserDto> getMyUser(@LoginUserId Long userId) {
        MyUserDto result = userService.getMyUserById(userId);
        return ResponseDto
                .success("Get my user success", result);
    }

    @GetMapping("/{publicId}")
    public ResponseDto<UserDto> getUserById(@PathVariable String publicId) {
        UserDto result = userService.getUserById(publicId);
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
    public ResponseDto<Void> updateUser(@LoginUserId Long userId, @Validated @RequestBody UpdateUserDto userDto) {
        userService.updateUser(userId, userDto);
        return ResponseDto
                .success("Update user success", null);
    }

    @DeleteMapping("/me")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDto<Void> deleteUser(@LoginUserId Long userId) {
        userService.deleteUser(userId);
        return ResponseDto
                .success("Delete user success", null);
    }
}
