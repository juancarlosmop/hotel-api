package com.example.hotel.mapper;

import com.example.hotel.dto.ReservationResponse;
import com.example.hotel.dto.request.ReservationRequest;
import com.example.hotel.model.entity.Reservation;
import com.example.hotel.model.entity.Room;
import com.example.hotel.model.entity.User;

import java.util.List;
import java.util.stream.Collectors;

public class ReservationMapper {
    public static ReservationResponse reservationEntityToReservation(Reservation reservation){
        return ReservationResponse.builder()
                .id(reservation.getId())
                .user(UserMapper.userEntityToUser(reservation.getUser()))
                .room(RoomMapper.roomEntityToRoom(reservation.getRoom()))
                .checkInDate(reservation.getCheckInDate())
                .checkOutDate(reservation.getCheckOutDate())
                .total(reservation.getTotal())
                .build();
    }

    public static  Reservation reservationToReservationEntity(ReservationRequest request, User user, Room room){
        return Reservation.builder()
                .user(user)
                .room(room)
                .checkInDate(request.getCheckInDate())
                .checkOutDate(request.getCheckOutDate())
                .total(request.getTotal())
                .status(true)
                .build();
    }

    public static List<ReservationResponse> reservationListEntityToReservationList(List<Reservation> list){
        return  list.stream()
                .map(reservation->
                        ReservationResponse.builder()
                                .id(reservation.getId())
                                .user(UserMapper.userEntityToUser(reservation.getUser()))
                                .room(RoomMapper.roomEntityToRoom(reservation.getRoom()))
                                .checkInDate(reservation.getCheckInDate())
                                .checkOutDate(reservation.getCheckOutDate())
                                .total(reservation.getTotal())
                                .build()
                ).collect(Collectors.toList());
    }
}
