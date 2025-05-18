package com.iucyh.jjapcloud.domain.user.repository;

import com.iucyh.jjapcloud.domain.user.User;

public interface UserRepository {

    User find(int id);
    int create(User user);
    void update(int id, User newUser);
    void delete(int id);
}
