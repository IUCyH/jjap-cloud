package com.iucyh.jjapcloud.repository;

import com.iucyh.jjapcloud.domain.user.User;
import com.iucyh.jjapcloud.repository.user.UserRepository;
import com.iucyh.jjapcloud.repository.user.UserRepositoryJDBCImpl;
import com.iucyh.jjapcloud.repository.user.UserRepositoryMemoryImpl;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class UserRepositoryTest {

    @Autowired
    private UserRepository repository;

    @AfterEach
    void afterEach() {
        if(repository instanceof UserRepositoryMemoryImpl) {
            ((UserRepositoryMemoryImpl)repository).clearAll();
        }
    }

    @Test
    @DisplayName("유저 저장 성공")
    void save() {
        String uniqueString = UUID.randomUUID().toString();
        int id = saveTestUser(uniqueString);
        Optional<User> foundUser = repository.find(id);

        assertThat(foundUser.isPresent()).isTrue();
        assertThat(foundUser.get().getId()).isEqualTo(id);
        assertThat(foundUser.get().getNickname()).isEqualTo(uniqueString);
    }

    @Test
    @DisplayName("유저 조회 성공")
    void find() {
        String uniqueString = UUID.randomUUID().toString();
        int id = saveTestUser(uniqueString);
        Optional<User> foundUser = repository.find(id);

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
        Optional<User> foundUser = repository.find(id + 1);

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
        String uniqueString = UUID.randomUUID().toString();
        int id = saveTestUser(uniqueString);
        Optional<User> foundUser = repository.find(id);
        assertThat(foundUser.isPresent()).isTrue();

        repository.delete(id);

        Optional<User> deletedUser = repository.find(id);
        assertThat(deletedUser.isPresent()).isFalse();
    }

    private int saveTestUser(String uniqueString) {
        User user = new User();
        user.setNickname(uniqueString);
        user.setPassword("123456");
        user.setEmail(uniqueString + "@abc.com");

        return repository.create(user);
    }
}
