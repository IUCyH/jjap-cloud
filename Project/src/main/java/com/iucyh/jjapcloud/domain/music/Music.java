package com.iucyh.jjapcloud.domain.music;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(
        name = "musics",
        indexes = {
                @Index(name = "idx_original_name", columnList = "original_name"),
                @Index(name = "idx_singer", columnList = "singer")
        }
)
public class Music {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 100, nullable = false)
    private String originalName;

    @Column(length = 64)
    private String storeName;

    @Column(length = 20, nullable = false)
    private String singer;

    private Long playTime;

    @CreationTimestamp
    private LocalDateTime createTime;

    public Music() {}
}
