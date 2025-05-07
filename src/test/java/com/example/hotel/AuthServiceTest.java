package com.example.hotel;

import com.example.hotel.constants.StatusCodeEnum;
import com.example.hotel.dto.AuthResponse;
import com.example.hotel.dto.GeneralResponse;
import com.example.hotel.dto.request.AuthRequest;
import com.example.hotel.dto.request.RefreshTokenRequest;
import com.example.hotel.dto.request.UserRequest;
import com.example.hotel.ecxeption.BusinessException;
import com.example.hotel.ecxeption.LoginException;
import com.example.hotel.ecxeption.UserNotFoundException;
import com.example.hotel.jwt.JwtUtil;
import com.example.hotel.model.entity.User;
import com.example.hotel.repository.UserRepository;
import com.example.hotel.service.AuthServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthServiceImpl authService;

    private AuthRequest request;
    private User user;
    private UserRequest userRequest;
    @BeforeEach
    void setUp(){
        request = AuthRequest.builder()
                .email("juancarlosmoreno@gmail.com")
                .password("1234")
                .build();

        user=User.builder()
                .id(1l)
                .email("juancarlos@gmail.com")
                .fullName("Juan Carlos")
                .password("1234")
                .status(true)
                .role("USER")
                .build();

        userRequest=UserRequest.builder()
                .email("juancarlos@gmail.com")
                .fullName("Juan Carlos")
                .password("1234")
                .role("USER")
                .build();
    }

    @Test
    void  login_whenUserFound_shouldReturnUserNotFoundException(){
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        UserNotFoundException ex= assertThrows(UserNotFoundException.class,()->{
            authService.login(request);
        });
        assertEquals(StatusCodeEnum.R_003.getDescription(),ex.getMessage());
    }
    @Test
    void  login_whenPassAreNotMatch_shouldThrowLoginException(){
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));
        LoginException ex = assertThrows(LoginException.class,()->{
            authService.login(request);
        });
        assertEquals(StatusCodeEnum.R_002.getDescription(),ex.getMessage());
    }
    @Test
    void  login_whenUserLogin_shouldReturnToken(){
        User userEn=User.builder()
                .id(1l)
                .email("juancarlos@gmail.com")
                .fullName("Juan Carlos")
                .password("$2a$12$jZfbjErC2Y3fytwDTMp3RedphQ45rl5skejOvF8StePa0fsiYmytu")
                .status(true)
                .role("USER")
                .build();
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(userEn));
        when(passwordEncoder.matches("1234", userEn.getPassword())).thenReturn(true); // aquí estaba el fallo
        when(jwtUtil.generateToken(anyString(),anyString(),anyString())).thenReturn("token");
        when(jwtUtil.generateRefreshToken(anyString(),anyString(),anyString())).thenReturn("refreshToken");
        AuthResponse response= authService.login(request);
        assertEquals("OK", response.getCode());
        assertEquals(StatusCodeEnum.R_005.getDescription(), response.getMessage());
    }
    @Test
    void  register_whenUserExist_shouldReturnBusinessException(){
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));
        BusinessException ex = assertThrows(BusinessException.class,()->{
            authService.register(userRequest);
        });
    }
    @Test
    void  register_whenUserNotExist_shouldSave(){
        UserRequest userRequestIn = UserRequest.builder()
                .email("test@example.com")
                .build();
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        when(userRepository.save(any(User.class))).thenReturn(user);
        GeneralResponse<Void> resp=authService.register(userRequestIn);
        assertEquals("OK", resp.getCode());
        assertEquals(StatusCodeEnum.R_001.getDescription(), resp.getMessage());
    }
    @Test
    void refreshToken_validToken_success() {
        // Arrange
        String refreshToken = "validRefreshToken";
        String email = "user@example.com";
        String role = "USER";
        String fullName = "John Doe";
        String newAccessToken = "newAccessToken";

        RefreshTokenRequest request = RefreshTokenRequest.builder()
                .refreshToken(refreshToken).build();

        when(jwtUtil.isValidRefreshToken(refreshToken)).thenReturn(true);
        when(jwtUtil.getUsernameFromToken(refreshToken)).thenReturn(email);
        when(jwtUtil.getRoleFromToken(refreshToken)).thenReturn(role);
        when(jwtUtil.getFullNameFromToken(refreshToken)).thenReturn(fullName);
        when(jwtUtil.generateToken(email, role, fullName)).thenReturn(newAccessToken);

        // Act
        AuthResponse response = authService.refreshToken(request);

        // Assert
        assertEquals("OK", response.getCode());
        assertEquals("Token refreshed", response.getMessage());
        assertEquals(newAccessToken, response.getToken());
        assertEquals(refreshToken, response.getRefreshToken());
    }

    @Test
    void refreshToken_invalidToken_throwsException() {
        // Arrange
        String invalidRefreshToken = "invalidToken";

        RefreshTokenRequest request = RefreshTokenRequest.builder()
                .refreshToken(invalidRefreshToken).build();

        when(jwtUtil.isValidRefreshToken(invalidRefreshToken)).thenReturn(false);

        // Act & Assert
        LoginException exception = assertThrows(LoginException.class, () -> {
            authService.refreshToken(request);
        });

        assertEquals(StatusCodeEnum.R_008.getDescription(), exception.getMessage());
    }

}
