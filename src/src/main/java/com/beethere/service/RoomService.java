package com.beethere.service;

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

}
