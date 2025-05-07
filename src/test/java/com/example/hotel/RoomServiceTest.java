package com.example.hotel;

import com.example.hotel.constants.StatusCodeEnum;
import com.example.hotel.dto.GeneralResponse;
import com.example.hotel.dto.RoomResponse;
import com.example.hotel.dto.request.RoomRequest;
import com.example.hotel.ecxeption.RoomAlreadyExistsException;
import com.example.hotel.ecxeption.RoomNotFoundException;
import com.example.hotel.model.entity.Room;
import com.example.hotel.repository.RoomRepository;
import com.example.hotel.service.RoomServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RoomServiceTest {
    @InjectMocks
    private RoomServiceImpl roomService;

    @Mock
    private RoomRepository roomRepository;
    @Test
    void createRom_whenRoomNotExist_shouldSaveRoom() {
        RoomRequest request = RoomRequest.builder().number("101").build();

        when(roomRepository.findByNumber(anyString())).thenReturn(Optional.empty());
        when(roomRepository.save(any())).thenReturn(new Room());

        GeneralResponse<Void> response = roomService.createRom(request);

        assertEquals("OK", response.getCode());
        assertEquals(StatusCodeEnum.R_001.getDescription(), response.getMessage());
    }

    @Test
    void createRom_whenRoomExists_shouldThrowException() {
        RoomRequest request = RoomRequest.builder().number("101").build();

        when(roomRepository.findByNumber("101")).thenReturn(Optional.of(new Room()));

        assertThrows(RoomAlreadyExistsException.class, () -> roomService.createRom(request));
    }
    @Test
    void updateRoomById_whenRoomExists_shouldUpdateRoom() {
        long id = 1L;
        RoomRequest request = RoomRequest.builder()
                .type("Deluxe")
                .description("Updated description")
                .pricePerNight(BigDecimal.valueOf(200))
                .available(true)
                .build();

        Room room = new Room();
        when(roomRepository.findById(id)).thenReturn(Optional.of(room));
        when(roomRepository.saveAndFlush(any())).thenReturn(room);

        GeneralResponse<Void> response = roomService.updateRoomById(id, request);

        assertEquals("OK", response.getCode());
    }

    @Test
    void updateRoomById_whenRoomNotFound_shouldThrowException() {
        long id = 1L;
        RoomRequest request = RoomRequest.builder().build();

        when(roomRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(RoomNotFoundException.class, () -> roomService.updateRoomById(id, request));
    }
    @Test
    void getAllRooms_shouldReturnListOfRooms() {
        when(roomRepository.findActiveRooms()).thenReturn(List.of(new Room(), new Room()));

        GeneralResponse<List<RoomResponse>> response = roomService.getAllRooms();

        assertEquals("OK", response.getCode());
        assertEquals(2, response.getData().size());
    }
    @Test
    void getRoomById_whenRoomExists_shouldReturnRoom() {
        Room room = new Room();
        room.setId(1L);

        when(roomRepository.findById(1L)).thenReturn(Optional.of(room));

        GeneralResponse<RoomResponse> response = roomService.getRoomById(1L);

        assertEquals("OK", response.getCode());
        assertNotNull(response.getData());
    }
    @Test
    void getRoomById_whenRoomNotFound_shouldThrowException() {
        when(roomRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RoomNotFoundException.class, () -> roomService.getRoomById(1L));
    }
    @Test
    void deleteRomSofDeleteById_whenRoomExists_shouldUpdateStatus() {
        long id = 1L;
        when(roomRepository.findById(id)).thenReturn(Optional.of(new Room()));
        doNothing().when(roomRepository).updateRoomStatus(id, false);

        GeneralResponse<Void> response = roomService.deleteRomSofDeleteById(id);

        assertEquals("OK", response.getCode());
    }
    @Test
    void deleteRomSofDeleteById_whenRoomNotFound_shouldThrowException() {
        when(roomRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RoomNotFoundException.class, () -> roomService.deleteRomSofDeleteById(1L));
    }









}
