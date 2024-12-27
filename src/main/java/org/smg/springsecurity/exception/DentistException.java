package org.smg.springsecurity.exception;


import org.springframework.http.HttpStatusCode;

public class DentistException extends RuntimeException{
    public DentistException(String message, HttpStatusCode httpStatusCode){
        super(message);
    }
}
