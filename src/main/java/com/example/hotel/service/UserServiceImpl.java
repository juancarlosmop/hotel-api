package com.example.hotel.service;

import com.example.hotel.constants.StatusCodeEnum;
import com.example.hotel.dto.GeneralResponse;
import com.example.hotel.dto.UserResponse;
import com.example.hotel.dto.request.UserRequest;
import com.example.hotel.ecxeption.UserNotFoundException;
import com.example.hotel.mapper.UserMapper;
import com.example.hotel.model.entity.User;
import com.example.hotel.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public GeneralResponse<UserResponse> getUserByEmail(String email){
        User user = userRepository.findByEmail(email)
                .orElseThrow(()->new  UserNotFoundException(StatusCodeEnum.R_003.getDescription()));
        return GeneralResponse.<UserResponse>builder()
                .code("OK")
                .message(StatusCodeEnum.R_001.getDescription())
                .data(UserMapper.userEntityToUser(user))
                .build();
    }

    @Override
    public GeneralResponse<UserResponse> updateUserByEmail(String email, UserRequest userRequest) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(()->new  UserNotFoundException(StatusCodeEnum.R_003.getDescription()));
        user.setFullName(userRequest.getFullName());
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        User userUpdate=userRepository.save(user);
        return GeneralResponse.<UserResponse>builder()
                .code("OK")
                .message(StatusCodeEnum.R_001.getDescription())
                .data(UserMapper.userEntityToUser(userUpdate))
                .build();
    }

    @Override
    public GeneralResponse<List<UserResponse>> getAllUsers() {
        List<User> listUser = userRepository.findByStatusTrue();
        return GeneralResponse.<List<UserResponse>>builder()
                .code("OK")
                .message(StatusCodeEnum.R_001.getDescription())
                .data(UserMapper.useListEntityToUserList(listUser))
                .build();
    }

    @Override
    public GeneralResponse<Void> deleteUserSofDelete(long id) {
        User user = userRepository.findById(id)
                .orElseThrow(()->new  UserNotFoundException(StatusCodeEnum.R_003.getDescription()));

        userRepository.updateUserStatus(user.getId(),false);
        return GeneralResponse.<Void>builder()
                .code("OK")
                .message(StatusCodeEnum.R_001.getDescription())
                .build();
    }
}
