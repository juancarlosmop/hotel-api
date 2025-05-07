package com.example.hotel.ecxeption;

public class BusinessException extends RuntimeException {
    public BusinessException(String message) {
        super(message);
    }
}
