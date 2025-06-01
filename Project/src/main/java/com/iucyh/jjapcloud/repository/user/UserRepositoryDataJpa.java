package com.iucyh.jjapcloud.repository.user;

import com.iucyh.jjapcloud.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepositoryDataJpa extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);
}
