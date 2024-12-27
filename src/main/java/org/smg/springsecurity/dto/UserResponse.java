package org.smg.springsecurity.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserResponse<T> {//generics

    private T data;               // User data or any other response data
    private String errorMessage;  // Error message in case of failure
    private LocalDateTime timestamp;

    // Constructor for success response
    public UserResponse(T data, String errorMessage) {
        this.data = data;
        this.errorMessage = errorMessage;
        this.timestamp = LocalDateTime.now();
    }
}

