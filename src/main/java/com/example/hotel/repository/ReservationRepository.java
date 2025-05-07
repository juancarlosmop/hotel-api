package com.example.hotel.repository;

import com.example.hotel.model.entity.Reservation;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation,Long> {
    @Query("SELECT r FROM Reservation r JOIN FETCH r.room JOIN FETCH r.user  WHERE  r.status = true")
    List<Reservation> findAllActiveReservations();

    @Query("SELECT r FROM Reservation r JOIN FETCH r.room JOIN FETCH r.user u WHERE u.id = :userId")
    List<Reservation> findAllByUserIdWithRoom(@Param("userId") Long userId);

    @Modifying
    @Transactional
    @Query("UPDATE  Reservation r SET r.status = :status WHERE r.id =:id")
    void updateStatusReservation(@Param("status") boolean status,@Param("id") long id);
}
