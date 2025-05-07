package com.example.hotel.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponse {
    private long id;
    private String email;
    private String fullName;
    private String role;
}
