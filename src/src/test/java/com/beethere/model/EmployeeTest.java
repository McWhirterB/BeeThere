package com.beethere.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.FileInputStream;

import org.junit.jupiter.api.Test;
// import org.springframework.test.context.event.annotation;

public class EmployeeTest {

   
    public Employee testEmployee = new Employee(
        01, 
        "Pallet Town",  
        "Ash", 
        "Ketchum",
        "Acquistions", 
        "Trainer"
        );

    // @Test
    // void testAssertEquals() {
    //     String actual = "test";
    //     String expected = "whomp";
    //     assertEquals(actual, expected);
            
    // }
    // @BeforeAll
    // void initializeConfig() throws FileNotFoundException{
    //     System.getProperty("user.dir");
    //     Config.initializeFromFile(new FileInputStream("config.yaml"));
    // }


    @Test
    void testGetId() {
        int actual = testEmployee.getId();
        int expected = 01;
        assertEquals(actual, expected);
            
    }

    @Test
    void testGetLoc() {
            String actual = testEmployee.getLoc();
            String expected = "Pallet Town";
        assertEquals(actual, expected);

    }

    @Test
    void testGetFName() {
        String actual = testEmployee.getFName();
        String expected = "Ash";
        assertEquals(actual, expected);
    }

    @Test
    void testGetLName() {
        String actual = testEmployee.getLName();
        String expected = "Ketchum";
        assertEquals(actual, expected);
    }

    @Test
    void testGetDept() {
        String actual = testEmployee.getDept();
        String expected = "Acquistions";
        assertEquals(actual, expected);
    }

    @Test
    void testGetTitle() {
        String actual = testEmployee.getTitle();
        String expected = "Trainer";
        assertEquals(actual, expected);
    }

    @Test
    void testSetFName() {
        testEmployee.setFName("Jesse");
        String actual = testEmployee.getFName();
        String expected = "Jesse";
        assertEquals(actual, expected);
    }

    @Test
    void testSetLName() {
        testEmployee.setLName("James");
        String actual = testEmployee.getLName();
        String expected = "James";
        assertEquals(actual, expected);
    }

    @Test
    void testSetLoc() {
        testEmployee.setLoc("TeamRocketHQ");
        String actual = testEmployee.getLoc();
        String expected = "TeamRocketHQ";
        assertEquals(actual, expected);
    }

    @Test
    void testSetDept() {
        testEmployee.setDept("TeamRocket");
        String actual = testEmployee.getDept();
        String expected = "TeamRocket";
        assertEquals(actual, expected);
    }

    @Test
    void testSetTitle() {
        testEmployee.setTitle("Grunt");
        String actual = testEmployee.getTitle();
        String expected = "Grunt";
        assertEquals(actual, expected);
    }
}