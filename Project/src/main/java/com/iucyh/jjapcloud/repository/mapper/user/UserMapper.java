package com.iucyh.jjapcloud.repository.mapper.user;

import com.iucyh.jjapcloud.domain.user.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Optional;

@Mapper
public interface UserMapper {

    Optional<User> find(int id);
    Optional<User> findByEmail(String email);
    int create(User user);
    void update(@Param("id") int id, @Param("newUser") User newUser);
    void delete(int id);
}
