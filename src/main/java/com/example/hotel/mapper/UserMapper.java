package com.example.hotel.mapper;

import com.example.hotel.dto.UserResponse;
import com.example.hotel.dto.request.UserRequest;
import com.example.hotel.model.entity.User;

import java.util.List;
import java.util.stream.Collectors;

public class UserMapper {

    public static UserResponse userEntityToUser(User user){
        return UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .fullName(user.getFullName())
                .role(user.getRole())
                .build();
    }

    public static  User userToUserEntity(UserRequest user){
        return User.builder()
                .email(user.getEmail())
                .fullName(user.getFullName())
                .password(user.getPassword())
                //.status(true)
                .role(user.getRole())
                .build();
    }

    public static List<UserResponse> useListEntityToUserList(List<User> listUser){
        return  listUser.stream()
                .map(x->
                     UserResponse.builder()
                            .id(x.getId())
                            .email(x.getEmail())
                            .fullName(x.getFullName())
                            .role(x.getRole())
                            .build()
                ).collect(Collectors.toList());
    }
}
