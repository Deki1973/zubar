package org.smg.springsecurity.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smg.springsecurity.dto.LoginRequest;
import org.smg.springsecurity.dto.RegisterRequest;
import org.smg.springsecurity.dto.UserResponse;
import org.smg.springsecurity.model.User;
import org.smg.springsecurity.service.UserService;
import org.smg.springsecurity.validation.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

import static org.smg.springsecurity.constants.RoleConstants.ROLE_ADMIN;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);//logging

    private final UserService userService;//interface
    private final ValidationService validationService;//validation service

    //Constructor injection
    @Autowired
    public UserController(UserService userService, ValidationService validationService) {
        this.userService = userService;
        this.validationService = validationService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse<User>> registerUser(@RequestBody RegisterRequest registerRequest) {
        validationService.validateUserRegister(registerRequest);//validation
        Set<String> roles = userService.determineRoles(registerRequest);//roles

        User createdUser = userService.registerNewUser(registerRequest.getUsername(), registerRequest.getPassword(), roles);//create new user
        logger.info("User registered successfully with id: {}", createdUser.getId());

        return ResponseEntity.status(HttpStatus.CREATED).body(new UserResponse<>(createdUser, null));//201 status code
    }

    @PostMapping("/authenticate")
    public ResponseEntity<UserResponse<String>> authenticateUser(@RequestBody LoginRequest loginRequest) {
        validationService.validateUsernameAndPassword(loginRequest.getUsername(), loginRequest.getPassword());//validation
        User user = userService.findUserByUsername(loginRequest.getUsername());//check DB
        logger.info("User found with username '{}'", user.getUsername());

        String jwtToken = userService.authenticateUser(user.getUsername(), loginRequest.getPassword(), user.getRoles());//encrypted password

        return ResponseEntity.ok(new UserResponse<>(jwtToken, null));//200
    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse<User>> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        logger.info("User fetched successfully with id: {}", id);

        return ResponseEntity.ok(new UserResponse<>(user, null));//200 OK
    }

    @GetMapping
    public ResponseEntity<UserResponse<List<User>>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        logger.info("Fetched {} users", users.size());

        return ResponseEntity.ok(new UserResponse<>(users, null)); // 200 OK
    }

    @PutMapping
    public ResponseEntity<UserResponse<User>> updateUserPassword(@RequestBody LoginRequest loginRequest) {
        validationService.validateUsernameAndPassword(loginRequest.getUsername(), loginRequest.getPassword());//validation
        User existingUser = userService.findUserByUsername(loginRequest.getUsername());//check DB
        User updatedUser = userService.updateUserPassword(existingUser, loginRequest.getPassword());
        logger.info("User updated successfully with username: {}", loginRequest.getUsername());

        return ResponseEntity.ok(new UserResponse<>(updatedUser, null)); // Success response
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<UserResponse<User>> deleteUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        userService.deleteUserById(id);
        logger.info("User deleted successfully with id: {}", id);

        return ResponseEntity.ok(new UserResponse<>(user, null));//200
    }

    @GetMapping("/protected-resource")
    public ResponseEntity<String> protectedResource() {
        // Assuming you have a method to get the currently authenticated user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            // You can check roles/authorities here
            boolean hasAdminRole = authentication.getAuthorities().stream()
                    .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equalsIgnoreCase("ROLE_" + ROLE_ADMIN));

            if (hasAdminRole) {
                return ResponseEntity.ok("You have access to this protected resource.");
            }
        }

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied.");
    }

}
