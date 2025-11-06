 package com.beethere.model;

 import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.Instant;

import org.junit.jupiter.api.Test;
// import org.springframework.test.context.event.annotation;

public class ReservationTest {
    Date testDateNow = new Date();
    Date testDateStart1 = Date.from(Instant.parse("3025-10-01T12:10:00.123456789Z"));
    Date testDateStart2 = Date.from(Instant.parse("3025-10-01T12:10:30.123456789Z"));
    Date testDateEnd1 = Date.from(Instant.parse("3025-10-01T12:12:00.123456789Z"));
    Date testDateEnd2 = Date.from(Instant.parse("3025-10-01T12:12:30.123456789Z"));

   
    public Reservation testReservation = new Reservation(
        01, 
        02,  
        "Sandra", 
        testDateStart1,
        testDateEnd1
        );

    @Test
    void testGetID() {
            int actual = testReservation.getReservationId();
            int expected = 01;
        assertEquals(actual, expected);

    }

    @Test
    void testGetEmployeeId() {
        int actual = testReservation.getEmployeeId();
        int expected = 02;
        assertEquals(actual, expected);
    }

    @Test
    void testGetEmployeeName() {
        String actual = testReservation.getEmployeeName();
        String expected = "Sandra";
        assertEquals(actual, expected);
    }

     @Test
    void testGetStartTime() {
        Date actual = testReservation.getStartTime();
        Date expected = testDateStart1;
        assertEquals(actual, expected);
    }
      @Test
    void testGetEndTime() {
        Date actual = testReservation.getEndTime();
        Date expected = testDateEnd1;
        assertEquals(actual, expected);
    }
    @Test
    void testSetEmployeeName() {
        testReservation.setEmployeeName("PorygonZ");
        String actual = testReservation.getEmployeeName();
        String expected = "PorygonZ";
        assertEquals(actual, expected);
    }

    @Test
    void testSetStartTime() {
        testReservation.setStartTime(testDateStart2);
        Date actual = testReservation.getStartTime();
        Date expected = testDateStart2;
        assertEquals(actual, expected);
    }
     @Test
    void testSetEndTime() {
        testReservation.setEndTime(testDateEnd2);
        Date actual = testReservation.getEndTime();
        Date expected = testDateEnd2;
        assertEquals(actual, expected);
    }


}
