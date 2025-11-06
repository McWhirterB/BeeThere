package com.beethere.service;

import java.util.*;
import java.util.stream.Collectors;

import com.beethere.model.*;
import com.beethere.repository.*;

import org.springframework.stereotype.Service;

@Service
public class ReservationService {
	private ReservationRepository reservationRepository;
	private RoomRepository roomRepository;

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
		Set<Room> attachedRooms = rsvp.getRooms().stream()
            .map(room -> roomRepository.findById(room.getId()).orElseThrow())
            .collect(Collectors.toSet());

        rsvp.setRooms(attachedRooms);

		return reservationRepository.save(rsvp);
	}

	public Reservation updateRsvp(Reservation rsvp) {
		if (reservationRepository.existsById(rsvp.reservationId)) {
			return reservationRepository.save(rsvp);
		}

		return null;
	}

	public void deleteRsvp(Integer id) {
        if (reservationRepository.existsById(id)) {
            reservationRepository.deleteById(id);
        } else {
            throw new RuntimeException("Room not found with ID: " + id);
        }
	}
}