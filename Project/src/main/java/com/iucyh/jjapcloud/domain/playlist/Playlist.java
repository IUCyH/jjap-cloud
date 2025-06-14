package com.iucyh.jjapcloud.domain.playlist;

import com.iucyh.jjapcloud.domain.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

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

    @CreationTimestamp
    public LocalDateTime createdAt;

    @UpdateTimestamp
    public LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public Playlist() {}

    public Playlist(String publicId, String name, User user) {
        this.publicId = publicId;
        this.name = name;
        this.user = user;
    }
}
