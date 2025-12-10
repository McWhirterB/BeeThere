package com.beethere.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.beethere.model.Room;

@Repository
public interface RoomRepository extends CrudRepository<Room, Integer> {
    List<Room> findByCountry(String country);

    List<Room> findByType(String type);

    List<Room> findByCountryAndType(String country, String type);


    @Query(value = "SELECT * FROM room WHERE location = ?1 AND room_id NOT IN (SELECT tr.room_id FROM reservation r JOIN reservation_room tr ON r.reservation_id = tr.reservation_id WHERE start_time >= ?2 AND end_time <= ?3);", nativeQuery = true)
    List<Room> findByCountryAndAvailability(String country, LocalDateTime startTime, LocalDateTime endTime);

}
