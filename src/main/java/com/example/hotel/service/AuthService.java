package com.example.hotel.service;

import com.example.hotel.dto.AuthResponse;
import com.example.hotel.dto.GeneralResponse;
import com.example.hotel.dto.request.AuthRequest;
import com.example.hotel.dto.request.RefreshTokenRequest;
import com.example.hotel.dto.request.UserRequest;
import com.example.hotel.ecxeption.LoginException;

public interface AuthService {
    /**
     * validate that email exist in the database and password matched
     * if the validation was successful return a token
     *
     * @param request Object with the credentials
     * @return AuthResponse toke if was validated or error message
     * @throws LoginException if the credentials are not correct
     * @author Juan Carlos
     */
    public AuthResponse login(AuthRequest request);
    /**
     * Create new user of typeUser
     *
     * @param userRequest object with data of user
     * @return GeneralResponse response common
     * @author Juan Carlos
     * */
    public GeneralResponse<Void> register(UserRequest userRequest);

    /**
     * Get a new token by refresh token
     *
     * @param request object with the refresh token
     * @return AuthResponse
     * @author Juan Carlos
     * */
    public AuthResponse refreshToken(RefreshTokenRequest request);
}
