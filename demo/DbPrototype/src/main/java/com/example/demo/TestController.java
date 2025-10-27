package com.example.demo;

import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/test")
public class TestController {
	@Autowired
	private PersonRepository personRepository;
	@Autowired
	private RoomRepository roomRepository;
	@Autowired
	private ReservationRepository reservationRepository;
	
	@GetMapping("/")
	public @ResponseBody Iterable<Person> getPeople() {
		return personRepository.findAll();
	}

	// ROOMS
  //
	@GetMapping("/room")
	public @ResponseBody Iterable<Room> getRooms() {
		return roomRepository.findAll();
	}

	@GetMapping("/room/{id}")
	public @ResponseBody Optional<Room> getRoomById(@PathVariable("id") Integer id) {
		return roomRepository.findById(id);
	}

	@PostMapping("/room")
	public @ResponseBody Room createRoom(@RequestBody Room room) {
		return roomRepository.save(room);	
	}

  // RESERVATIONS 
  //
	@GetMapping("/reservation")
	public @ResponseBody Iterable<Reservation> getReservations() {
		return reservationRepository.findAll();
	}

	@GetMapping("/reservation/{id}")
	public @ResponseBody Optional<Reservation> getReservationById(@PathVariable("id") Integer id) {
		return reservationRepository.findById(id);
	}
}
