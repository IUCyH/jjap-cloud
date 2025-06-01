package com.iucyh.jjapcloud.repository.user;

import com.iucyh.jjapcloud.domain.user.User;
import com.iucyh.jjapcloud.dto.user.query.UserSimpleDto;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepositoryJpaImpl implements UserRepository {

    private final EntityManager em;

    @Override
    public Optional<User> find(int id) {
        User user = em.find(User.class, id);
        return Optional.ofNullable(user);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        String jpql = "select new com.iucyh.jjapcloud.dto.user.query.UserSimpleDto(u.id, u.nickname, u.email) from User u where u.email = :email";

        return em.createQuery(jpql, UserSimpleDto.class)
                .setParameter("email", email)
                .getResultList()
                .stream()
                .findFirst()
                .map(UserSimpleDto::toEntity);
    }

    @Override
    public int create(User user) {
        em.persist(user);
        return user.getId();
    }

    @Override
    public void update(int id, User newUser) {
        User user = em.find(User.class, id);
        user.setNickname(newUser.getNickname());
        user.setPassword(newUser.getPassword());
    }

    @Override
    public void delete(int id) {
        User user = em.find(User.class, id);
        em.remove(user);
    }
}
