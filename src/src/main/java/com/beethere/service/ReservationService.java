package com.beethere.service;

import java.util.*;
import com.beethere.model.*;
import com.beethere.repository.*;

import org.springframework.stereotype.Service;

@Service
public class ReservationService {
	private ReservationRepository reservationRepository;

	public ReservationService(ReservationRepository reservationRepository) {
		this.reservationRepository = reservationRepository;
	}

	public Iterable<Reservation> getRsvps() {
		return reservationRepository.findAll();
	}

	public Optional<Reservation> getRsvp(Integer id) {
		return reservationRepository.findById(id);
	}

	public Reservation createRsvp(Reservation rsvp) {
		return reservationRepository.save(rsvp);
	}

    public Reservation overrideRsvp(Integer id, Reservation rsvp, Employee e) {
        if (!e.isManager()) {
            throw new RuntimeException("Insufficient permissions to override reservation");
        }
        
        reservationRepository.deleteById(id);
        return reservationRepository.save(rsvp);
	}

	public Reservation updateRsvp(Integer id, Reservation rsvp, Employee e) {
        Optional<Reservation> perms = reservationRepository.findById(id);
        // If an employee tries to delete a reservation that's not their own
        // and they are not a manager
        if (perms.get().getEmployeeId() != e.getId() && !e.isManager()) {
            throw new RuntimeException("Insufficient permissions to delete reservation");
        }
		
		return reservationRepository.save(rsvp);
	}

	public void deleteRsvp(Integer id, Employee e) {
        Reservation perms = reservationRepository.findById(id);
        // If an employee tries to delete a reservation that's not their own
        // and they are not a manager
        if (perms.getEmployeeId() != e.getId() && !e.isManager()) {
            throw new RuntimeException("Insufficient permissions to delete reservation");
        }
        
        reservationRepository.deleteById(id);
	}
}