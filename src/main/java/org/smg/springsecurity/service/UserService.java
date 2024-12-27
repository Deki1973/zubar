package org.smg.springsecurity.service;

import org.smg.springsecurity.dto.RegisterRequest;
import org.smg.springsecurity.exception.UserAlreadyExistsException;
import org.smg.springsecurity.exception.UserNotFoundException;
import org.smg.springsecurity.model.User;

import java.util.List;
import java.util.Set;

public interface UserService {

    /**
     * Registers a new user with the specified username and password.
     *
     * @param username the username of the new user
     * @param password the password of the new user
     * @return the created RecordUser instance
     * @throws UserAlreadyExistsException if a user with the given username already exists
     */
    User registerNewUser(String username, String password, Set<String> roles) throws UserAlreadyExistsException;

    String authenticateUser(String username, String password, Set<String> roles) throws UserNotFoundException;

    User findUserByUsername(String username);

    /**
     * Retrieves a user by their ID.
     *
     * @param id the ID of the user
     * @return the User instance
     * @throws RuntimeException if the user is not found
     */
    User getUserById(Long id) throws RuntimeException;

    /**
     * Retrieves all users.
     *
     * @return a list of User instances
     */
    List<User> getAllUsers();

    User updateUserPassword(User user, String password) throws RuntimeException;

    /**
     * Deletes a user by their ID.
     *
     * @param id the ID of the user to delete
     * @throws RuntimeException if the user is not found
     */
    void deleteUserById(Long id) throws RuntimeException;

    Set<String> determineRoles(RegisterRequest registerRequest);
}