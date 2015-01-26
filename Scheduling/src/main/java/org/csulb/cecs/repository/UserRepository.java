package org.csulb.cecs.repository;

import java.util.Optional;

import org.csulb.cecs.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findOneByEmail(String email);
}