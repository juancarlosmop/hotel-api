package com.example.hotel.controller;



import com.example.hotel.dto.AuthResponse;
import com.example.hotel.dto.GeneralResponse;
import com.example.hotel.dto.request.AuthRequest;
import com.example.hotel.dto.request.RefreshTokenRequest;
import com.example.hotel.dto.request.UserRequest;
import com.example.hotel.service.AuthServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/api/auth")
public class AuthController {

    @Autowired
    private AuthServiceImpl authService;

    @Operation(summary = "Login of user", description = "Get the credentials,return a token and refresh token")
    @ApiResponse(responseCode = "200", description = "token and refresh token was created")
    @ApiResponse(responseCode = "404", description = "user was not found")
    @ApiResponse(responseCode = "500", description = "password was not matched")
    @PostMapping("/login")
    public AuthResponse login(@Valid @RequestBody  AuthRequest request) {
        return authService.login(request);
    }

    @Operation(summary = "Register of new user", description = "Get a user and save the user in the database")
    @ApiResponse(responseCode = "200", description = "The user was created")
    @ApiResponse(responseCode = "500", description = "The user already Exist")
    @PostMapping("/register")
    public ResponseEntity<GeneralResponse<Void>> register(@Valid  @RequestBody UserRequest request){
        return new ResponseEntity<>( authService.register(request), HttpStatus.CREATED);
    }
    @Operation(summary = "Get new token", description = "Get a user and save the user in the database")
    @ApiResponse(responseCode = "200", description = "The new token access was created")
    @ApiResponse(responseCode = "500", description = "Invalid refresh token")
    @PostMapping("/refresh-token")
    public ResponseEntity<AuthResponse> refreshToken(@RequestBody RefreshTokenRequest request) {
        return  new ResponseEntity<>(authService.refreshToken(request), HttpStatus.CREATED);
    }
}
