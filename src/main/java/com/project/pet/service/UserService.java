package com.project.pet.service;

import com.project.pet.domain.User;

import java.util.Collection;

public interface UserService {
    User create(User user);
    Collection<User> readAll();
    User readById(Long id);
    void update(User user);
    void delete(Long id);
}
