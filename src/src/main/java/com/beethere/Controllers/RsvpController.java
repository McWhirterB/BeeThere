package com.beethere.Controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.beethere.model.Employee;
import com.beethere.model.Reservation;
import com.beethere.model.Room;

import java.util.*;

@RestController
public class RsvpController {

private AuthProxy authProxy;
// This action return a list of reservations for users/client
    @GetMapping("/reservation")
    /*public ArrayList<Reservation> GetRsvp(@RequestHeader(value = "Bearer" ) String token) {
        
        authProxy.verifyEmployee(new TokenRequest(token));
        System.out.println("200");
        // else System.err.println();
        return new ArrayList<Reservation>();
        */
    public ResponseEntity<?> GetRsvp(@RequestHeader(value = "Bearer" ) String token) {

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
		Iterable<Rsvp> responseBody = rsvpRepository.findAll();
		return new ResponseEntity<Iterable<Rsvp>>(responseBody, HttpStatus.ACCEPTED);
	}
        

    }
}
