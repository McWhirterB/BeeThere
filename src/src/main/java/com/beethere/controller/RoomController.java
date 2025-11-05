package com.beethere.controller;

import com.beethere.service.RoomService;
import com.beethere.model.Room;
import com.beethere.model.Employee;
import com.beethere.model.TokenRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.function.Supplier;

@RestController
@RequestMapping("/room")
public class RoomController {

    private final AuthProxy authProxy;
    private final RoomService roomService;

    public RoomController(AuthProxy authProxy, RoomService roomService) {
        this.authProxy = authProxy;
        this.roomService = roomService;
    }


    private ResponseEntity<?> withAuth(String token, Supplier<ResponseEntity<?>> action) {
        if (token == null || token.isEmpty()) {
            return new ResponseEntity<>("Token Required", HttpStatus.BAD_REQUEST);
        }

        try {
            Employee employee = authProxy.verifyEmployee(new TokenRequest(token));
            return action.get();
        } catch (Exception ex) {
            return new ResponseEntity<>("Failed to validate token", HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/")
    public ResponseEntity<?> getAllRooms(@RequestHeader(value = "Bearer", required = false) String token) {
        return withAuth(token, () -> {
            Iterable<Room> rooms = roomService.getRooms();
            return new ResponseEntity<>(rooms, HttpStatus.OK);
        });
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getRoomById(
            @RequestHeader(value = "Bearer", required = false) String token,
            @PathVariable Integer id) {

        return withAuth(token, () -> {
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

        return withAuth(token, () -> {
            Room newRoom = roomService.createRoom(room);
            return new ResponseEntity<>(newRoom, HttpStatus.CREATED);
        });
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateRoom(
            @RequestHeader(value = "Bearer", required = false) String token,
            @PathVariable Integer id,
            @RequestBody Room updatedRoom) {

        return withAuth(token, () -> {
            Optional<Room> existing = roomService.getRoomById(id);
            if (existing.isEmpty()) {
                return new ResponseEntity<>("Room not found", HttpStatus.NOT_FOUND);
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

        return withAuth(token, () -> {
            Optional<Room> existing = roomService.getRoomById(id);
            if (existing.isEmpty()) {
                return new ResponseEntity<>("Room not found", HttpStatus.NOT_FOUND);
            }
            roomService.deleteRoom(id);
            return new ResponseEntity<>("Room deleted successfully", HttpStatus.NO_CONTENT);
        });
    }
}
