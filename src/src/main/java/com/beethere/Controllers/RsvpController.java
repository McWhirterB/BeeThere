package com.beethere.Controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.beethere.model.Employee;
import com.beethere.model.Reservation;
import com.beethere.model.Room;

import java.util.*;

@RestController
@RequestMapping("/rsvp")
public class RsvpController {



private AuthProxy authProxy;
// This action return a list of reservations for users/client

private  RsvpService rsvpService;


	public RsvpController(
		RsvpService rsvpService,
		AuthProxy authProxy) {
		this.authProxy = authProxy;
		this.rsvpService = rsvpService;
	}

    @GetMapping("/")
   
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
		Iterable<Rsvp> responseBody = rsvpService.findAll();
		return new ResponseEntity<Iterable<Rsvp>>(responseBody, HttpStatus.ACCEPTED);
	}
        

    }

