package com.iucyh.jjapcloud.web.controller;

import com.iucyh.jjapcloud.web.dto.IdDto;
import com.iucyh.jjapcloud.web.dto.user.CreateUserDto;
import com.iucyh.jjapcloud.web.dto.user.UpdateUserDto;
import com.iucyh.jjapcloud.web.dto.user.UserDto;
import com.iucyh.jjapcloud.web.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/{id}")
    public UserDto getUserById(@PathVariable int id) {
        return userService.getUserById(id);
    }

    @PostMapping
    public IdDto createUser(@Validated @RequestBody CreateUserDto userDto) {
        return userService.createUser(userDto);
    }

    @PatchMapping("/{id}")
    public void updateUser(@PathVariable int id, @Validated @RequestBody UpdateUserDto userDto) {
        userService.updateUser(id, userDto);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable int id) {
        userService.deleteUser(id);
    }
}
