package com.iucyh.jjapcloud;

import com.iucyh.jjapcloud.repository.mapper.user.UserMapper;
import com.iucyh.jjapcloud.repository.music.MusicRepository;
import com.iucyh.jjapcloud.repository.music.MusicRepositoryMemoryImpl;
import com.iucyh.jjapcloud.repository.user.UserRepository;
import com.iucyh.jjapcloud.repository.user.UserRepositoryMyBatisImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class RepositoryConfig {

    private final UserMapper userMapper;

    @Bean
    public UserRepository userRepository() {
        return new UserRepositoryMyBatisImpl(userMapper);
    }

    @Bean
    public MusicRepository musicRepository() {
        return new MusicRepositoryMemoryImpl();
    }
}
