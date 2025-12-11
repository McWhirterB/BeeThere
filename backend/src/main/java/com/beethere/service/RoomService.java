package com.beethere.service;

import java.util.Date;
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

    public Iterable<Room> getRooms(String country, String type, Date start, Date end) {
        APPLICATION_LOGGER.debug("Fetching rooms with filters - Location: " + country + ", Type: " + type + ", Start: " + start + ", End: " + end);
        
        // Log the actual UTC time for debugging
        if (start != null) {
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            sdf.setTimeZone(java.util.TimeZone.getTimeZone("UTC"));
            System.out.println("Start time in UTC: " + sdf.format(start));
        }
        if (end != null) {
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            sdf.setTimeZone(java.util.TimeZone.getTimeZone("UTC"));
            System.out.println("End time in UTC: " + sdf.format(end));
        }
        
        if (country != null && start != null && end != null){
            System.out.println("first call");
            return roomRepository.findByCountryAndAvailability(country, start, end);
        }
        if (start != null && end != null){
            System.out.println("second call");
            return roomRepository.findByAvailability(start, end);
        }
        if (country != null && type != null) {
            return roomRepository.findByCountryAndType(country, type);
        } 
        if (country != null) {
            return roomRepository.findByCountry(country);
        } 
        if (type != null) {
            return roomRepository.findByType(type);
        } 
        return roomRepository.findAll();
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
