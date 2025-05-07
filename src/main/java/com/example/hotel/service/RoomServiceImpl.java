package com.example.hotel.service;

import com.example.hotel.constants.StatusCodeEnum;
import com.example.hotel.dto.GeneralResponse;
import com.example.hotel.dto.RoomResponse;
import com.example.hotel.dto.request.RoomRequest;
import com.example.hotel.ecxeption.RoomAlreadyExistsException;
import com.example.hotel.ecxeption.RoomNotFoundException;
import com.example.hotel.mapper.RoomMapper;
import com.example.hotel.model.entity.Room;
import com.example.hotel.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoomServiceImpl implements RoomService {
    @Autowired
    private RoomRepository roomRepository;


    @Override
    public GeneralResponse<Void> createRom(RoomRequest request) {
        Optional<Room> roomFound=roomRepository.findByNumber(request.getNumber());
        if(roomFound.isPresent()){
           throw new RoomAlreadyExistsException(StatusCodeEnum.R_004.getDescription());
        }
        roomRepository.save(RoomMapper.roomToRoomEntity(request));
        return GeneralResponse.<Void>builder()
                .code("OK")
                .message(StatusCodeEnum.R_001.getDescription())
                .build();
    }

    @Override
    public GeneralResponse<Void> updateRoomById(long id, RoomRequest request) {
        Room room=roomRepository.findById(id)
                .orElseThrow(()->new RoomNotFoundException(StatusCodeEnum.R_004.getDescription())) ;
        room.setType(request.getType());
        room.setDescription(request.getDescription());
        room.setPricePerNight(request.getPricePerNight());
        room.setAvailable(request.getAvailable());
        roomRepository.saveAndFlush(room);
        return GeneralResponse.<Void>builder()
                .code("OK")
                .message(StatusCodeEnum.R_001.getDescription())
                .build();
    }

    @Override
    public GeneralResponse<List<RoomResponse>> getAllRooms() {
        List<Room> list = roomRepository.findActiveRooms();
        return GeneralResponse.<List<RoomResponse>>builder()
                .code("OK")
                .message(StatusCodeEnum.R_001.getDescription())
                .data(RoomMapper.roomListEntityToRoomList(list))
                .build();
    }

    @Override
    public GeneralResponse<RoomResponse> getRoomById(long id) {
        Room roomFound=roomRepository.findById(id)
                .orElseThrow(()->new RoomNotFoundException(StatusCodeEnum.R_004.getDescription()));
        return GeneralResponse.<RoomResponse>builder()
                .code("OK")
                .data(RoomMapper.roomEntityToRoom(roomFound))
                .message(StatusCodeEnum.R_001.getDescription())
                .build();
    }

    @Override
    public GeneralResponse<Void> deleteRomSofDeleteById(long id) {
        roomRepository.findById(id)
                        .orElseThrow(()-> new RoomNotFoundException(StatusCodeEnum.R_004.getDescription()));
        roomRepository.updateRoomStatus(id,false);
        return GeneralResponse.<Void>builder()
                .code("OK")
                .message(StatusCodeEnum.R_001.getDescription())
                .build();
    }
}
