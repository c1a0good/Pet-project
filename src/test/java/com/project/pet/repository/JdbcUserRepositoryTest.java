package com.project.pet.repository;

import com.project.pet.domain.User;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.jdbc.Sql;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests for {@link JdbcUserRepository}.
 * <p/>
 * 08.12.21
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DataJdbcTest
@Sql({"classpath:sqlScripts/users.sql", "classpath:testData/users_data.sql"})
public class JdbcUserRepositoryTest {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    private final JdbcUserRepository userRepository;

    @Autowired
    public JdbcUserRepositoryTest(NamedParameterJdbcTemplate jdbcTemplate) {

        this.jdbcTemplate = jdbcTemplate;

        this.userRepository = new JdbcUserRepository(jdbcTemplate);
    }

    @Test
    public void shouldReadAllUsers() {

        Collection expectedUsers = Arrays.asList(
            new User(1, "name1", "surname1", 18, "+375291234567"),
            new User(2, "name2", "surname2", 20, "+375292345678"),
            new User(3, "name3", "surname3", 22, "+375293456789")
        );

        Collection<User> actualUsers = userRepository.readAll();

        assertEquals(expectedUsers, actualUsers);
    }

    @Test
    public void shouldReadUserById() {

        User expectedUser = new User(1, "name1", "surname1", 18, "+375291234567");

        User actualUser = userRepository.readById(1L);

        assertEquals(expectedUser, actualUser);
    }

    @Test
    public void shouldCreateUser() {

        User expectedUser = new User(4, "name4", "surname4", 24, "+375291234568");

        assertEquals(4L, userRepository.create(expectedUser));

        User actualUser = readById(4L);

        assertEquals(expectedUser, actualUser);
    }

    @Test
    public void shouldUpdateUser() {

        User expectedUser = new User(1, "newName1", "newSurname1", 19, "+375291234569");

        userRepository.update(expectedUser);

        User actualUser = readById(1L);

        assertEquals(expectedUser, actualUser);
    }

    @Test
    public void shouldDeleteUser() {

        Collection expectedUsers = Arrays.asList(
                new User(2, "name2", "surname2", 20, "+375292345678"),
                new User(3, "name3", "surname3", 22, "+375293456789")
        );

        userRepository.delete(1L);

        Collection<User> actualUsers = readAll();

        assertEquals(expectedUsers, actualUsers);
    }

    private User readById(Long id) {

        String sql = "SELECT * FROM users " +
                "WHERE user_id = :user_id";

        MapSqlParameterSource params = new MapSqlParameterSource();

        params.addValue("user_id", id);

        return getUsersThroughSqlQuery(sql, params).get(0);
    }

    private Collection<User> readAll() {

        String sql = "SELECT * FROM users";

        return getUsersThroughSqlQuery(sql, new MapSqlParameterSource());
    }

    private List<User> getUsersThroughSqlQuery(String sql, MapSqlParameterSource params) {

        return jdbcTemplate.query(sql, params,
                (resultSet, i) -> {
                    User user = new User();

                    user.setId(resultSet.getLong("user_id"));
                    user.setName(resultSet.getString("name"));
                    user.setSurname(resultSet.getString("surname"));
                    user.setAge(resultSet.getInt("age"));
                    user.setPhoneNumber(resultSet.getString("phone_number"));

                    return user;
                });
    }


}
