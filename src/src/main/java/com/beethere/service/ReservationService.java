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

	public Reservation createRsvp(Reservation rsvp) {
		return reservationRepository.save(rsvp);
	}
}
