package com.beethere.controller;

import java.util.Optional;
import java.util.function.Function;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.beethere.model.Employee;
import com.beethere.model.Reservation;
import com.beethere.model.TokenRequest;
import com.beethere.service.ReservationService;

@RestController
@RequestMapping("/reservations")
public class ReservationController {
	private AuthProxy authProxy;
	private ReservationService reservationService;

    private static final Logger APPLICATION_LOGGER = LogManager.getLogger("Application");
    private static final Logger SECURITY_LOGGER = LogManager.getLogger("Security");

	public ReservationController(AuthProxy authProxy, ReservationService reservationService) {
        APPLICATION_LOGGER.debug("Constructing ReservationController");
		this.authProxy = authProxy;
		this.reservationService = reservationService;
	}

    private ResponseEntity<?> withAuth(String token, Function<Employee, ResponseEntity<?>> action) {
        if (token == null || token.isEmpty()) {
            SECURITY_LOGGER.error("Authentication failed: Token is missing ");
            return new ResponseEntity<>("Token Required", HttpStatus.BAD_REQUEST);
        }
        try {
            Employee employee = authProxy.verifyEmployee(new TokenRequest(token));
            employee.toStringFormat(); // optional, maybe for logging
            return action.apply(employee);
        } catch (Exception ex) {
            ex.printStackTrace();
            SECURITY_LOGGER.error("Authentication failed: Invalid token");
            return new ResponseEntity<>("Failed to validate token", HttpStatus.UNAUTHORIZED);
        }
    }

	@GetMapping({"", "/"})
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