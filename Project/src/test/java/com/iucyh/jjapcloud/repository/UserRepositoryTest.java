package com.iucyh.jjapcloud.repository;

import com.iucyh.jjapcloud.domain.user.User;
import com.iucyh.jjapcloud.repository.user.UserRepository;
import com.iucyh.jjapcloud.repository.user.UserRepositoryMemoryImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

class UserRepositoryTest {

    private final UserRepository repository = new UserRepositoryMemoryImpl();

    @AfterEach
    void afterEach() {
        if(repository instanceof UserRepositoryMemoryImpl) {
            ((UserRepositoryMemoryImpl)repository).clearAll();
        }
    }

    @Test
    @DisplayName("유저 저장 성공")
    void save() {
        int id = saveTestUser();
        Optional<User> foundUser = repository.find(1);

        assertThat(id).isEqualTo(1);
        assertThat(foundUser.isPresent()).isTrue();
        assertThat(foundUser.get().getNickname()).isEqualTo("test");
    }

    @Test
    @DisplayName("유저 조회 성공")
    void find() {
        int id = saveTestUser();
        Optional<User> foundUser = repository.find(id);

        assertThat(id).isEqualTo(1);
        assertThat(foundUser.isPresent()).isTrue();
        assertThat(foundUser.get().getNickname()).isEqualTo("test");
    }

    @Test
    @DisplayName("유저 이메일로 조회 성공")
    void findByEmail() {
        int id = saveTestUser();
        Optional<User> foundUser = repository.findByEmail("abc@abc.com");

        assertThat(id).isEqualTo(1);
        assertThat(foundUser.isPresent()).isTrue();
        assertThat(foundUser.get().getNickname()).isEqualTo("test");
    }

    @Test
    @DisplayName("유저 조회 실패")
    void findFail() {
        int id = saveTestUser();
        Optional<User> foundUser = repository.find(id + 1);

        assertThat(foundUser.isPresent()).isFalse();
    }

    @Test
    @DisplayName("유저 이메일로 조회 실패")
    void findByEmailFail() {
        int id = saveTestUser();
        Optional<User> foundUser = repository.findByEmail("fail@fail.com");

        assertThat(foundUser.isPresent()).isFalse();
    }

    @Test
    @DisplayName("유저 업데이트 성공")
    void update() {
        int id = saveTestUser();
        Optional<User> foundUser = repository.find(id);
        assertThat(foundUser.isPresent()).isTrue();

        User updateUser = new User();
        updateUser.setId(id);
        updateUser.setNickname("update");

        repository.update(id, updateUser);
        Optional<User> foundUpdateUser = repository.find(id);

        assertThat(foundUpdateUser.isPresent()).isTrue();
        assertThat(foundUpdateUser.get().getNickname()).isEqualTo("update");
    }

    @Test
    @DisplayName("유저 삭제 성공")
    void delete() {
        int id = saveTestUser();
        Optional<User> foundUser = repository.find(id);
        assertThat(foundUser.isPresent()).isTrue();

        repository.delete(id);

        Optional<User> deletedUser = repository.find(id);
        assertThat(deletedUser.isPresent()).isFalse();
    }

    private int saveTestUser() {
        User user = new User();
        user.setNickname("test");
        user.setPassword("123456");
        user.setEmail("abc@abc.com");

        return repository.create(user);
    }
}
