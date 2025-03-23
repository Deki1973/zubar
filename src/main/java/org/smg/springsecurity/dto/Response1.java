package org.smg.springsecurity.dto;

public class Response1 {
    private String message;

    public Response1(String message, int retCode) {
        this.message = message;
        this.retCode = retCode;
    }

    public Response1() {
    }

    public int getRetCode() {
        return retCode;
    }

    public void setRetCode(int retCode) {
        this.retCode = retCode;
    }

    private int retCode;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
