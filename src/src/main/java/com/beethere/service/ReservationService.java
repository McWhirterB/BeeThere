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

	public ReservationService(ReservationRepository reservationRepository, RoomRepository roomRepsitory) {
		this.reservationRepository = reservationRepository;
        this.roomRepository = roomRepsitory;
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

	public Reservation updateRsvp(Reservation rsvp, Employee e) {
		Optional<Reservation> perms = reservationRepository.findById(rsvp.getReservationId());
		if (perms.get().getEmployeeId() != e.getId() && !e.isManager()) {
			throw new RuntimeException("Insufficient permissions to update reservation");
		}
		return reservationRepository.save(rsvp);
	}

	public void deleteRsvp(Integer id, Employee e) {
		Optional<Reservation> perms = reservationRepository.findById(id);
		if (perms.get().getEmployeeId() != e.getId() && !e.isManager()) {
			throw new RuntimeException("Insufficient permissions to delete reservation");
		}
        reservationRepository.deleteById(id);
	}
}