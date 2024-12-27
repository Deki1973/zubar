package org.smg.springsecurity.provider;

import org.smg.springsecurity.model.User;
import org.smg.springsecurity.repository.UserRepository;
import org.smg.springsecurity.service.AccountStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final AccountStatusService accountStatusService;
    private final UserRepository userRepository;//circular dependency!
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public CustomAuthenticationProvider(AccountStatusService accountStatusService, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.accountStatusService = accountStatusService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // Extract username and password from the authentication request
        String username = authentication.getName();
        String password = (String) authentication.getCredentials();

        // Validate user credentials
        User user = validateUserCredentials(username, password);

        // Reset failed login attempts upon successful authentication
        accountStatusService.resetFailedAttempts(username);

        // Return an authentication token representing a successful authentication
        return createAuthenticationToken(user);
    }

    //This method indicates that this AuthenticationProvider supports UsernamePasswordAuthenticationToken,
    // which is the standard token for username/password-based authentication in Spring Security.
    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }

    private User validateUserCredentials(String username, String password) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        // Check if the account is locked and whether it can be unlocked
        checkAccountLock(user);

        // Validate the provided password
        validatePassword(password, user);

        return user;
    }

    private void checkAccountLock(User user) {
        if (user.isAccountLocked() && !accountStatusService.unlockWhenTimeExpired(user)) {
            throw new LockedException("Your account is locked. Please try again after 30 minutes.");//Spring Security Exception
        }
    }

    private void validatePassword(String rawPassword, User user) {
        if (!passwordEncoder.matches(rawPassword, user.getPassword())) {
            accountStatusService.increaseFailedAttempts(user);
            throw new BadCredentialsException("Invalid password.");
        }
    }

    private Authentication createAuthenticationToken(User user) {
        return new UsernamePasswordAuthenticationToken(user.getUsername(), null, user.getAuthorities());//Authorities added!
    }
}