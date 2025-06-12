package com.iucyh.jjapcloud.domain.playlist;

import com.iucyh.jjapcloud.domain.music.Music;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "playlist_items")
@Getter
public class PlaylistItem {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer position;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "music_id")
    private Music music;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "playlist_id")
    private Playlist playlist;

    protected PlaylistItem() {}

    public PlaylistItem(Integer position, Music music, Playlist playlist) {
        this.position = position;
        this.music = music;
        this.playlist = playlist;
    }

    public void changePosition(Integer position) {
        this.position = position;
    }
}
