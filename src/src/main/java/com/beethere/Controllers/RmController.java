package com.beethere.Controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import com.beethere.model.Employee;
import com.beethere.model.Room;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.*;

@RestController
public class RmController {

private RoomRepository roomRepository;

@RestController
@RequestMapping("/test")
public class TestController {
	private PersonRepository personRepository;
	private RoomRepository roomRepository;
	private ReservationRepository reservationRepository;


public TestController(PersonRepository personRepository,
		RoomRepository roomRepository,
		ReservationRepository reservationRepository,
		AuthProxy authProxy) {
		this.personRepository = personRepository;
		this.roomRepository = roomRepository;
		this.reservationRepository = reservationRepository;	
		this.authProxy = authProxy;
	}

private AuthProxy authProxy;

// This action return a list of rooms to the users/client
    @GetMapping("/room")
    public ResponseEntity<?> GetRooms(@RequestHeader(value = "Bearer" ) String token) {

        Employee e = null;
		if (token == null || token.isEmpty()) return new ResponseEntity<String>("Token required", HttpStatus.BAD_REQUEST);
		try {
			 e = authProxy.verifyEmployee(new TokenRequest(token));
        } 
        catch 
        (Exception ex) { 
			return new ResponseEntity<String>("Bad token", HttpStatus.BAD_REQUEST);
		}
		if 
        (e == null) {
			return new ResponseEntity<String>("Unauthorized", HttpStatus.UNAUTHORIZED);
		}
		Iterable<Room> responseBody = roomRepository.findAll();
		return new ResponseEntity<Iterable<Room>>(responseBody, HttpStatus.ACCEPTED);
	}
        // System.out.println("200");
        // return new ArrayList<Room>();
        
    }

    /*@GetMapping("/")
    public String base() {
        return "Root element";
    }*/
}

