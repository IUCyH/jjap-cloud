package com.iucyh.jjapcloud.repository.user;

import com.iucyh.jjapcloud.domain.user.User;
import com.iucyh.jjapcloud.repository.mapper.user.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepositoryMyBatisImpl implements UserRepository {

    private final UserMapper userMapper;

    @Override
    public Optional<User> find(int id) {
        return userMapper.find(id);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userMapper.findByEmail(email);
    }

    @Override
    public int create(User user) {
        return userMapper.create(user);
    }

    @Override
    public void update(int id, User newUser) {
        userMapper.update(id, newUser);
    }

    @Override
    public void delete(int id) {
        userMapper.delete(id);
    }
}
