package com.example.hotel.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
@Entity
@Table(name = "reservations")
@Data
@NoArgsConstructor         // <–– lo genera Lombok
@AllArgsConstructor
@Builder
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private User user;
    @ManyToOne
    private Room room;
    @Column(name = "check_in_date")
    private LocalDate checkInDate;
    @Column(name = "check_out_date ")
    private LocalDate checkOutDate;
    private BigDecimal total;
    private Boolean status;

    // Getters and Setters
}
