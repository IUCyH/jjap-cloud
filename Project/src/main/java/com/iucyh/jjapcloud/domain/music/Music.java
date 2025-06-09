package com.iucyh.jjapcloud.domain.music;

import com.iucyh.jjapcloud.domain.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(
        name = "musics",
        indexes = {
                @Index(name = "idx_name", columnList = "name")
        }
)
@Getter
public class Music {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 32, unique = true, nullable = false)
    private String publicId;

    @Column(length = 100, nullable = false)
    private String title;

    @Column(length = 64)
    private String storeName;

    @Column(nullable = false)
    private Long playTime;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    protected Music() {}

    public Music(String title, String storeName, User user, Long playTime) {
        this.title = title;
        this.storeName = storeName;
        this.user = user;
        this.playTime = playTime;
        this.publicId = UUID.randomUUID().toString().replace("-", "");
    }
}
