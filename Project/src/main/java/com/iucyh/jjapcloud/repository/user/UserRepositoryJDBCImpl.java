package com.iucyh.jjapcloud.repository.user;

import com.iucyh.jjapcloud.domain.user.User;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Map;
import java.util.Optional;

@Repository
public class UserRepositoryJDBCImpl implements UserRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    public UserRepositoryJDBCImpl(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.jdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("users")
                .usingGeneratedKeyColumns("id")
                .usingColumns("nickname", "email", "password");
    }

    @Override
    public Optional<User> find(int id) {
        String sql = "select id, nickname, email from users where id = :id";

        try {
            Map<String, Integer> param = Map.of("id", id);
            User user = jdbcTemplate.queryForObject(sql, param, userRowMapper());
            return Optional.ofNullable(user);
        } catch (DataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<User> findByEmail(String email) {
        String sql = "select id, nickname, email from users where email = :email";

        try {
            Map<String, String> param = Map.of("email", email);
            User user = jdbcTemplate.queryForObject(sql, param, userRowMapper());
            return Optional.ofNullable(user);
        } catch (DataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public int create(User user) {
        BeanPropertySqlParameterSource params = new BeanPropertySqlParameterSource(user);
        Number id = jdbcInsert.executeAndReturnKey(params);
        return id.intValue();
    }

    @Override
    public void update(int id, User newUser) {
        String sql = "update users ";
        MapSqlParameterSource params = new MapSqlParameterSource();
        boolean and = false;

        if(newUser.getNickname() != null || newUser.getPassword() != null) {
            sql += "set ";
        }

        if(newUser.getNickname() != null) {
            sql += "nickname = :nickname";
            params.addValue("nickname", newUser.getNickname());
            and = true;
        }

        if(newUser.getPassword() != null) {
            if(and) {
                sql += ", password = :password";
            } else {
                sql += "password = :password";
            }
            params.addValue("password", newUser.getPassword());
        }

        sql += " where id = :id";
        params.addValue("id", id);
        jdbcTemplate.update(sql, params);
    }

    @Override
    public void delete(int id) {
        String sql = "delete from users where id = :id";
        Map<String, Integer> param = Map.of("id", id);
        jdbcTemplate.update(sql, param);
    }

    private RowMapper<User> userRowMapper() {
        return BeanPropertyRowMapper.newInstance(User.class);
    }
}
