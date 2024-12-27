package org.smg.springsecurity.validation;

import io.micrometer.common.util.StringUtils;
import org.smg.springsecurity.dto.RegisterRequest;
import org.smg.springsecurity.exception.UserAlreadyExistsException;
import org.smg.springsecurity.exception.ValidationException;
import org.smg.springsecurity.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

import static org.smg.springsecurity.constants.RoleConstants.ALLOWED_ROLES;

@Service
public class ValidationService {

    private static final String USERNAME = "Username";// USE CONST
    private static final String PASSWORD = "Password";

    private final UserRepository userRepository;

    public ValidationService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void validateUserRegister(RegisterRequest registerRequest) {
        validateUsernameAndPassword(registerRequest.getUsername(), registerRequest.getPassword());
        validateRoles(registerRequest.getRoles());
    }

    public void validateUsernameAndPassword(String username, String password) {
        validateField(USERNAME, username);
        validateField(PASSWORD, password);

        if (password.length() < 6) {
            throw new ValidationException("Password must be at least 6 characters long!");
        }
    }

    public void checkIfUserExists(String username) {
        if (userRepository.findByUsername(username).isPresent()) {
            throw new UserAlreadyExistsException("User with username '" + username + "' already exists.");
        }
    }

    private void validateField(String fieldName, String fieldValue) {
        if (StringUtils.isBlank(fieldValue)) {
            throw new ValidationException(fieldName + " is required");
        }
    }

    private void validateRoles(Set<String> roles) {
        if (roles != null && !ALLOWED_ROLES.containsAll(roles.stream()
                .map(String::toUpperCase).collect(Collectors.toSet()))) {//ignore case
            throw new ValidationException("Invalid role provided.");
        }
    }
}