package com.bilal.taskmanager.repository;

import java.util.Optional;

import com.bilal.taskmanager.model.User;

public interface UserRepository {
	Optional<User> findByEmail(String email);

    void save(User user);

    boolean existsByEmail(String email);

}
