package com.iucyh.jjapcloud;

import com.iucyh.jjapcloud.repository.music.MusicRepository;
import com.iucyh.jjapcloud.repository.music.MusicRepositoryMemoryImpl;
import com.iucyh.jjapcloud.repository.user.UserRepository;
import com.iucyh.jjapcloud.repository.user.UserRepositoryJDBCImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
@RequiredArgsConstructor
public class RepositoryConfig {

    private final DataSource dataSource;

    @Bean
    public UserRepository userRepository() {
        return new UserRepositoryJDBCImpl(dataSource);
    }

    @Bean
    public MusicRepository musicRepository() {
        return new MusicRepositoryMemoryImpl();
    }
}
