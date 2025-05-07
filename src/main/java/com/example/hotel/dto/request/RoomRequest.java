package com.example.hotel.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class RoomRequest {
    @NotBlank(message = "The number is required")
    private String number;
    @NotBlank(message = "The type is required")
    private String type;
    @NotBlank(message = "The description is required")
    @Size(min = 10, message = "The description should have 10 characters min")
    private String description;
    @NotNull(message = "The price is required")
    @Positive(message = "The price per night should be positive")
    @Min(value = 0, message = "pricePerNight must be non-negative")
    @JsonProperty("pricePerNight")
    private BigDecimal pricePerNight;
    private Boolean available;
}
