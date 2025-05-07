package com.example.hotel.service;

import com.example.hotel.dto.GeneralResponse;
import com.example.hotel.dto.RoomResponse;
import com.example.hotel.dto.request.RoomRequest;
import com.example.hotel.ecxeption.RoomNotFoundException;

import java.util.List;

public interface RoomService {
    /**
     * Received a dto object with the room to create
     * a new room in database
     *
     * @param roomRequest Object with room params
     * @return GeneralResponse common response
     * @throws RoomNotFoundException if the room was not found
     * @author Juan Carlos
     */
    public GeneralResponse<Void> createRom(RoomRequest roomRequest);
    /**
     * Received a dto object with id room to update
     * a new room in database
     *
     * @param id id Room
     * @param user Object of room
     * @return GeneralResponse common response
     * @throws RoomNotFoundException if the room was not found
     * @author Juan Carlos
     */
    public GeneralResponse<Void> updateRoomById(long id, RoomRequest user);
    /**
     * Get all available rooms
     *
     * @return GeneralResponse common response with data of all rooms
     * @author Juan Carlos
     */
    public GeneralResponse<List<RoomResponse>> getAllRooms();
    /**
     * Receives an object type of reservation to create
     * a new reservation
     *
     * @param id of room
     * @return GeneralResponse with Room found
     * @throws RoomNotFoundException if something happened like a validation failed
     * @author Juan Carlos
     */
    public GeneralResponse<RoomResponse> getRoomById(long id);
    /**
     * Receives an id of room to sof delete
     *
     * @param id of room
     * @throws RoomNotFoundException if something happened like a validation failed
     * @author Juan Carlos
     */
    public GeneralResponse<Void> deleteRomSofDeleteById(long id);
}
