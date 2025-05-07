package com.example.hotel.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthRequest {
    @Email(message = "The mail must be valid")
    @NotBlank(message = "The mail is required")
    private String email;
    @NotBlank(message = "The password is required")
    private String password;
}
