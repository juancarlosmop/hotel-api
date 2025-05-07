package com.example.hotel.dto.request;

import com.example.hotel.model.entity.Room;
import com.example.hotel.model.entity.User;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
public class ReservationRequest {
    @NotNull(message = "The id user was not null")
    private Long idUser;
    @NotNull(message = "The id room was not null")
    private Long idRoom;
    @NotNull(message = "The check in was not null")
    private LocalDate checkInDate;
    @NotNull(message = "The check out was not null ")
    private LocalDate checkOutDate;
    @NotNull(message = "The total was not null")
    @Positive(message = "The total should be positive")
    @Min(value = 0, message = "total must be non-negative")
    private BigDecimal total;
}
