package com.example.hotel;

import com.example.hotel.constants.StatusCodeEnum;
import com.example.hotel.dto.GeneralResponse;
import com.example.hotel.dto.UserResponse;
import com.example.hotel.dto.request.UserRequest;
import com.example.hotel.ecxeption.UserNotFoundException;
import com.example.hotel.model.entity.User;
import com.example.hotel.repository.UserRepository;
import com.example.hotel.service.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;
    private UserRequest userRequest;
    private User user;
    @BeforeEach()
    void setUP(){
        userRequest = UserRequest.builder()
                .email("juancarlos@gmail.com")
                .password("12345")
                .fullName("Juan Carlos moreno")
                .role("USER")
                .build();


        user=User.builder()
                .id(1l)
                .email("juancarlos@gmail.com")
                .fullName("Juan Carlos")
                .password("1234")
                .status(true)
                .role("USER")
                .build();
    }
    @Test
    void getUserByEmail_whenUserNotFound_shouldThrowUserNotFoundException(){
        when(userRepository.findByEmail("asdf@gmial.com")).thenReturn(Optional.empty());
        UserNotFoundException ex = assertThrows(UserNotFoundException.class,()->{
            userService.getUserByEmail("asdf@gmial.com");
        });
        verify(userRepository,times(1)).findByEmail(any());
    }

    @Test
    void getUserByEmail_whenUserFound_shouldSave(){
        when(userRepository.findByEmail("juancarlos@gmail.com")).thenReturn(Optional.of(user));
        GeneralResponse<UserResponse> res= userService.getUserByEmail("juancarlos@gmail.com");
        assertEquals("OK",res.getCode());
        assertEquals("Success",res.getMessage());

    }
    @Test
    void updateUserByEmail_WhenUserNotFound_shouldThrowUserNotFoundException(){
        when(userRepository.findByEmail("juancarlos@gmail.com")).thenReturn(Optional.empty());
        UserNotFoundException ex = assertThrows(UserNotFoundException.class,()->{
            userService.getUserByEmail("juancarlos@gmail.com");
        });
        verify(userRepository,times(1)).findByEmail(any());
    }
    @Test
    void updateUserByEmail_WhenUserFound_shouldSaveUser() {
        when(userRepository.findByEmail("juancarlos@gmail.com")).thenReturn(Optional.of(user));
        when(passwordEncoder.encode("12345")).thenReturn("encoded-password");
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        GeneralResponse<UserResponse> res = userService.updateUserByEmail("juancarlos@gmail.com", userRequest);

        assertEquals("OK", res.getCode());
        assertEquals(StatusCodeEnum.R_001.getDescription(), res.getMessage());
        assertEquals("Juan Carlos moreno", res.getData().getFullName());
    }
    @Test
    void getAllUsers_WhenFoundUsers_shouldReturnUsers(){
        List<User> ls = new ArrayList<>();
        ls.add(user);
        when(userRepository.findByStatusTrue()).thenReturn(ls);
        GeneralResponse<List<UserResponse>> res = userService.getAllUsers();
        assertFalse(res.getData().isEmpty());
    }

    @Test
    void  deleteUserSofDelete_WhenNotFoundUsers_shouldThrowUserNotFoundException(){
        when(userRepository.findById(1l)).thenReturn(Optional.empty());
        UserNotFoundException ex = assertThrows(UserNotFoundException.class,()->{
            userService.deleteUserSofDelete(1l);
        });
    }

    @Test
    void  deleteUserSofDelete_WhenNotFoundUsers_shouldUpdateUser(){
        when(userRepository.findById(1l)).thenReturn(Optional.of(user));
        doNothing().when(userRepository).updateUserStatus(anyLong(),anyBoolean());
        GeneralResponse<Void> res = userService.deleteUserSofDelete(1l);
        assertEquals("OK", res.getCode());
        assertEquals(StatusCodeEnum.R_001.getDescription(), res.getMessage());
    }
}
