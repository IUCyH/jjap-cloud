package com.iucyh.jjapcloud.web.controller;

import com.iucyh.jjapcloud.web.common.annotation.LoginUser;
import com.iucyh.jjapcloud.web.dto.IdDto;
import com.iucyh.jjapcloud.web.dto.RequestSuccessDto;
import com.iucyh.jjapcloud.web.dto.user.*;
import com.iucyh.jjapcloud.web.service.user.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    public MyUserDto getMyUser(@LoginUser UserInfoDto user) {
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

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public RequestSuccessDto updateUser(@PathVariable int id, @Validated @RequestBody UpdateUserDto userDto) {
        userService.updateUser(id, userDto);
        return new RequestSuccessDto("User Update Success");
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public RequestSuccessDto deleteUser(@PathVariable int id) {
        userService.deleteUser(id);
        return new RequestSuccessDto("User Delete Success");
    }
}
