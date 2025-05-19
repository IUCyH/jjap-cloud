package com.iucyh.jjapcloud.domain.user.repository;

import com.iucyh.jjapcloud.domain.user.User;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class UserRepositoryMemoryImpl implements UserRepository {

    private static final Map<Integer, User> userStore = new ConcurrentHashMap<>();
    private static Integer sequence = 0;

    @Override
    public Optional<User> find(int id) {
        return Optional.ofNullable(userStore.get(id));
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userStore.values().stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst();
    }

    @Override
    public int create(User user) {
        user.setId(++sequence);
        userStore.put(user.getId(), user);
        return user.getId();
    }

    @Override
    public void update(int id, User newUser) {
        User user = userStore.get(id);
        bindUser(newUser, user);

        userStore.put(id, user);
    }

    @Override
    public void delete(int id) {
        userStore.remove(id);
    }

    private void bindUser(User newUser, User user) {
        if(newUser.getEmail() != null) {
            user.setEmail(newUser.getEmail());
        }

        if(newUser.getNickname() != null) {
            user.setNickname(newUser.getNickname());
        }

        if(newUser.getPassword() != null) {
            user.setPassword(newUser.getPassword());
        }
    }
}
