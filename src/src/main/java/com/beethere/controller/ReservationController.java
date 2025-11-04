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
@RequestMapping("/reservation")
public class ReservationController {
	private AuthProxy authProxy;
	private ReservationService reservationService;

	public ReservationController(AuthProxy authProxy, ReservationService reservationService) {
		this.authProxy = authProxy;
		this.reservationService = reservationService;
	}

	@GetMapping("/")
	public ResponseEntity<?> getReservations(@RequestHeader(value = "Bearer", required = false) String token) {
		if (token == null || token.isEmpty()) {
			return new ResponseEntity<String>("Token Required", HttpStatus.BAD_REQUEST);
		}
		Employee e = null;
		
		try { 
			e = authProxy.verifyEmployee(new TokenRequest(token));	
		} catch (Exception ex) {
			return new ResponseEntity<String>("Failed to validate token", HttpStatus.UNAUTHORIZED);
		}
		Iterable<Reservation> rsvps = reservationService.getRsvps();
		return new ResponseEntity<Iterable<Reservation>>(rsvps, HttpStatus.ACCEPTED);
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getReservations(@RequestHeader(value = "Bearer", required = false) String token, @PathVariable("id") Integer id) {
		if (token == null || token.isEmpty()) {
			return new ResponseEntity<String>("Token Required", HttpStatus.BAD_REQUEST);
		}
		Employee e = null;
		
		try { 
			e = authProxy.verifyEmployee(new TokenRequest(token));	
		} catch (Exception ex) {
			return new ResponseEntity<String>("Failed to validate token", HttpStatus.UNAUTHORIZED);
		}
		Optional<Reservation> rsvp = reservationService.getRsvp(id);
		return new ResponseEntity<Optional<Reservation>>(rsvp, HttpStatus.ACCEPTED);
	}

	@PostMapping("/")
	public ResponseEntity<?> getReservations(@RequestHeader(value = "Bearer", required = false) String token, @RequestBody Reservation rsvp) {
		if (token == null || token.isEmpty()) {
			return new ResponseEntity<String>("Token Required", HttpStatus.BAD_REQUEST);
		}
		Employee e = null;
		
		try { 
			e = authProxy.verifyEmployee(new TokenRequest(token));	
		} catch (Exception ex) {
			return new ResponseEntity<String>("Failed to validate token", HttpStatus.UNAUTHORIZED);
		}
		Reservation newRsvp = reservationService.createRsvp(rsvp);
		return new ResponseEntity<Reservation>(newRsvp, HttpStatus.ACCEPTED);
	}

	@PutMapping("/")
	public ResponseEntity<?> updateReservation(@RequestHeader(value = "Bearer", required = false) String token, @RequestBody Reservation rsvp) {
		if (token == null || token.isEmpty()) {
			return new ResponseEntity<String>("Token Required", HttpStatus.BAD_REQUEST);
		}
		Employee e = null;
		
		try { 
			e = authProxy.verifyEmployee(new TokenRequest(token));	
		} catch (Exception ex) {
			return new ResponseEntity<String>("Failed to validate token", HttpStatus.UNAUTHORIZED);
		}
		Reservation newRsvp = reservationService.updateRsvp(rsvp);
		return new ResponseEntity<Reservation>(newRsvp, HttpStatus.ACCEPTED);
	}

	@DeleteMapping("/")
	public ResponseEntity<?> deleteReservation(@RequestHeader(value = "Bearer", required = false) String token, @RequestBody Reservation rsvp) {
		if (token == null || token.isEmpty()) {
			return new ResponseEntity<String>("Token Required", HttpStatus.BAD_REQUEST);
		}
		Employee e = null;
		
		try { 
			e = authProxy.verifyEmployee(new TokenRequest(token));	
		} catch (Exception ex) {
			return new ResponseEntity<String>("Failed to validate token", HttpStatus.UNAUTHORIZED);
		}
		reservationService.deleteRsvp(rsvp);
		return new ResponseEntity< >(HttpStatus.ACCEPTED);
	}
}
