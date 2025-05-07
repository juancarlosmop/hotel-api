package com.example.hotel.mapper;

import com.example.hotel.dto.RoomResponse;
import com.example.hotel.dto.request.RoomRequest;
import com.example.hotel.model.entity.Room;
import com.example.hotel.constants.Constants;
import java.util.List;
import java.util.stream.Collectors;

public class RoomMapper {
    public static RoomResponse roomEntityToRoom(Room room){
        return RoomResponse.builder()
                .id(room.getId())
                .number(room.getNumber())
                .type(room.getType())
                .description(room.getDescription())
                .pricePerNight(room.getPricePerNight())
                .available(Constants.ROOM_STATUS_AVAILABLE)
                .build();
    }

    public static  Room roomToRoomEntityUpdate(RoomRequest room){
        return Room.builder()
                .type(room.getType())
                .description(room.getDescription())
                .pricePerNight(room.getPricePerNight())
                .available(Constants.ROOM_STATUS_AVAILABLE)
                .build();
    }

    public static  Room roomToRoomEntity(RoomRequest room){
        return Room.builder()
                .number(room.getNumber())
                .type(room.getType())
                .description(room.getDescription())
                .pricePerNight(room.getPricePerNight())
                .available(Constants.ROOM_STATUS_AVAILABLE)
                .build();
    }

    public static List<RoomResponse> roomListEntityToRoomList(List<Room> listRoom){
        return  listRoom.stream()
                .map(room->
                        RoomResponse.builder()
                                .id(room.getId())
                                .number(room.getNumber())
                                .type(room.getType())
                                .description(room.getDescription())
                                .pricePerNight(room.getPricePerNight())
                                .available(room.getAvailable())
                                .build()
                ).collect(Collectors.toList());
    }
}
