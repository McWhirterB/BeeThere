 package com.beethere.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
// import org.springframework.test.context.event.annotation;

public class RoomTest {

   
    public Room testRoom = new Room(
        01, 
        "Japan",  
        "2nd Street", 
        "1-200", 
        "video", 
        20
        );

    // @Test
    // void testAssertEquals() {
    //     String actual = "test";
    //     String expected = "whomp";
    //     assertEquals(actual, expected);
            
    // }


    @Test
    void testGetLocation() {
        String actual = testRoom.getLocation();
        String expected = "Japan";
        assertEquals(actual, expected);
            
    }

    @Test
    void testGetID() {
            int actual = testRoom.getId();
            int expected = 01;
        assertEquals(actual, expected);

    }

    @Test
    void testGetRoomNumber() {
        String actual = testRoom.getRoomNumber();
        String expected = "1-200";
        assertEquals(actual, expected);
    }

    @Test
    void testGetSeatCount() {
        int actual = testRoom.getSeatCount();
        int expected = 20;
        assertEquals(actual, expected);
    }

    @Test
    void testSetLocation() {
        testRoom.setLocation("Mexico City");
        String actual = testRoom.getLocation();
        String expected = "Mexico City";
        assertEquals(actual, expected);
    }

    @Test
    void testSetBuilding() {
        testRoom.setBuilding("1st Street");
        String actual = testRoom.getBuilding();
        String expected = "1st Street";
        assertEquals(actual, expected);
    }

    @Test
    void testSetRoomNumber() {
        testRoom.setRoomNumber("3-900");
        String actual = testRoom.getRoomNumber();
        String expected = "3-900";
        assertEquals(actual, expected);
    }

    @Test
    void testSetSeatCount() {
        testRoom.setSeatCount(42);
        int actual = testRoom.getSeatCount();
        int expected = 42;
        assertEquals(actual, expected);
    }

    @Test
    void testSetType() {
         testRoom.setType("breakroom");
        String actual = testRoom.getType();
        String expected = "breakroom";
        assertEquals(actual, expected);
    }

     @Test()
    private void testVoidCity() {
        assertNotNull(testRoom.getLocation());
       
    }
}
