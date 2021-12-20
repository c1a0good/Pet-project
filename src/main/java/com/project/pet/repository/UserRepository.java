package com.project.pet.repository;

import com.project.pet.domain.User;

import java.util.Collection;

public interface UserRepository {
    Long create(User user);
    Collection<User> readAll();
    User readById(Long id);
    void update(User user);
    void delete(Long id);
}
