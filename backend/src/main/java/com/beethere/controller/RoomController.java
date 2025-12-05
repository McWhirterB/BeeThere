package com.beethere.controller;

import java.util.Optional;
import java.util.function.Function;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

   @GetMapping({"", "/"})
    public ResponseEntity<?> getAllRooms(
            @RequestHeader(value = "Bearer", required = false) String token,
            @RequestParam(required = false) String location,
            @RequestParam(required = false) String type
    ) {
        APPLICATION_LOGGER.debug("Getting all rooms with optional filters");
        return withAuth(token, employee -> {
            Iterable<Room> rooms = roomService.getRooms(location, type);
            return new ResponseEntity<>(rooms, HttpStatus.OK);
        });
    }


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
                    room.setLocation(updatedRoom.getLocation());
                    room.setBuilding(updatedRoom.getBuilding());
                    room.setRoomNumber(updatedRoom.getRoomNumber());
                    room.setType(updatedRoom.getType());
                    room.setSeatCount(updatedRoom.getSeatCount());

                    Room saved = roomService.updateRoom(room);
                    return new ResponseEntity<>(saved, HttpStatus.OK);
                });
            }

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
