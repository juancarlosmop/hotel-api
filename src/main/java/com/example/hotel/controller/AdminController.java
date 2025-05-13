package com.example.hotel.controller;

import com.example.hotel.dto.GeneralResponse;
import com.example.hotel.dto.ReservationResponse;
import com.example.hotel.dto.UserResponse;
import com.example.hotel.dto.request.RoomRequest;
import com.example.hotel.service.ReservationService;
import com.example.hotel.service.RoomService;
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
@RequestMapping("/v1/api/admin")
public class AdminController {
    @Autowired
    private UserService userService;
    @Autowired
    private ReservationService reservationService;

    @Autowired
    private RoomService roomService;

    @Operation(summary = "All users", description = "Get all available users")
    @ApiResponse(responseCode = "200", description = "Get all users")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/users")
    public ResponseEntity<GeneralResponse<List<UserResponse>>> getUsers(){
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.CREATED);
    }


    @Operation(summary = "Delete User", description = "Delete user by email")
    @ApiResponse(responseCode = "200", description = "The user was deleted")
    @ApiResponse(responseCode = "404", description = "User was not found")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/users/{id}")
    public ResponseEntity<GeneralResponse<Void>> deleteUserForSofDelete(@PathVariable("id") long id){
        return new ResponseEntity<>(userService.deleteUserSofDelete(id), HttpStatus.CREATED);
    }

    @Operation(summary = "Create a new room", description = "Receives a room and save in database")
    @ApiResponse(responseCode = "200", description = "The room was created")
    @ApiResponse(responseCode = "409", description = "The room already exist")
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/rooms")
    public ResponseEntity<GeneralResponse<Void>> createRoom(@Valid @RequestBody RoomRequest request){
        return new ResponseEntity<>( roomService.createRom(request), HttpStatus.CREATED);
    }

    @Operation(summary = "Update Room", description = "Update room by id ")
    @ApiResponse(responseCode = "200", description = "The room was updated")
    @ApiResponse(responseCode = "404", description = "The room was not found")
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/rooms/{id}")
    public ResponseEntity<GeneralResponse<Void>> updateRoom(@PathVariable("id") long id,@Valid @RequestBody RoomRequest request){
        return new ResponseEntity<>( roomService.updateRoomById(id,request), HttpStatus.CREATED);
    }


    @Operation(summary = "Delete Room", description = "Delete room by id ")
    @ApiResponse(responseCode = "200", description = "The room was deleted")
    @ApiResponse(responseCode = "404", description = "The room was not found")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/rooms/{id}")
    public ResponseEntity<GeneralResponse<Void>> deleteRoomForSofDelete(@PathVariable("id") long id){
        return new ResponseEntity<>(roomService.deleteRomSofDeleteById(id), HttpStatus.CREATED);
    }


    @Operation(summary = "All reservations", description = "Get All available Reservations ")
    @ApiResponse(responseCode = "200", description = "Get All reservations")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/reservations")
    public ResponseEntity<GeneralResponse<List<ReservationResponse>>> allActiveReservations(){
        return new ResponseEntity<>(reservationService.getReservations(), HttpStatus.OK);
    }

    @Operation(summary = "Cancel Reservation", description = "Cancel reservation by id reservation ")
    @ApiResponse(responseCode = "200", description = "Reservation was cancelled")
    @ApiResponse(responseCode = "404", description = "The reservation was not found")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/reservations-cancel/{id}")
    public ResponseEntity<GeneralResponse<Void>> CancelReservation(@PathVariable("id") long id){
        return new ResponseEntity<>(reservationService.cancelReservation(id), HttpStatus.CREATED);
    }


}
