package com.project.pet.repository;

import com.project.pet.domain.User;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.jdbc.Sql;

import java.util.Arrays;
import java.util.Collection;

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

    private final JdbcUserRepository userRepository;

    @Autowired
    public JdbcUserRepositoryTest(NamedParameterJdbcTemplate jdbcTemplate) {

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
}
