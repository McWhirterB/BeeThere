package com.beethere.service;


import com.beethere.model.*;
import com.beethere.repository.*;
import org.springframework.stereotype.Service;

@Service
public class ReservationService {

	private ReservationRepository reservationRepository;

	public ReservationService(ReservationRepository reservationRepository) {
		this.reservationRepository = reservationRepository;
	}

	public Iterable<Reservation> findAll() {
        return reservationRepository.findAll();
    }

}
