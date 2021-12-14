package com.project.pet.service;

import com.project.pet.domain.User;
import com.project.pet.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User create(User user) {
        user.setId(userRepository.create(user));
        return user;
    }

    @Override
    public Collection<User> readAll() {
        return userRepository.readAll();
    }

    @Override
    public User readById(Long id) {
        return userRepository.readById(id);
    }

    @Override
    public void update(User user) {
        userRepository.update(user);
    }

    @Override
    public void delete(Long id) {
        userRepository.delete(id);
    }
}
