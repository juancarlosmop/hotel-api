package com.example.hotel.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RefreshTokenRequest {
    private String refreshToken;
}
