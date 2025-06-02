package com.iucyh.jjapcloud.domain.music;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "musics")
public class Music {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String originalName;
    private String storeName;
    private String singer;
    private Long playTime;
    @CreationTimestamp
    private LocalDateTime createTime;

    public Music() {}
}
