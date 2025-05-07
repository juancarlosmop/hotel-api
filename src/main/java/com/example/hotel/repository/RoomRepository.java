package com.example.hotel.repository;

import com.example.hotel.model.entity.Room;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<Room,Long> {
    @Query("SELECT r FROM Room r WHERE r.available = true")
    List<Room> findActiveRooms();

    @Query("SELECT r FROM Room r WHERE r.id = :id and  r.available = true")
    Optional<Room> findActiveRoom(@Param("id") Long id, @Param("status") boolean status);

    Optional<Room> findByNumber(String number);

    @Modifying
    @Query("UPDATE Room r SET r.available = :status WHERE r.id = :id")
    @Transactional
    void updateRoomStatus(@Param("id") Long id, @Param("status") boolean status);
}
