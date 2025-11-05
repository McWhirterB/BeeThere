package com.beethere.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.beethere.model.Room;

@Repository
public interface RoomRepository extends CrudRepository<Room, Integer> {
    List<Room> findByLocation(String location);

    List<Room> findByType(String type);

    List<Room> findByLocationAndType(String location, String type);

}
