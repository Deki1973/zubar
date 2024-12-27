package org.smg.springsecurity.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

public class ClientException extends RuntimeException{
    public ClientException(String message, HttpStatusCode httpStatusCode){
        super(message);
    }
}
