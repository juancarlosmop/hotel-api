package com.example.hotel.service;

import com.example.hotel.constants.Constants;
import com.example.hotel.constants.StatusCodeEnum;
import com.example.hotel.dto.GeneralResponse;
import com.example.hotel.dto.ReservationResponse;
import com.example.hotel.dto.request.ReservationRequest;
import com.example.hotel.ecxeption.BusinessException;
import com.example.hotel.ecxeption.ReservationNotFoundException;
import com.example.hotel.ecxeption.RoomNotFoundException;
import com.example.hotel.ecxeption.UserNotFoundException;
import com.example.hotel.mapper.ReservationMapper;
import com.example.hotel.model.entity.Reservation;
import com.example.hotel.model.entity.Room;
import com.example.hotel.model.entity.User;
import com.example.hotel.repository.ReservationRepository;
import com.example.hotel.repository.RoomRepository;
import com.example.hotel.repository.UserRepository;
import com.example.hotel.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ReservationServiceImpl implements ReservationService {
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoomRepository roomRepository;
    @Override
    public GeneralResponse<Void> createReservation(ReservationRequest request) {
        User userFound = userRepository.findById(request.getIdUser())
                .orElseThrow(()-> new UserNotFoundException(StatusCodeEnum.R_003.getDescription()));

        Room roomFound = roomRepository.findById(request.getIdRoom())
                .orElseThrow(()-> new RoomNotFoundException(StatusCodeEnum.R_004.getDescription()));

        validationDates(request.getCheckInDate(),request.getCheckOutDate());
        roomRepository.updateRoomStatus(request.getIdRoom(), Constants.ROOM_STATUS_NOT_AVAILABLE);
        reservationRepository.save(ReservationMapper.reservationToReservationEntity(request,userFound,roomFound));
        return GeneralResponse.<Void>builder()
                .code("OK")
                .message(StatusCodeEnum.R_001.getDescription())
                .build();
    }

    private void validationDates(LocalDate checkIn,LocalDate checkOut){
        if(checkIn.isBefore(DateUtil.getCurrentDate())){
            throw new BusinessException(StatusCodeEnum.R_009.getDescription());

        }
        if(checkIn.isAfter(checkOut) ){
            throw new BusinessException(StatusCodeEnum.R_010.getDescription());

        }
        if (checkIn.isEqual(checkOut)){
            throw new BusinessException(StatusCodeEnum.R_011.getDescription());

        }

    }

    @Override
    public GeneralResponse<ReservationResponse> getReservationById(long id) {
        Reservation reservationFound = reservationRepository.findById(id)
                .orElseThrow(()->new ReservationNotFoundException(StatusCodeEnum.R_006.getDescription()));
        return GeneralResponse.<ReservationResponse>builder()
                .code("OK")
                .message(StatusCodeEnum.R_001.getDescription())
                .data(ReservationMapper.reservationEntityToReservation(reservationFound))
                .build();
    }

    @Override
    public GeneralResponse<List<ReservationResponse>> getReservations() {
        List<Reservation> ls = reservationRepository.findAllActiveReservations();
        return GeneralResponse.<List<ReservationResponse>>builder()
                .code("OK")
                .message(StatusCodeEnum.R_001.getDescription())
                .data(ReservationMapper.reservationListEntityToReservationList(ls))
                .build();
    }

    @Override
    public GeneralResponse<List<ReservationResponse>> getReservationsByIdUser(long id) {
        List<Reservation> lsReservation=reservationRepository.findAllByUserIdWithRoom(id);
        return GeneralResponse.<List<ReservationResponse>>builder()
                .code("OK")
                .message(StatusCodeEnum.R_001.getDescription())
                .data(ReservationMapper.reservationListEntityToReservationList(lsReservation))
                .build();
    }

    @Override
    public GeneralResponse<Void> cancelReservation(long id) {
        Reservation reservation=reservationRepository.findById(id)
                .orElseThrow(()->new ReservationNotFoundException(StatusCodeEnum.R_006.getDescription()));
        reservationRepository.updateStatusReservation(Constants.ROOM_STATUS_RESERVATION_DISABLE,id);
        roomRepository.updateRoomStatus(reservation.getRoom().getId(),Constants.ROOM_STATUS_AVAILABLE);
        return GeneralResponse.<Void>builder()
                .code("OK")
                .message(StatusCodeEnum.R_001.getDescription())
                .build();
    }
}
