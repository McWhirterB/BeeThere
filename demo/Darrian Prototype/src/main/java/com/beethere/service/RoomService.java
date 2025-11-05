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

	public Optional<Room> getRoomById(Integer id) {
		return roomRepository.findById(id);
	}

	//public Optional<Room> createRoom(Room room) {
	//	return roomRepository.save(room);	
	//}

}
