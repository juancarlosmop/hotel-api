package com.example.hotel.ecxeption;

public class LoginException extends RuntimeException {
    public LoginException(String message) {
        super(message);
    }
}
