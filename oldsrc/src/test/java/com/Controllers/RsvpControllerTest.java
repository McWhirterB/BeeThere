package com.Controllers;

import org.junit.jupiter.api.Test;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import com.beethere.Controllers.TokenRequest;
import com.beethere.model.Room;

import org.springframework.http.MediaType;

@FeignClient(name = "auth", url = "http://localhost:8080/") 
interface AuthProxy {
	@PostMapping(value = "/rooms", consumes = MediaType.APPLICATION_JSON_VALUE)
	Room[] getRooms(@RequestBody TokenRequest token);

	@PostMapping(value = "/rooms/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	Room[] getRoomById(@PathVariable int id);
}

public class RsvpControllerTest {

    @Test
    void testSetStartTime() {
        
    }
}
