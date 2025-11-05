package com.beethere.controller;

import com.beethere.service.*;
import com.beethere.controller.AuthProxy;
import com.beethere.model.*;

import java.util.*;
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

    private ResponseEntity<?> withAuth(String token, Supplier<ResponseEntity<?>> action) {
        if (token == null || token.isEmpty()) {
            return new ResponseEntity<>("Token Required", HttpStatus.BAD_REQUEST);
        }

        try {
            Employee employee = authProxy.verifyEmployee(new TokenRequest(token));
            employee.toStringFormat();
            //String response = authProxy.verifyEmployee(new TokenRequest(token));
            //System.out.println(response);
            return action.get();
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<>("Failed to validate token", HttpStatus.UNAUTHORIZED);
        }
    }

	@GetMapping("/")
	public ResponseEntity<?> getReservations(@RequestHeader(value = "Bearer", required = false) String token) {
		return withAuth(token, () -> {
            Iterable<Reservation> rsvps = reservationService.getRsvps();
            return new ResponseEntity<>(rsvps, HttpStatus.OK);
        });
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getReservation(@RequestHeader(value = "Bearer", required = false) String token, @PathVariable("id") Integer id) {
		return withAuth(token, () -> {
            Optional<Reservation> rsvp = reservationService.getRsvp(id);
            return rsvp.isPresent()
                    ? new ResponseEntity<>(rsvp.get(), HttpStatus.OK)
                    : new ResponseEntity<>("Reservation not found", HttpStatus.NOT_FOUND);
        });
	}

	@PostMapping("/")
	public ResponseEntity<?> createReservation(@RequestHeader(value = "Bearer", required = false) String token, @RequestBody Reservation rsvp) {
		return withAuth(token, () -> {
            Reservation newRsvp = reservationService.createRsvp(rsvp);
            return new ResponseEntity<>(newRsvp, HttpStatus.CREATED);
        });
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> updateReservation(@RequestHeader(value = "Bearer", required = false) String token, @PathVariable Integer id, @RequestBody Reservation updatedRsvp) {
		return withAuth(token, () -> {
            Optional<Reservation> existing = reservationService.getRsvp(id);
            if (existing.isEmpty()) {
                return new ResponseEntity<>("Reservation not found", HttpStatus.NOT_FOUND);
            }

            Reservation rsvp = existing.get();
            rsvp.setReservationId(updatedRsvp.getReservationId());
            rsvp.setEmployeeId(updatedRsvp.getEmployeeId());
            rsvp.setEmployeeName(updatedRsvp.getEmployeeName());
            rsvp.setStartTime(updatedRsvp.getStartTime());
            rsvp.setEndTime(updatedRsvp.getEndTime());

            Reservation saved = reservationService.updateRsvp(rsvp);
            return new ResponseEntity<>(saved, HttpStatus.OK);
        });
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteReservation(@RequestHeader(value = "Bearer", required = false) String token, @PathVariable Integer id) {
		return withAuth(token, () -> {
            Optional<Reservation> existing = reservationService.getRsvp(id);
            if (existing.isEmpty()) {
                return new ResponseEntity<>("Reservation not found", HttpStatus.NOT_FOUND);
            }
            reservationService.deleteRsvp(id);
            return new ResponseEntity<>("Reservation deleted successfully", HttpStatus.NO_CONTENT);
        });
	}
}