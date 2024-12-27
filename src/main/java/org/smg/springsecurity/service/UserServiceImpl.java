package org.smg.springsecurity.service;

import io.micrometer.common.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smg.springsecurity.dto.RegisterRequest;
import org.smg.springsecurity.exception.UserNotFoundException;
import org.smg.springsecurity.model.User;
import org.smg.springsecurity.repository.UserRepository;
import org.smg.springsecurity.util.JwtUtil;
import org.smg.springsecurity.validation.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.smg.springsecurity.constants.RoleConstants.ROLE_USER;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final ValidationService validationService;

    //constructor injection
    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager,
                           JwtUtil jwtUtil, ValidationService validationService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.validationService = validationService;
    }

    @Override
    public User registerNewUser(String username, String password, Set<String> roles) {
        // Check if the user already exists through validation service
        validationService.checkIfUserExists(username);

        User user = createUser(username, password, roles);//Builder pattern

        User savedUser = userRepository.save(user); // 1. save new user
        logger.info("New user registered with username '{}'", username);

        return savedUser;
    }

    @Override
    public String authenticateUser(String username, String password, Set<String> roles) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

        SecurityContextHolder.getContext().setAuthentication(authentication);//set authentication in context!
        logger.info("User authenticated successfully: {}", authentication.getName());

        return jwtUtil.generateToken(username, roles);
    }

    @Override
    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> {
            logger.warn("No user found with username '{}'", username);
            return new UserNotFoundException("User not found with username '" + username + "'");
        });
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> {
            logger.error("User with ID '{}' not found", id);
            return new UserNotFoundException("User not found!");
        });
    }

    @Override
    public List<User> getAllUsers() {
        logger.info("Fetching all users");
        return userRepository.findAll();
    }

    @Override
    public User updateUserPassword(User user, String password) {
        if (StringUtils.isNotBlank(password)) {
            user.setPassword(passwordEncoder.encode(password));
        }

        userRepository.save(user);
        logger.info("User with username '{}' updated successfully", user.getUsername());

        return user;
    }

    @Override
    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
        logger.info("User with ID '{}' deleted successfully", id);
    }

    @Override
    public Set<String> determineRoles(RegisterRequest registerRequest) {
        return new HashSet<>(registerRequest.getRoles() != null && registerRequest.getRoles().stream()
                .anyMatch(role -> !role.isBlank()) ? registerRequest.getRoles() : Set.of(ROLE_USER));//Default USER role
    }

    private User createUser(String username, String password, Set<String> roles) {
        return User.builder()//Builder pattern
                .username(username)
                .password(passwordEncoder.encode(password))//encoded password
                .roles(roles)
                .build();
    }
}

