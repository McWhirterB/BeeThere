package com.beethere.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class Employee {

private static final Logger LOG = LogManager.getLogger();

    private int ID;
    private String location;
    private String firstName;
    private String lastName;
    private String department;
    private String title;

    public Employee(int ID, String location, String firstName, String lastName, String department, String title) {
        setID(ID);
        setLocation(location);
        setFirstName(firstName);
        setLastName(lastName);
        setDepartment(department);
        setTitle(title);
    }
    
    //TODO: Implement data validation for setters
    public int getID() {
        LOG.debug("Getting the employee id");
        return ID;
    }
    
    
    public void setID(int ID) {
        LOG.debug("Setting the employee id");

        if (ID < 0){

            LOG.error("Employee ID cannot be less than 0");
            throw new IllegalArgumentException("Employee ID cannot be less than 0");
        }

        LOG.debug("Employee ID set to " + ID);
        this.ID = ID;
    }


    public String getLocation() {
        return location;
    }


    public void setLocation(String location) {
        final int min = 2;
        final int max = 20;

        if (location.length() < min || location.length() > max) {
            throw new IllegalArgumentException("Location must be between " + min + " and " + max + " characters");
        }
        if (location == null || location.isEmpty()){
            throw new IllegalArgumentException("Location cannot be null or empty");
        }

        this.location = location;
    }


    public String getFirstName() {
        return firstName;
    }


    public void setFirstName(String firstName) {
        final int min = 1;
        final int max = 20;

        if (firstName.length() < min || firstName.length() > max) {
            throw new IllegalArgumentException("First name must be between " + min + " and " + max + " characters");
        }
        if (firstName == null || firstName.isEmpty()){
            throw new IllegalArgumentException("First name cannot be null or empty");
        }
        this.firstName = firstName;
    }


    public String getLastName() {
        return lastName;
    }


    public void setLastName(String lastName) {
        final int min = 1;
        final int max = 20;

        if (lastName.length() < min || lastName.length() > max) {
            throw new IllegalArgumentException("Last name must be between " + min + " and " + max + " characters");
        }
        if (lastName == null || lastName.isEmpty()){
            throw new IllegalArgumentException("Last name cannot be null or empty");
        }
        this.lastName = lastName;
    }


    public String getDepartment() {
        return department;
    }


    public void setDepartment(String department) {
        final int min = 1;
        final int max = 40;

        if (department.length() < min || department.length() > max) {
            throw new IllegalArgumentException("Department must be between " + min + " and " + max + " characters");
        }
        if (department == null || department.isEmpty()){
            throw new IllegalArgumentException("Department cannot be null or empty");
        }
        this.department = department;
    }


    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }

}
