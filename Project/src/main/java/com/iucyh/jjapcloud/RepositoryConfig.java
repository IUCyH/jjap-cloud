package com.iucyh.jjapcloud;

import com.iucyh.jjapcloud.repository.music.MusicRepository;
import com.iucyh.jjapcloud.repository.music.MusicRepositoryMemoryImpl;
import com.iucyh.jjapcloud.repository.user.UserRepository;
import com.iucyh.jjapcloud.repository.user.UserRepositoryJpaImpl;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class RepositoryConfig {

    private final EntityManager em;

    @Bean
    public UserRepository userRepository() {
        return new UserRepositoryJpaImpl(em);
    }

    @Bean
    public MusicRepository musicRepository() {
        return new MusicRepositoryMemoryImpl();
    }
}
