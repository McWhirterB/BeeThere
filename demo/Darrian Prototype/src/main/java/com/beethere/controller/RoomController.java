package com.beethere.controller;

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
	private AuthProxy authProxy;
	private RoomService roomService;

	public RoomController(AuthProxy authProxy, RoomService roomService) {
		this.authProxy = authProxy;
		this.roomService = roomService;
	}

	@GetMapping("/")
	public ResponseEntity<?> getRooms(@RequestHeader(value = "Bearer", required = false) String token) {
		if (token == null || token.isEmpty()) {
			return new ResponseEntity<String>("Token Required", HttpStatus.BAD_REQUEST);
		}
		Employee e = null;
		
		try { 
			e = authProxy.verifyEmployee(new TokenRequest(token));	
		} catch (Exception ex) {
			return new ResponseEntity<String>("Failed to validate token", HttpStatus.UNAUTHORIZED);
		}
		Iterable<Room> rooms = roomService.getRooms();
		return new ResponseEntity<Iterable<Room>>(rooms, HttpStatus.ACCEPTED);
		//Employee e = authProxy.verifyEmployee(new TokenRequest(token));
		//return new ResponseEntity<Employee>(e, HttpStatus.ACCEPTED);
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getRoomById(@RequestHeader(value = "Bearer", required = false) String token, @PathVariable("id") Integer id) {
		if (token == null || token.isEmpty()) {
			return new ResponseEntity<String>("Token Required", HttpStatus.BAD_REQUEST);
		}
		Employee e = null;

		try { 
			e = authProxy.verifyEmployee(new TokenRequest(token));	
		} catch (Exception ex) {
			return new ResponseEntity<String>("Failed to validate token", HttpStatus.UNAUTHORIZED);
		}
		//Optional<Room> room = roomService.getRoomById(id);
		//return new ResponseEntity<Optional<Room>>(room, HttpStatus.ACCEPTED);
		return new ResponseEntity<Employee>(e, HttpStatus.ACCEPTED);
	}

	@PostMapping("/")
	public ResponseEntity<?> createRoom(@RequestHeader(value = "Bearer", required = false) String token, @RequestBody Room room) {
		if (token == null || token.isEmpty()) {
			return new ResponseEntity<String>("Token Required", HttpStatus.BAD_REQUEST);
		}
		Employee e = null;

		try { 
			e = authProxy.verifyEmployee(new TokenRequest(token));	
		} catch (Exception ex) {
			return new ResponseEntity<String>("Failed to validate token", HttpStatus.UNAUTHORIZED);
		}
		//Optional<Room> newRoom = roomService.createRoom(room);
		//return new ResponseEntity<Optional<Room>>(newRoom, HttpStatus.ACCEPTED);
		return new ResponseEntity<Employee>(e, HttpStatus.ACCEPTED);
	}
}
