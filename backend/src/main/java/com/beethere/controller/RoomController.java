package com.beethere.controller;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.function.Function;
import java.util.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.beethere.model.Employee;
import com.beethere.model.Room;
import com.beethere.model.TokenRequest;
import com.beethere.service.RoomService;

@CrossOrigin(origins ="http://localhost:3000")
@RestController
@RequestMapping("/rooms")
public class RoomController {

    private final AuthProxy authProxy;
    private final RoomService roomService;

    private static final Logger APPLICATION_LOGGER = LogManager.getLogger("Application");
    private static final Logger SECURITY_LOGGER = LogManager.getLogger("Security");

    public RoomController(AuthProxy authProxy, RoomService roomService) {
        APPLICATION_LOGGER.debug("Constructing RoomController");
        this.authProxy = authProxy;
        this.roomService = roomService;
    }

    /**
     * Takes in a token and an action to perform.
     * <br><br>
     * Returns a ResponseEntity of either Bad Request, Unauthorized, or the result of the action.
     * @param token  Bearer token for authentication
     * @param action Action to perform if authentication is successful
     * @return ResponseEntity with the result of the action or an error message
     */
    private ResponseEntity<?> withAuth(String token, Function<Employee, ResponseEntity<?>> action) {
  
        APPLICATION_LOGGER.debug("Authenticating request with token");
        if (token == null || token.isEmpty()) {
                SECURITY_LOGGER.error("Authentication failed: Token is missing");
            return new ResponseEntity<>("Token Required", HttpStatus.BAD_REQUEST);
            }
            try {
                Employee employee = authProxy.verifyEmployee(new TokenRequest(token));
                employee.toStringFormat(); // optional, maybe for logging
                return action.apply(employee);
            } catch (Exception ex) {
                ex.printStackTrace();
                SECURITY_LOGGER.error("Authentication failed: Invalid token");
            return new ResponseEntity<>("Failed to validate token", HttpStatus.UNAUTHORIZED);
            }
        }

    /**
     * Get all rooms with optional filters
     * <br><br>
     * The Parameters which are passed will determine the type of filtering applied
     * @param token   Bearer token for authentication
     * @param country Optional country filter
     * @param type    Optional room type filter
     * @param start   Optional start time for availability filter (UTC)
     * @param end     Optional end time for availability filter (UTC)
     * @return ResponseEntity with list of rooms or error message
     * 
     */
   @GetMapping({"", "/"})
    public ResponseEntity<?> getAllRooms(
            @RequestHeader(value = "Bearer", required = false) String token,
            @RequestParam(required = false) String country,
            @RequestParam(required = false) String type,
            // ISO DateTime format - Spring will parse automatically
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end
    ) {

        
        APPLICATION_LOGGER.debug("Getting all rooms with optional filters");
        return withAuth(token, employee -> {
            Iterable<Room> rooms = roomService.getRooms(country, type, start, end);
            return new ResponseEntity<>(rooms, HttpStatus.OK);
        });
    }
    
    /**
     * Returns the room with the specified ID
     * @param token Bearer token for authentication
     * @param id    ID of the room to retrieve
     * @return ResponseEntity with the room or error message
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getRoomById(
            @RequestHeader(value = "Bearer", required = false) String token,
            @PathVariable Integer id) {

                APPLICATION_LOGGER.debug("Getting room by ID: " + id);
                return withAuth(token, employee -> {
                    Optional<Room> room = roomService.getRoomById(id);
                    return room.isPresent()
                            ? new ResponseEntity<>(room.get(), HttpStatus.OK)
                            : new ResponseEntity<>("Room not found", HttpStatus.NOT_FOUND);
                });
            }

    /**
     * Create a new room
     * @param token Bearer token for authentication
     * @param room  Room object to create
     * @return ResponseEntity with created room or error message
     */
    @PostMapping("/")
    public ResponseEntity<?> createRoom(
            @RequestHeader(value = "Bearer", required = false) String token,
            @RequestBody Room room) {
                
                APPLICATION_LOGGER.debug("Creating new room");
                return withAuth(token, employee -> {
                    Room newRoom = roomService.createRoom(room, employee);
                    return new ResponseEntity<>(newRoom, HttpStatus.CREATED);
                });
            }
    /**
     * Update an existing room
     * @param token       Bearer token for authentication
     * @param id          ID of the room to update
     * @param updatedRoom Room object with updated details
     * @return ResponseEntity with updated room or error message
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateRoom(
            @RequestHeader(value = "Bearer", required = false) String token,
            @PathVariable Integer id,
            @RequestBody Room updatedRoom) {
                APPLICATION_LOGGER.debug("Updating room with ID: " + id);
                return withAuth(token, employee -> {
                    Optional<Room> existing = roomService.getRoomById(id);
                    if (existing.isEmpty()) {
                        return new ResponseEntity<>("Room not found", HttpStatus.NOT_FOUND);
                    }
                    if (!"Manager".equals(employee.getTitle())) {
                        return new ResponseEntity<>("Access Denied: Only managers can update rooms.", HttpStatus.FORBIDDEN);
                    }

                    Room room = existing.get();
                    room.setCountry(updatedRoom.getCountry());
                    room.setBuilding(updatedRoom.getBuilding());
                    room.setRoomNumber(updatedRoom.getRoomNumber());
                    room.setType(updatedRoom.getType());
                    room.setSeatCount(updatedRoom.getSeatCount());

                    Room saved = roomService.updateRoom(room);
                    return new ResponseEntity<>(saved, HttpStatus.OK);
                });
            }
    /**
     * Delete a room by ID
     * @param token Bearer token for authentication
     * @param id ID of the room to delete
     * @return ResponseEntity with success message or error message
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRoom(
            @RequestHeader(value = "Bearer", required = false) String token,
            @PathVariable Integer id) {
                APPLICATION_LOGGER.debug("Deleting room with ID: " + id);
                return withAuth(token, employee -> {
                    Optional<Room> existing = roomService.getRoomById(id);
                    if (existing.isEmpty()) {
                        return new ResponseEntity<>("Room not found", HttpStatus.NOT_FOUND);
                    }
                    roomService.deleteRoom(id, employee);
                    return new ResponseEntity<>("Room successfully deleted", HttpStatus.NO_CONTENT);
                });
            }
        }
