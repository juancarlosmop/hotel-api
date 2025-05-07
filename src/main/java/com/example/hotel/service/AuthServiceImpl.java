package com.example.hotel.service;


import com.example.hotel.constants.StatusCodeEnum;
import com.example.hotel.dto.AuthResponse;
import com.example.hotel.dto.GeneralResponse;
import com.example.hotel.dto.request.AuthRequest;
import com.example.hotel.dto.request.RefreshTokenRequest;
import com.example.hotel.dto.request.UserRequest;
import com.example.hotel.ecxeption.BusinessException;
import com.example.hotel.ecxeption.LoginException;
import com.example.hotel.ecxeption.UserNotFoundException;
import com.example.hotel.jwt.JwtUtil;
import com.example.hotel.mapper.UserMapper;
import com.example.hotel.model.entity.User;
import com.example.hotel.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * {@inheritDoc}
     */
    public AuthResponse login(AuthRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                        .orElseThrow(()->new  UserNotFoundException(StatusCodeEnum.R_003.getDescription()));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
                throw new LoginException(StatusCodeEnum.R_002.getDescription());
        }
        String token = jwtUtil.generateToken(user.getEmail(), user.getRole(), user.getFullName());
        String refreshToken = jwtUtil.generateRefreshToken(user.getEmail(), user.getRole(), user.getFullName());
        return new AuthResponse("OK", StatusCodeEnum.R_005.getDescription(),token,refreshToken);
    }

    /**
     * {@inheritDoc}
     */
    public GeneralResponse<Void> register(UserRequest userRequest){
        Optional<User> userFound = userRepository.findByEmail(userRequest.getEmail());
        if(userFound.isPresent()){
            throw  new BusinessException(StatusCodeEnum.R_007.getDescription());
        }
        userRepository.save(UserMapper.userToUserEntity(userRequest));
        return GeneralResponse.<Void>builder()
                .code("OK")
                .message(StatusCodeEnum.R_001.getDescription())
                .build();
    }

    /**
     * {@inheritDoc}
     */
    public AuthResponse refreshToken(RefreshTokenRequest request) {
        String refreshToken = request.getRefreshToken();

        if (!jwtUtil.isValidRefreshToken(refreshToken)) {
            throw new LoginException(StatusCodeEnum.R_008.getDescription());
        }

        String email = jwtUtil.getUsernameFromToken(refreshToken);
        String role = jwtUtil.getRoleFromToken(refreshToken);
        String fullName = jwtUtil.getFullNameFromToken(refreshToken);


        String newAccessToken = jwtUtil.generateToken(email, role, fullName);

        return new AuthResponse("OK", StatusCodeEnum.R_012.getDescription(), newAccessToken, refreshToken);
    }
}

