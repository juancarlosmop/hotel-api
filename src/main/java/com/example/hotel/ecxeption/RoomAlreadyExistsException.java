package com.example.hotel.ecxeption;

public class RoomAlreadyExistsException extends RuntimeException {
    public RoomAlreadyExistsException(String number) {
      super("The room with number " + number + " already exists.");
    }
}
