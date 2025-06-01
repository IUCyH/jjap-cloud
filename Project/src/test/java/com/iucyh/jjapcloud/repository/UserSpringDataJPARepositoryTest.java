package com.iucyh.jjapcloud.repository;

import com.iucyh.jjapcloud.domain.user.User;
import com.iucyh.jjapcloud.repository.user.UserRepository;
import com.iucyh.jjapcloud.repository.user.UserRepositoryDataJpa;
import com.iucyh.jjapcloud.repository.user.UserRepositoryMemoryImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class UserSpringDataJPARepositoryTest {

    @Autowired
    private UserRepositoryDataJpa repository;

    @Test
    @DisplayName("유저 저장 성공")
    void save() {
        String uniqueString = UUID.randomUUID().toString();
        int id = saveTestUser(uniqueString);
        Optional<User> foundUser = repository.findById(id);

        assertThat(foundUser.isPresent()).isTrue();
        assertThat(foundUser.get().getId()).isEqualTo(id);
        assertThat(foundUser.get().getNickname()).isEqualTo(uniqueString);
    }

    @Test
    @DisplayName("유저 조회 성공")
    void find() {
        String uniqueString = UUID.randomUUID().toString();
        int id = saveTestUser(uniqueString);
        Optional<User> foundUser = repository.findById(id);

        assertThat(foundUser.isPresent()).isTrue();
        assertThat(foundUser.get().getId()).isEqualTo(id);
        assertThat(foundUser.get().getNickname()).isEqualTo(uniqueString);
    }

    @Test
    @DisplayName("유저 이메일로 조회 성공")
    void findByEmail() {
        String uniqueString = UUID.randomUUID().toString();
        int id = saveTestUser(uniqueString);
        Optional<User> foundUser = repository.findByEmail(uniqueString + "@abc.com");

        assertThat(foundUser.isPresent()).isTrue();
        assertThat(foundUser.get().getId()).isEqualTo(id);
        assertThat(foundUser.get().getNickname()).isEqualTo(uniqueString);
    }

    @Test
    @DisplayName("유저 조회 실패")
    void findFail() {
        String uniqueString = UUID.randomUUID().toString();
        int id = saveTestUser(uniqueString);
        Optional<User> foundUser = repository.findById(id + 1);

        assertThat(foundUser.isPresent()).isFalse();
    }

    @Test
    @DisplayName("유저 이메일로 조회 실패")
    void findByEmailFail() {
        String uniqueString = UUID.randomUUID().toString();
        int id = saveTestUser(uniqueString);

        Optional<User> foundUser = repository.findByEmail("fail@fail.com");

        assertThat(foundUser.isPresent()).isFalse();
    }

    @Test
    @DisplayName("유저 업데이트 성공")
    void update() {
        String uniqueString = UUID.randomUUID().toString();
        int id = saveTestUser(uniqueString);

        User foundUser = repository.findById(id).orElseThrow();
        foundUser.setNickname("update");
        foundUser.setPassword("1234");

        Optional<User> foundUpdateUser = repository.findById(id);

        assertThat(foundUpdateUser.isPresent()).isTrue();
        assertThat(foundUpdateUser.get().getNickname()).isEqualTo("update");
    }

    @Test
    @DisplayName("유저 삭제 성공")
    void delete() {
        String uniqueString = UUID.randomUUID().toString();
        int id = saveTestUser(uniqueString);
        Optional<User> foundUser = repository.findById(id);
        assertThat(foundUser.isPresent()).isTrue();

        repository.delete(foundUser.get());

        Optional<User> deletedUser = repository.findById(id);
        assertThat(deletedUser.isPresent()).isFalse();
    }

    private int saveTestUser(String uniqueString) {
        User user = new User();
        user.setNickname(uniqueString);
        user.setPassword("123456");
        user.setEmail(uniqueString + "@abc.com");

        return repository.save(user).getId();
    }
}
