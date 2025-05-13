package com.example.hotel.controller;

import com.example.hotel.dto.GeneralResponse;
import com.example.hotel.dto.ReservationResponse;
import com.example.hotel.dto.UserResponse;
import com.example.hotel.dto.request.ReservationRequest;
import com.example.hotel.dto.request.UserRequest;
import com.example.hotel.service.ReservationService;
import com.example.hotel.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/api/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private ReservationService reservationService;


    @Operation(summary = "Get users", description = "Get user by email")
    @ApiResponse(responseCode = "200", description = "Get user ")
    @ApiResponse(responseCode = "404", description = "The user was not found")
    @PreAuthorize("hasRole('USER')  or hasRole('ADMIN')")
    @GetMapping("/profile/{email}")
    public ResponseEntity<GeneralResponse<UserResponse>> getUser(@PathVariable("email") String email) {
        return new ResponseEntity<>(userService.getUserByEmail(email), HttpStatus.OK);
    }

    @Operation(summary = "Update user", description = "Update user by email")
    @ApiResponse(responseCode = "200", description = "The user was updated ")
    @ApiResponse(responseCode = "404", description = "The user was not found")
    @PreAuthorize("hasRole('USER')  or hasRole('ADMIN')")
    @PutMapping("/profile/{email}")
    public ResponseEntity<GeneralResponse<UserResponse>> updateUser(@PathVariable("email") String email, @Valid @RequestBody UserRequest userRequest) {
        return new ResponseEntity<>(userService.updateUserByEmail(email,userRequest), HttpStatus.OK);
    }

    //GET	/api/user/reservations	Ver sus reservas	USER
    @Operation(summary = "All user reservations", description = "Get all reservations made for a user")
    @ApiResponse(responseCode = "200", description = "All reservations made for user")
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/reservations/{id}")
    public ResponseEntity<GeneralResponse<List<ReservationResponse>>> getReservations(@PathVariable("id") Long id) {
        return new ResponseEntity<>(reservationService.getReservationsByIdUser(id), HttpStatus.OK);
    }
    @Operation(summary = "Create a Reservation", description = "Create a reservation resived a reservation an save in database")
    @ApiResponse(responseCode = "200", description = "The reservation was created")
    @ApiResponse(responseCode = "422", description = "The check-in date can not be in the past,The check-in date must be less than check-out,The check-in cant not be equal than check-out, Internal error")
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/create-reservation")
    public ResponseEntity<GeneralResponse<Void>> createReservation(@Valid @RequestBody ReservationRequest request){
        return new ResponseEntity<>( reservationService.createReservation(request), HttpStatus.CREATED);
    }

}
