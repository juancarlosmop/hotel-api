package com.example.hotel.service;

import com.example.hotel.dto.GeneralResponse;
import com.example.hotel.dto.ReservationResponse;
import com.example.hotel.dto.request.ReservationRequest;
import com.example.hotel.ecxeption.BusinessException;
import com.example.hotel.ecxeption.ReservationNotFoundException;

import java.util.List;

public interface ReservationService {
    /**
     * Receives an object type of reservation to create
     * a new reservation
     *
     * @param request Object with the credentials
     * @return GeneralResponse common response
     * @throws BusinessException if something happened like a validation failed
     * @author Juan Carlos
     */
    public GeneralResponse<Void> createReservation(ReservationRequest request);
    /**
     * Get a Reservation by id
     *
     * @param id id reservation
     * @return GeneralResponse common response
     * @throws ReservationNotFoundException if the reservation not exist
     * @author Juan Carlos
     */
    public GeneralResponse<ReservationResponse> getReservationById(long id);
    /**
     * Get list of available reservations
     *
     * @return GeneralResponse common response with a reservations data
     * @author Juan Carlos
     */
    public GeneralResponse<List<ReservationResponse>> getReservations();
    /**
     * Get all reservations by user id
     *
     * @param id user id
     * @return GeneralResponse common response with a list of reservations
     * @throws ReservationNotFoundException if the reservation not exist
     * @author Juan Carlos
     */
    public GeneralResponse<List<ReservationResponse>> getReservationsByIdUser(long id);
    /**
     * Cancel a reservation by id of reservation
     *
     * @param id id reservation
     * @return GeneralResponse common response
     * @throws ReservationNotFoundException if the reservation not exist
     * @author Juan Carlos
     */
    public GeneralResponse<Void> cancelReservation(long id);


}
