package org.smg.springsecurity.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smg.springsecurity.model.User;
import org.smg.springsecurity.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Service
public class AccountStatusServiceImpl implements AccountStatusService {

    private static final Logger logger = LoggerFactory.getLogger(AccountStatusServiceImpl.class);

    private static final int MAX_FAILED_ATTEMPTS = 3;
    private static final long LOCK_TIME_DURATION = 30 * 60 * 1000; // 30 minutes

    private final UserRepository userRepository;//circular dependency!

    @Autowired
    public AccountStatusServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //3 max attempts
    @Override
    public void increaseFailedAttempts(User user) {
        int newFailAttempts = user.getFailedAttempts() + 1;
        user.setFailedAttempts(newFailAttempts);

        logger.info("User '{}' failed to login. Attempt {} of {}", user.getUsername(), newFailAttempts, MAX_FAILED_ATTEMPTS);

        if (newFailAttempts >= MAX_FAILED_ATTEMPTS) {
            lockAccount(user);
            logger.warn("User '{}' account is locked due to {} failed login attempts", user.getUsername(), MAX_FAILED_ATTEMPTS);
        }

        userRepository.save(user);
    }

    //lock account
    @Override
    public void lockAccount(User user) {
        user.setAccountLocked(true);
        user.setLockTime(LocalDateTime.now());
        logger.info("User '{}' account is locked at {}", user.getUsername(), LocalDateTime.now());
    }

    //unlock user after 30min
    @Override
    public boolean unlockWhenTimeExpired(User user) {
        if (user.getLockTime() == null) {
            logger.info("User '{}' account is not locked.", user.getUsername());
            return false; // Account is not locked
        }

        long lockTimeInMillis = user.getLockTime().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        long currentTimeInMillis = System.currentTimeMillis();

        if (currentTimeInMillis - lockTimeInMillis > LOCK_TIME_DURATION) {
            unlockAccount(user);
            logger.info("User '{}' account is unlocked after lock time expired", user.getUsername());
            return true;
        }

        logger.info("User '{}' account is still locked. Time remaining to unlock.", user.getUsername());
        return false;
    }

    //reset failed attempts
    @Override
    public void resetFailedAttempts(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
        user.setFailedAttempts(0);

        unlockAccount(user); // Resetting also unlocks the account
        logger.info("User '{}' failed attempts have been reset and account is unlocked.", username);

        userRepository.save(user);
    }

    private void unlockAccount(User user) {
        user.setAccountLocked(false);
        user.setLockTime(null);
        logger.info("User '{}' account is unlocked.", user.getUsername());
    }
}