package com.beethere.controller;

import com.beethere.service.*;
import com.beethere.controller.AuthProxy;
import com.beethere.model.*;

import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;

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

    private ResponseEntity<?> withAuth(String token, Function<Employee, ResponseEntity<?>> action) {
        if (token == null || token.isEmpty()) {
            return new ResponseEntity<>("Token Required", HttpStatus.BAD_REQUEST);
        }
        try {
            Employee employee = authProxy.verifyEmployee(new TokenRequest(token));
            employee.toStringFormat(); // optional, maybe for logging
            return action.apply(employee);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<>("Failed to validate token", HttpStatus.UNAUTHORIZED);
        }
    }

	@GetMapping("/")
	public ResponseEntity<?> getReservations(@RequestHeader(value = "Bearer", required = false) String token) {
		return withAuth(token, employee -> {
            Iterable<Reservation> rsvps = reservationService.getRsvps();
            return new ResponseEntity<>(rsvps, HttpStatus.OK);
        });
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getReservation(@RequestHeader(value = "Bearer", required = false) String token, @PathVariable("id") Integer id) {
		return withAuth(token, employee -> {
            Optional<Reservation> rsvp = reservationService.getRsvp(id);
            return rsvp.isPresent()
                    ? new ResponseEntity<>(rsvp.get(), HttpStatus.OK)
                    : new ResponseEntity<>("Reservation not found", HttpStatus.NOT_FOUND);
        });
	}

	@PostMapping("/")
	public ResponseEntity<?> createReservation(@RequestHeader(value = "Bearer", required = false) String token, @RequestBody Reservation rsvp) {
		return withAuth(token, employee -> {
            Reservation newRsvp = reservationService.createRsvp(rsvp);
            return new ResponseEntity<>(newRsvp, HttpStatus.CREATED);
        });
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> updateReservation(@RequestHeader(value = "Bearer", required = false) String token, @PathVariable Integer id, @RequestBody Reservation updatedRsvp) {
		return withAuth(token, employee -> {
            Optional<Reservation> existing = reservationService.getRsvp(id);
            if (existing.isEmpty()) {
                return new ResponseEntity<>("Reservation not found", HttpStatus.NOT_FOUND);
            }

            Reservation rsvp = existing.get();
            rsvp.setReservationId(updatedRsvp.getReservationId());
            rsvp.setEmployeeId(updatedRsvp.getEmployeeId());
            rsvp.setEmployeeName(updatedRsvp.getEmployeeName());
            rsvp.setEndTime(updatedRsvp.getEndTime());
            rsvp.setStartTime(updatedRsvp.getStartTime());

            Reservation saved = reservationService.updateRsvp(rsvp, employee);
            return new ResponseEntity<>(saved, HttpStatus.OK);
        });
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteReservation(@RequestHeader(value = "Bearer", required = false) String token, @PathVariable Integer id) {
		return withAuth(token, employee -> {
            Optional<Reservation> existing = reservationService.getRsvp(id);
            if (existing.isEmpty()) {
                return new ResponseEntity<>("Reservation not found", HttpStatus.NOT_FOUND);
            }
            reservationService.deleteRsvp(id, employee);
            return new ResponseEntity<>("Reservation deleted successfully", HttpStatus.NO_CONTENT);
        });
	}
}