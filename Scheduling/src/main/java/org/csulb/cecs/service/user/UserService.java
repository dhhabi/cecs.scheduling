package org.csulb.cecs.service.user;

import java.util.Collection;
import java.util.Optional;

import org.csulb.cecs.model.User;
import org.csulb.cecs.model.UserCreateForm;


public interface UserService {

    Optional<User> getUserById(long id);

    Optional<User> getUserByEmail(String email);

    Collection<User> getAllUsers();

    User create(UserCreateForm form);

}