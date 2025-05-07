package com.example.hotel.dto.request;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserRequest {
    @Email(message = "The mail must be valid")
    @NotBlank(message = "The mail is required")
    private String email;
    @NotBlank(message = "The password is required")
    @Size(min = 8, message = "The password should have 8 characters at least")
    private String password;
    @NotBlank(message = "The fullName is required")
    @Size(min = 6, message = "The fullName should have 6 characters at least")
    private String fullName;
    @NotBlank(message = "The mail is required")
    private String role;
}
