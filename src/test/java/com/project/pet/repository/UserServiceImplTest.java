package com.project.pet.repository;

import com.project.pet.domain.User;
import com.project.pet.service.UserServiceImpl;
import org.easymock.Capture;
import org.easymock.EasyMock;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collection;

import static org.easymock.EasyMock.*;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class UserServiceImplTest {
    private final UserRepository userRepository = createMock(UserRepository.class);

    private final UserServiceImpl userService = new UserServiceImpl(userRepository);

    @Test
    public void shouldCreateUser() {

        Capture<User> capturedArgument = EasyMock.newCapture();

        expect(userRepository.create(capture(capturedArgument))).andReturn(1L);
        expectLastCall().once();

        replay(userRepository);

        User expectedUser = new User(1, "name1", "surname1", 18, "+375291234567");

        assertEquals(expectedUser, userService.create(expectedUser));

        verify(userRepository);

        assertEquals(expectedUser, capturedArgument.getValue());
    }

    @Test
    public void shouldReadAllUsers() {

        Collection<User> expectedUsers = Arrays.asList(
                new User(2, "name2", "surname2", 20, "+375292345678"),
                new User(3, "name3", "surname3", 22, "+375293456789")
        );

        expect(userRepository.readAll()).andReturn(expectedUsers);

        replay(userRepository);

        Collection<User> actualUsers = userService.readAll();

        assertEquals(expectedUsers, actualUsers);
    }

    @Test
    public void shouldReadUserById() {

        User expectedUser = new User(1, "name1", "surname1", 18, "+375291234567");

        expect(userRepository.readById(1L)).andReturn(expectedUser);

        replay(userRepository);

        User actualUser = userService.readById(1L);

        assertEquals(expectedUser, actualUser);
    }

    @Test
    public void shouldUpdateUser() {

        Capture<User> capturedArgument = EasyMock.newCapture();

        userRepository.update(capture(capturedArgument));
        expectLastCall().once();

        replay(userRepository);

        User expectedUser = new User(1, "name1", "surname1", 18, "+375291234567");

        userService.update(expectedUser);

        verify(userRepository);

        assertEquals(expectedUser, capturedArgument.getValue());
    }

    @Test
    public void shouldDeleteUser() {

        Capture<Long> capturedArgument = EasyMock.newCapture();

        userRepository.delete(capture(capturedArgument));
        expectLastCall().once();

        replay(userRepository);

        Long expectedId = 1L;

        userService.delete(expectedId);

        verify(userRepository);

        assertEquals(expectedId, capturedArgument.getValue());
    }
}
