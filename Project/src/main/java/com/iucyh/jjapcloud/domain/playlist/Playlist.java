package com.iucyh.jjapcloud.domain.playlist;

import com.iucyh.jjapcloud.domain.music.Music;
import com.iucyh.jjapcloud.domain.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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

    @OneToMany(mappedBy = "playlist", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<PlaylistItem> items = new ArrayList<>();

    protected Playlist() {}

    public Playlist(String name, User user) {
        this.name = name;
        this.user = user;
        this.itemCount = 0;
        this.publicId = UUID.randomUUID().toString().replace("-", "");
    }

    public void addMusic(Music music) {
        itemCount++;

        PlaylistItem item = new PlaylistItem(itemCount, music, this);
        items.add(item);
    }

    public void removeMusic(Music music) {
        boolean removed = items.removeIf(item -> item.getMusic().equals(music));
        if (!removed) return;

        for (int i = 0; i < items.size(); i++) {
            items.get(i).changePosition(i + 1);
        }

        itemCount = items.size();
    }

    public void setName(String name) {
        if(name != null) {
            this.name = name;
        }
    }
}
