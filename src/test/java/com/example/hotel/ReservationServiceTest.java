package com.example.hotel;

import com.example.hotel.constants.StatusCodeEnum;
import com.example.hotel.dto.GeneralResponse;
import com.example.hotel.dto.ReservationResponse;
import com.example.hotel.dto.request.ReservationRequest;
import com.example.hotel.ecxeption.BusinessException;
import com.example.hotel.ecxeption.ReservationNotFoundException;
import com.example.hotel.ecxeption.RoomNotFoundException;
import com.example.hotel.ecxeption.UserNotFoundException;
import com.example.hotel.model.entity.Reservation;
import com.example.hotel.model.entity.Room;
import com.example.hotel.model.entity.User;
import com.example.hotel.repository.ReservationRepository;
import com.example.hotel.repository.RoomRepository;
import com.example.hotel.repository.UserRepository;
import com.example.hotel.service.ReservationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class ReservationServiceTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private RoomRepository roomRepository;
    @Mock
    private ReservationRepository reservationRepository;
    @InjectMocks
    private ReservationServiceImpl reservationService;

    private User user;
    private Room room;
    @BeforeEach
    void upSet(){
        user=User.builder()
                .id(1l)
                .email("juancarlos@gmail.com")
                .fullName("Juan Carlos")
                .password("1234")
                .status(true)
                .role("USER")
                .build();
        room=Room.builder()
                .id(1l)
                .available(true)
                .type("SUIT")
                .description("CUARTO")
                .pricePerNight(new BigDecimal(234.00))
                .number("13")
                .build();

    }
    @Test
    void createReservation_userNotExist_shouldReturnUserNotFoundException(){
        ReservationRequest request = ReservationRequest.builder()
                .idUser(2l)
                .idRoom(1l)
                .checkInDate(LocalDate.parse("2025-04-22"))
                .checkOutDate(LocalDate.parse("2024-04-23"))
                .total(new BigDecimal(123))
                .build();
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());
        UserNotFoundException ex = assertThrows(UserNotFoundException.class,()->{
            reservationService.createReservation(request);
        });
    }

    @Test
    void createReservation_roomNotExist_shouldReturnRoomNotFoundException(){
        ReservationRequest request = ReservationRequest.builder()
                .idUser(1l)
                .idRoom(2l)
                .checkInDate(LocalDate.parse("2025-04-22"))
                .checkOutDate(LocalDate.parse("2024-04-23"))
                .total(new BigDecimal(123))
                .build();
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(roomRepository.findById(anyLong())).thenReturn(Optional.empty());
        RoomNotFoundException ex = assertThrows(RoomNotFoundException.class,()->{
            reservationService.createReservation(request);
        });

    }
    @Test
    void createReservation_whenCheckInIsBeforeTheCurrentDate_shouldReturnBusinessException(){
        ReservationRequest request = ReservationRequest.builder()
                .idUser(1l)
                .idRoom(1l)
                .checkInDate(LocalDate.parse("2025-04-22"))
                .checkOutDate(LocalDate.parse("2024-04-23"))
                .total(new BigDecimal(123))
                .build();
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(roomRepository.findById(anyLong())).thenReturn(Optional.of(room));
        BusinessException ms = assertThrows(BusinessException.class,()->{
            reservationService.createReservation(request);
        });
        assertEquals("The check-in date can not be in the past ",ms.getMessage());

    }
    @Test
    void createReservation_whenCheckInIsLessThanCheckOut_shouldReturnBusinessException(){
        ReservationRequest request = ReservationRequest.builder()
                .idUser(1l)
                .idRoom(1l)
                .checkInDate(LocalDate.parse("2025-05-28"))
                .checkOutDate(LocalDate.parse("2024-05-26"))
                .total(new BigDecimal(123))
                .build();
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(roomRepository.findById(anyLong())).thenReturn(Optional.of(room));
        BusinessException ms = assertThrows(BusinessException.class,()->{
            reservationService.createReservation(request);
        });
        assertEquals("The check-in date must be less than check-out",ms.getMessage());

    }
    @Test
    void createReservation_whenCheckEqualsThanCheckOut_shouldReturnBusinessException(){
        ReservationRequest request = ReservationRequest.builder()
                .idUser(1l)
                .idRoom(1l)
                .checkInDate(LocalDate.parse("2025-05-28"))
                .checkOutDate(LocalDate.parse("2025-05-28"))
                .total(new BigDecimal(123))
                .build();
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(roomRepository.findById(anyLong())).thenReturn(Optional.of(room));
        BusinessException ms = assertThrows(BusinessException.class,()->{
            reservationService.createReservation(request);
        });
        assertEquals("The check-in cant not be equal than check-out",ms.getMessage());

    }

    @Test
    void createReservation_whenSave_shouldReturnGeneralResponse(){
        ReservationRequest request = ReservationRequest.builder()
                .idUser(1l)
                .idRoom(1l)
                .checkInDate(LocalDate.parse("2025-05-26"))
                .checkOutDate(LocalDate.parse("2025-05-28"))
                .total(new BigDecimal(123))
                .build();

        Reservation reservation = Reservation.builder()
                .id(1l)
                .user(user)
                .room(room)
                .checkInDate(LocalDate.parse("2025-04-26"))
                .checkOutDate(LocalDate.parse("2025-04-28"))
                .total(new BigDecimal(123))
                .build();
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(roomRepository.findById(anyLong())).thenReturn(Optional.of(room));
        when(reservationRepository.save(any(Reservation.class))).thenReturn(reservation);
        doNothing().when(roomRepository).updateRoomStatus(1l,false);
        GeneralResponse<Void> res= reservationService.createReservation(request);
        assertEquals("OK",res.getCode());
        assertEquals("Success",res.getMessage());
    }

    @Test
    void getReservationById_validId_shouldReturnReservation() {
        Reservation reservation = Reservation.builder()
                .id(1l)
                .user(user)
                .room(room)
                .checkInDate(LocalDate.parse("2025-04-26"))
                .checkOutDate(LocalDate.parse("2025-04-28"))
                .total(new BigDecimal(123))
                .build();

        when(reservationRepository.findById(1L)).thenReturn(Optional.of(reservation));

        GeneralResponse<ReservationResponse> response = reservationService.getReservationById(1L);

        assertEquals("OK", response.getCode());
        assertEquals(StatusCodeEnum.R_001.getDescription(), response.getMessage());
        assertNotNull(response.getData());
    }
    @Test
    void getReservationById_invalidId_shouldThrowReservationNotFoundException() {
        when(reservationRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ReservationNotFoundException.class, () -> reservationService.getReservationById(1L));
    }
    @Test
    void getReservations_shouldReturnListOfReservations() {
        Reservation reservation = Reservation.builder()
                .id(1l)
                .user(user)
                .room(room)
                .checkInDate(LocalDate.parse("2025-05-26"))
                .checkOutDate(LocalDate.parse("2025-05-28"))
                .total(new BigDecimal(123))
                .build();
        List<Reservation> reservations= new ArrayList<>();
        reservations.add(reservation);

        when(reservationRepository.findAllActiveReservations()).thenReturn(reservations);

        GeneralResponse<List<ReservationResponse>> response = reservationService.getReservations();

        assertEquals("OK", response.getCode());
        assertEquals(1, response.getData().size());
    }
    @Test
    void getReservationsByIdUser_validId_shouldReturnReservations() {
        Reservation reservation = Reservation.builder()
                .id(1l)
                .user(user)
                .room(room)
                .checkInDate(LocalDate.parse("2025-04-26"))
                .checkOutDate(LocalDate.parse("2025-04-28"))
                .total(new BigDecimal(123))
                .build();
        List<Reservation> reservations= new ArrayList<>();
        reservations.add(reservation);

        when(reservationRepository.findAllByUserIdWithRoom(1L)).thenReturn(reservations);

        GeneralResponse<List<ReservationResponse>> response = reservationService.getReservationsByIdUser(1L);

        assertEquals("OK", response.getCode());
        assertFalse(response.getData().isEmpty());
    }
    @Test
    void cancelReservation_validId_shouldUpdateStatus() {
        Reservation reservation = new Reservation();
        reservation.setId(1L);
        reservation.setRoom(room);
        when(reservationRepository.findById(1L)).thenReturn(Optional.of(reservation));
        doNothing().when(reservationRepository).updateStatusReservation(anyBoolean(), anyLong());
        doNothing().when(roomRepository).updateRoomStatus(anyLong(),anyBoolean());
        GeneralResponse<Void> response = reservationService.cancelReservation(1L);

        assertEquals("OK", response.getCode());
        assertEquals(StatusCodeEnum.R_001.getDescription(), response.getMessage());
    }
    @Test
    void cancelReservation_invalidId_shouldThrowReservationNotFoundException() {
        when(reservationRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ReservationNotFoundException.class, () -> reservationService.cancelReservation(99L));
    }


}
