package com.iucyh.jjapcloud.domain.playlist;

import com.iucyh.jjapcloud.domain.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(
        name = "playlists",
        indexes = {
                @Index(name = "idx_public_id", columnList = "public_id")
        }
)
@Getter
public class Playlist {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 32, unique = true, nullable = false)
    private String publicId;

    @Column(length = 50, nullable = false)
    private String name;

    private Integer itemCount;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    protected Playlist() {}

    public Playlist(String name, User user) {
        this.name = name;
        this.user = user;
        this.itemCount = 0;
        this.publicId = UUID.randomUUID().toString().replace("-", "");
    }

    public void setName(String name) {
        if(name != null) {
            this.name = name;
        }
    }
}
