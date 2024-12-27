package org.smg.springsecurity.dto;

import lombok.Data;

import java.util.Set;

@Data
public class RegisterRequest {//It can be immutable record class
    private String username;
    private String password;
    private Set<String> roles; // Use Set<String> to store roles directly
}

