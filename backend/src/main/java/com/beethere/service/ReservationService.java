package com.beethere.service;

import java.util.*;
import java.util.stream.Collectors;

import com.beethere.model.*;
import com.beethere.repository.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class ReservationService {
	private ReservationRepository reservationRepository;
	private RoomRepository roomRepository;

	private static final Logger APPLICATION_LOGGER = LogManager.getLogger("Application");

	public ReservationService(ReservationRepository reservationRepository, RoomRepository roomRepsitory) {
		APPLICATION_LOGGER.debug("Constructing ReservationService");
		this.reservationRepository = reservationRepository;
        this.roomRepository = roomRepsitory;
	}

	public Iterable<Reservation> getRsvps() {
		APPLICATION_LOGGER.debug("Fetching all reservations");
		return reservationRepository.findAll();
	}

	public Optional<Reservation> getRsvp(Integer id) {
		APPLICATION_LOGGER.debug("Fetching reservation with ID: " + id);
		return reservationRepository.findById(id);
	}

	public Reservation createRsvp(Reservation rsvp) {
		APPLICATION_LOGGER.debug("Creating new reservation");

		Set<Room> attachedRooms = rsvp.getRooms().stream()
            .map(room -> roomRepository.findById(room.getId()).orElseThrow())
            .collect(Collectors.toSet());

        rsvp.setRooms(attachedRooms);
		return reservationRepository.save(rsvp);
	}

	public Reservation updateRsvp(Reservation rsvp, Employee e) {
		APPLICATION_LOGGER.debug("Updating reservation with ID: " + rsvp.getReservationId());
		Optional<Reservation> perms = reservationRepository.findById(rsvp.getReservationId());
		if (perms.get().getEmployeeId() != e.getId() && !e.isManager()) {
			throw new RuntimeException("Insufficient permissions to update reservation");
		}
		return reservationRepository.save(rsvp);
	}

	public void deleteRsvp(Integer id, Employee e) {
		APPLICATION_LOGGER.debug("Deleting reservation with ID: " + id);
		Optional<Reservation> perms = reservationRepository.findById(id);
		if (perms.get().getEmployeeId() != e.getId() && !e.isManager()) {
			throw new RuntimeException("Insufficient permissions to delete reservation");
		}
        reservationRepository.deleteById(id);
	}

	public void deleteRsvp(Integer id) {
		APPLICATION_LOGGER.debug("Deleting reservation with ID: " + id);
        if (reservationRepository.existsById(id)) {
            reservationRepository.deleteById(id);
        } else {
            throw new RuntimeException("Room not found with ID: " + id);
        }
	}
}