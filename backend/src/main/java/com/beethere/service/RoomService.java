package com.beethere.service;

import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beethere.model.Employee;
import com.beethere.model.Room;
import com.beethere.repository.RoomRepository;

@Service
public class RoomService {

    @Autowired
    private RoomRepository roomRepository;

    private static final Logger APPLICATION_LOGGER = LogManager.getLogger("Application");

    // Get all rooms
    public Iterable<Room> getRooms() {
        APPLICATION_LOGGER.debug("Fetching all rooms");
        return roomRepository.findAll();
    }

    public Iterable<Room> getRooms(String location, String type) {
        APPLICATION_LOGGER.debug("Fetching rooms with filters - Location: " + location + ", Type: " + type);
        if (location != null && type != null) {
            return roomRepository.findByLocationAndType(location, type);
        } else if (location != null) {
            return roomRepository.findByLocation(location);
        } else if (type != null) {
            return roomRepository.findByType(type);
        } else {
            return roomRepository.findAll();
        }
    }

    // Get room by ID
    public Optional<Room> getRoomById(Integer id) {
        APPLICATION_LOGGER.debug("Fetching room with ID: " + id);
        return roomRepository.findById(id);
    }

    // Create a new room
    public Room createRoom(Room room, Employee employee) {
        APPLICATION_LOGGER.debug("Creating new room");
        if ("Manager".equals(employee.getTitle())){
            return roomRepository.save(room);
        } else {
            throw new RuntimeException("Access Denied: Only managers can create rooms.");
        }
    }

    // Update an existing room
    public Room updateRoom(Room room) {
        APPLICATION_LOGGER.debug("Updating room with ID: " + room.getId());
        if (roomRepository.existsById(room.getId())) {

            return roomRepository.save(room);

        } else {
            throw new RuntimeException("Room not found with ID: " + room.getId());
        }
    }

    // Delete a room by ID
    public void deleteRoom(Integer id, Employee employee) {
        APPLICATION_LOGGER.debug("Deleting room with ID: " + id);
        if (roomRepository.existsById(id)) {
            if ("Manager".equals(employee.getTitle())){
                roomRepository.deleteById(id);
            }
            else {
                throw new RuntimeException("Access Denied: Only managers can delete rooms.");
            }
            
        } else {
            throw new RuntimeException("Room not found with ID: " + id);
        }
    }
}
