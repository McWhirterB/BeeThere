package com.beethere.service;

import java.util.*;
import com.beethere.model.*;
import com.beethere.repository.*;
import org.springframework.stereotype.Service;

@Service
public class RoomService {
	private RoomRepository roomRepository;

	public RoomService(RoomRepository roomRepository) {
		this.roomRepository = roomRepository;
	}

	public Iterable<Room> getRooms() {
		return roomRepository.findAll();
	}

	//public Iterable<Room> getOpenRooms(OpenRoomRequest request) {
	//	return null;
	//	//return roomRepository.getOpenRooms(request.startTime, request.endTime);
	//}

	public Optional<Room> getRoomById(String id) {
		return roomRepository.findById(id);
	}

	public Room createRoom(Room room) {
		return roomRepository.save(room);	
		// return Optional.ofNullable(roomRepository.save(room))
	}
	
	public Room updateRoom(Room room) {
		if (roomRepository.existsById(room.roomNumber)) {
			return roomRepository.save(room);
		} 

		return null;
	}

	public void deleteRoom(Room room) {
		roomRepository.delete(room);
	}

}
