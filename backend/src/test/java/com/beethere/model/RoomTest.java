 package com.beethere.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
// import org.springframework.test.context.event.annotation;

public class RoomTest {

    int testId = 01;
    String testLocation = "Tokyo";
    String testBuilding = "2nd Street";
    String testRoomNumber = "2-222";
    String testType = "video";
    int testSeatCount = 20;
   
    public Room testRoom = new Room(
        testId, 
        testLocation,  
        testBuilding, 
        testRoomNumber, 
        testType, 
        testSeatCount
        );

    // @Test
    // void testAssertEquals() {
    //     String actual = "test";
    //     String expected = "whomp";
    //     assertEquals(actual, expected);
            
    // }


    @Test
    void testGetCountry() {
        String actual = testRoom.getCountry();
        String expected = testLocation;
        assertEquals(actual, expected);
            
    }

    @Test
    void testGetID() {
            int actual = testRoom.getId();
            int expected = testId;
        assertEquals(actual, expected);

    }

    @Test
    void testGetRoomNumber() {
        String actual = testRoom.getRoomNumber();
        String expected = testRoomNumber;
        assertEquals(actual, expected);
    }

    @Test
    void testGetSeatCount() {
        int actual = testRoom.getSeatCount();
        int expected = testSeatCount;
        assertEquals(actual, expected);
    }

    @Test
    void testSetCountry() {
        testRoom.setBuilding("Mexico City");
        String actual = testRoom.getCountry();
        String expected = "Mexico City";
        assertEquals(actual, expected);
    }

    @Test
    void testSetCity() {
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
    @Test
    public void testNullLocation() {
        Room instance = new Room();
        
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> instance.setCountry(null)
        );
        
        assertEquals("location must not be empty", exception.getMessage());
    }
    @Test
    public void testNullBuilding() {
        Room instance = new Room();
        
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> instance.setBuilding(null)
        );
        
        assertEquals("building must not be null", exception.getMessage());
    }
    @Test
    public void testNullType() {
        Room instance = new Room();
        
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> instance.setType(null)
        );
        
        assertEquals("Type must not be null", exception.getMessage());
    }

    
}
