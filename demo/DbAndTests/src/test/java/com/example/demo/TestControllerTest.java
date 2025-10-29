package com.example.demo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TestControllerTest {
    private MockMvc mockMvc;
		private PersonRepository personRepository;
		private RoomRepository roomRepository;
		private ReservationRepository reservationRepository;
		private AuthProxy authProxy;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(new TestController(personRepository, roomRepository, reservationRepository, authProxy)).build();
    }

    @Test
    public void testEndpoint() throws Exception {
        mockMvc.perform(get("/test"))
               .andExpect(status().isNotFound());
    }

		@Test
		public void tesEndpoint2() throws Exception {
			mockMvc.perform(get("/"))
						.andExpect(status().isNotFound());
		}
}

