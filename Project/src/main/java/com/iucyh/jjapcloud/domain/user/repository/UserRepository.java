package com.iucyh.jjapcloud.domain.user.repository;

import com.iucyh.jjapcloud.domain.user.User;

import java.util.Optional;

public interface UserRepository {

    Optional<User> find(int id);
    int create(User user);
    void update(int id, User newUser);
    void delete(int id);
}
