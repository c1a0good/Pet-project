package com.project.pet.repository;


import com.project.pet.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

@Repository
public class JdbcUserRepository implements UserRepository{
    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcUserRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void create(User user) {
        String sql = "INSERT INTO users " +
                "(name, surname, age, phone_number)" +
                "VALUES " +
                "(:name, :surname, :age, :phone_number)";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("name", user.getName())
                .addValue("surname", user.getSurname())
                .addValue("age", user.getAge())
                .addValue("phone_number", user.getPhoneNumber());
        jdbcTemplate.update(sql, params);
    }

    @Override
    public Collection<User> readAll() {
        String sql = "SELECT * FROM users";
        return jdbcTemplate.query(sql, new UserMapping());
    }

    @Override
    public User readById(Long id) {
        String sql = "SELECT * FROM users " +
                "WHERE user_id = :user_id";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("user_id", id);
        return jdbcTemplate.query(sql, params, new UserMapping()).get(0);
    }

    @Override
    public void update(User user) {
        String sql = "UPDATE users SET " +
                "name = :name, surname = :surname, age = :age, phone_number = :phone_number " +
                "WHERE user_id = :user_id";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("name", user.getName())
                .addValue("surname", user.getSurname())
                .addValue("age", user.getAge())
                .addValue("phone_number", user.getPhoneNumber())
                .addValue("user_id", user.getId());
        jdbcTemplate.update(sql, params);
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM users " +
                "WHERE user_id = :user_id";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("user_id", id);
        jdbcTemplate.update(sql, params);
    }

    private static class UserMapping implements RowMapper<User> {
        @Override
        public User mapRow(ResultSet resultSet, int i) throws SQLException {
            User user = new User();
            user.setId(resultSet.getLong("user_id"));
            user.setName(resultSet.getString("name"));
            user.setSurname(resultSet.getString("surname"));
            user.setAge(resultSet.getInt("age"));
            user.setPhoneNumber(resultSet.getString("phone_number"));
            return user;
        }
    }
}
