package com.beethere.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class Employee {

private static final Logger LOG = LogManager.getLogger();

    enum Roles{
        EMPLOYEE,
        MANAGER
    }

    private int ID;
    private String location;
    private String firstName;
    private String lastName;
    private String department;
    private Roles role;

    public Employee(int ID, String location, String firstName, String lastName, String department, Roles role) {
        setID(ID);
        setLocation(location);
        setFirstName(firstName);
        setLastName(lastName);
        setDepartment(department);
        setRole(role);
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
        LOG.debug("Getting the employee location");
        return location;
    }


    public void setLocation(String location) {
        LOG.debug("Setting the employee location");
        final int min = 2;
        final int max = 20;

        if (location.length() < min || location.length() > max) {
            LOG.error("Location must be between " + min + " and " + max + "characters");
            throw new IllegalArgumentException("Location must be between " + min + " and " + max + " characters");
        }
        if (location == null || location.isEmpty()){
            LOG.error("Location can not be null or empty");
            throw new IllegalArgumentException("Location cannot be null or empty");
        }
        
        LOG.debug("setting location to " + location);
        this.location = location;
    }

    public String getFirstName() {
        LOG.debug("Getting the employee first name");
        return firstName;
    }


    public void setFirstName(String firstName) {
        LOG.debug("Setting the employee first name");

        final int min = 1;
        final int max = 20;

        if (firstName.length() < min || firstName.length() > max) {
            LOG.error("First name must be between " + min + " and " + max + " characters");
            throw new IllegalArgumentException("First name must be between " + min + " and " + max + " characters");
        }
        if (firstName == null || firstName.isEmpty()){
            LOG.error("First name cannot be null or empty");
            throw new IllegalArgumentException("First name cannot be null or empty");
        }
        LOG.debug("Setting first name to " + firstName);
        this.firstName = firstName;
    }


    public String getLastName() {
        LOG.debug("Getting the employee last name");
        return lastName;
    }


    public void setLastName(String lastName) {
        LOG.debug("Setting the employee last name");

        final int min = 1;
        final int max = 20;

        if (lastName.length() < min || lastName.length() > max) {
            LOG.error("Last name must be between " + min + " and " + max + " characters");
            throw new IllegalArgumentException("Last name must be between " + min + " and " + max + " characters");
        }
        if (lastName == null || lastName.isEmpty()){
            LOG.error("Last name cannot be null or empty");
            throw new IllegalArgumentException("Last name cannot be null or empty");
        }

        LOG.debug("Setting last name to " + lastName);
        this.lastName = lastName;
    }


    public String getDepartment() {
        LOG.debug("Getting the employee department");
        return department;
    }


    public void setDepartment(String department) {
        LOG.debug("Setting the employee department");

        final int min = 1;
        final int max = 40;

        if (department.length() < min || department.length() > max) {
            LOG.error("Department must be between " + min + " and " + max + " characters");
            throw new IllegalArgumentException("Department must be between " + min + " and " + max + " characters");
        }
        if (department == null || department.isEmpty()){
            LOG.error("Department cannot be null or empty");
            throw new IllegalArgumentException("Department cannot be null or empty");
        }
        LOG.debug("Setting department to " + department);
        this.department = department;
    }
    public Roles getRole() {
        return role;
    }
    public void setRole(Roles role) {
        this.role = role;
    }

}
