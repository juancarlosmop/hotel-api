package com.example.hotel.service;

import com.example.hotel.dto.GeneralResponse;
import com.example.hotel.dto.UserResponse;
import com.example.hotel.dto.request.UserRequest;
import com.example.hotel.ecxeption.UserNotFoundException;

import java.util.List;

public interface UserService {
    /**
     * Received an email of user to get the object user
     *
     * @param email of user
     * @return GeneralResponse common response
     * @throws UserNotFoundException if the room was not found
     * @author Juan Carlos
     */
    public GeneralResponse<UserResponse> getUserByEmail(String email);
    /**
     * Received a dto object with the room to create
     * a new room in database
     * @param email of user
     * @param  userRequest with room params
     * @return GeneralResponse common response with user object
     * @throws UserNotFoundException if the room was not found
     * @author Juan Carlos
     */
    public GeneralResponse<UserResponse> updateUserByEmail(String email, UserRequest userRequest);
    /**
     * Get all users
     * @return GeneralResponse common response with list of users
     * @author Juan Carlos
     */
    public GeneralResponse<List<UserResponse>> getAllUsers();
    /**
     * Received an id to sof delete
     *
     * @param id id of user
     * @return GeneralResponse common response
     * @throws UserNotFoundException if the room was not found
     * @author Juan Carlos
     */
    public GeneralResponse<Void> deleteUserSofDelete(long id);





}
