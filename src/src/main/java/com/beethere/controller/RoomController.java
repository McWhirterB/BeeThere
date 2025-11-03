package com.beethere;

import com.beethere.service.*;

import com.beethere.model.*;

import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/room")
public class RoomController {
	private RoomService roomService;

	public RoomController(RoomService roomService) {
		this.roomService = roomService;
	}

	@GetMapping("/")
	public ResponseEntity<?> testEndpoint() {
		Iterable<Room> rooms = roomService.getRooms();
		return new ResponseEntity<Iterable<Room>>(rooms, HttpStatus.ACCEPTED);
	}
}
