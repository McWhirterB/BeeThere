package com.beethere.repository;

import java.util.*;
import com.beethere.model.*;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends CrudRepository<Room, String> {
	
	//@Query("SELECT * FROM room r WHERE roomNumber NOT IN (SELECT * FROM reservation rs WHERE rs.roomNumber = r.roomNumber 
	//Iterable<Room> getOpenRooms(Date startTime, Date endTime);
}
