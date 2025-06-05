package com.iucyh.jjapcloud;

import com.iucyh.jjapcloud.repository.music.MusicQueryRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RepositoryConfig {

    @Bean
    public MusicQueryRepository musicPagingRepository(EntityManager em) {
        return new MusicQueryRepository(em);
    }
}
