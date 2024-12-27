package org.smg.springsecurity.exception;

import org.smg.springsecurity.dto.UserResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    // Handle specific exceptions
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<UserResponse<Void>> handleUserNotFoundException(UserNotFoundException ex) {
        UserResponse<Void> response = new UserResponse<>(null, ex.getMessage());

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND); // 404 Not Found
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<UserResponse<Void>> handleUserAlreadyExistsException(UserAlreadyExistsException ex) {
        UserResponse<Void> response = new UserResponse<>(null, ex.getMessage());

        return new ResponseEntity<>(response, HttpStatus.CONFLICT); // 409 Conflict
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<UserResponse<Void>> handleBadCredentialsException(BadCredentialsException ex) {
        UserResponse<Void> response = new UserResponse<>(null, ex.getMessage());

        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED); // 401
    }

    @ExceptionHandler(LockedException.class)
    public ResponseEntity<UserResponse<Void>> handleLockedException(LockedException ex) {
        UserResponse<Void> response = new UserResponse<>(null, ex.getMessage());

        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN); // 403
    }

    // Handle global exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<UserResponse<Void>> handleGlobalException(Exception ex) {
        UserResponse<Void> response = new UserResponse<>(null, ex.getMessage());
        response.setTimestamp(LocalDateTime.now());

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR); // 500 Internal Server Error
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<UserResponse<Void>> handleValidationException(ValidationException ex) {
        UserResponse<Void> response = new UserResponse<>(null, ex.getMessage());
        response.setTimestamp(LocalDateTime.now());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST); // 400 Bad Request
    }
}