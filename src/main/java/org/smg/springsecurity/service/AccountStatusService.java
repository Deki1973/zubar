package org.smg.springsecurity.service;

import org.smg.springsecurity.model.User;

public interface AccountStatusService {

    /**
     * Increases the number of failed login attempts for a user.
     *
     * @param user the user whose failed attempts will be increased
     */
    void increaseFailedAttempts(User user);

    /**
     * Resets the failed login attempts for a user.
     *
     * @param username the username of the user whose attempts will be reset
     */
    void resetFailedAttempts(String username);

    /**
     * Locks the user's account due to multiple failed login attempts.
     *
     * @param user the user whose account will be locked
     */
    void lockAccount(User user);

    /**
     * Checks if the user's account can be unlocked based on the elapsed time since locking.
     *
     * @param user the user whose lock status is being checked
     * @return true if the account can be unlocked, false otherwise
     */
    boolean unlockWhenTimeExpired(User user);
}

