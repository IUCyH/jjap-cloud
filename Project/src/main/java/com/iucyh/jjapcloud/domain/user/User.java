package com.iucyh.jjapcloud.domain.user;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Entity
@Table(
        name = "users",
        indexes = {
                @Index(name = "idx_email", columnList = "email"),
                @Index(name = "idx_nickname", columnList = "nickname")
        }
)
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 32, unique = true, nullable = false)
    private String publicId;

    @Column(length = 64, unique = true, nullable = false)
    private String nickname;

    @Column(length = 128, nullable = false)
    private String email;

    @Column(length = 128, nullable = false)
    private String password;

    protected User() {}

    public User(String nickname, String email, String password) {
        this.nickname = nickname;
        this.email = email;
        this.password = password;
        this.publicId = UUID.randomUUID().toString().replace("-", "");
    }

    public boolean isCorrectPassword(String password) {
        return this.password.equals(password);
    }

    public void setNickname(String nickname) {
        if(nickname != null) {
            this.nickname = nickname;
        }
    }

    public void setPassword(String password) {
        if(password != null) {
            this.password = password;
        }
    }
}
