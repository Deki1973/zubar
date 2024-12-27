package org.smg.springsecurity.constants;

import java.util.Set;

public class RoleConstants {
    public static final String ROLE_PREFIX = "ROLE_";
    public static final String ROLE_USER = "USER";
    public static final String ROLE_ADMIN = "ADMIN";
    public static final String ROLE_MODERATOR = "MODERATOR"; // New role added
    public static final Set<String> ALLOWED_ROLES = Set.of(ROLE_USER, ROLE_ADMIN, ROLE_MODERATOR);

    private RoleConstants() {
        // Private constructor to prevent instantiation
    }
}

