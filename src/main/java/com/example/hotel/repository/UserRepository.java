package com.example.hotel.repository;

import com.example.hotel.model.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByEmail(String email);
    List<User> findByStatusTrue();
    @Modifying
    @Query("UPDATE User u SET u.status = :status WHERE u.id = :id")
    @Transactional
    void updateUserStatus(@Param("id") Long id, @Param("status") boolean status);

}
