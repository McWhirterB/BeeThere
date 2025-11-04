package com.beethere.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.beethere.model.Room;



@Repository
public interface RoomRepository extends CrudRepository<Room, Integer> {

   
}
