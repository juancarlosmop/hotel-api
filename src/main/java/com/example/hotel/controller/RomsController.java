package com.example.hotel.controller;

import com.example.hotel.dto.GeneralResponse;
import com.example.hotel.dto.RoomResponse;
import com.example.hotel.service.RoomService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/api/room")
public class RomsController {
    @Autowired
    private RoomService roomService;
    @Operation(summary = "All rooms", description = "Get all Rooms available")
    @ApiResponse(responseCode = "200", description = "Get all Rooms")
    @GetMapping
    public ResponseEntity<GeneralResponse<List<RoomResponse>>> getAllRooms(){
        return new ResponseEntity<>(roomService.getAllRooms(),HttpStatus.OK);
    }
    @Operation(summary = "Get room", description = "Get Room by id")
    @ApiResponse(responseCode = "200", description = "Get  Room ")
    @ApiResponse(responseCode = "404", description = "The Room was not found")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @GetMapping("{id}")
    public ResponseEntity<GeneralResponse<RoomResponse>> getRoomById(@PathVariable("id") long id){
        return new ResponseEntity<>(roomService.getRoomById(id),HttpStatus.OK);
    }
}
