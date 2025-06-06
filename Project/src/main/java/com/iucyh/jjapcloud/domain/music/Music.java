package com.iucyh.jjapcloud.domain.music;

import com.iucyh.jjapcloud.domain.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(
        name = "musics",
        indexes = {
                @Index(name = "idx_name", columnList = "name")
        }
)
public class Music {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 100, nullable = false)
    private String name;

    @Column(length = 64)
    private String storeName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private Long playTime;

    @CreationTimestamp
    private LocalDateTime createTime;

    public Music() {}

    public Music(String name, String storeName, User user, Long playTime) {
        this.name = name;
        this.storeName = storeName;
        this.user = user;
        this.playTime = playTime;
    }
}
