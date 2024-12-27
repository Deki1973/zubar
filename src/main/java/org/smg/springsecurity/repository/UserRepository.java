package org.smg.springsecurity.repository;

import org.smg.springsecurity.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // Custom query method to find a user by their username
    Optional<User> findByUsername(String username);
}

