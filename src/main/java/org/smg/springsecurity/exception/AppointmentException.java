package org.smg.springsecurity.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

public class AppointmentException extends RuntimeException{
    public AppointmentException(String message, HttpStatusCode httpStatusCode){
        super(message);
    }
}
