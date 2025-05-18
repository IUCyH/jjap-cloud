package com.iucyh.jjapcloud.web.controller;

import com.iucyh.jjapcloud.web.dto.IdDto;
import com.iucyh.jjapcloud.web.dto.user.CreateUserDto;
import com.iucyh.jjapcloud.web.dto.user.UpdateUserDto;
import com.iucyh.jjapcloud.web.dto.user.UserDto;
import com.iucyh.jjapcloud.web.service.user.UserService;
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

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable int id) {
        UserDto user = userService.getUserById(id);
        if(user == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(user);
    }

    @PostMapping
    public ResponseEntity<IdDto> createUser(@Validated @RequestBody CreateUserDto userDto) {
        IdDto result = userService.createUser(userDto);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void updateUser(@PathVariable int id, @Validated @RequestBody UpdateUserDto userDto) {
        userService.updateUser(id, userDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteUser(@PathVariable int id) {
        userService.deleteUser(id);
    }
}
