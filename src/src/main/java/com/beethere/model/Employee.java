package com.beethere.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Id;

@Entity
public class Employee {
	@Id
	private Integer ID;
	private String fName;	
	private String lName;
	private String loc;
	private String dept;
	private String title;


    private static final Logger LOG = LogManager.getLogger();

    public Employee(int id, String location, String firstName, String lastName, String department, String title) {
        setId(id);
        setLocation(location);
        setFirstName(firstName);
        setLastName(lastName);
        setDepartment(department);
        setTitle(title);
    }

	//TODO: Implement data validation for setters
    public int getId() {
        LOG.debug("Getting the employee id");
        return ID;
    }
    
    
    public void setId(int id) {
        LOG.debug("Setting the employee id");

        if (id < 0){

            LOG.error("Employee ID cannot be less than 0");
            throw new IllegalArgumentException("Employee ID cannot be less than 0");
        }

        LOG.debug("Employee ID set to " + id);
        this.ID = id;
    }


   public String getLocation() {
        LOG.debug("Getting the employee location");
        return loc;
    }


    public void setLocation(String location) {
        LOG.debug("Setting the employee location");
        final int min = 2;
        final int max = 20;

        if (location == null || location.isEmpty()){
            LOG.error("Location can not be null or empty");
            throw new IllegalArgumentException("Location cannot be null or empty");
        }
        if (location.length() < min || location.length() > max) {
            LOG.error("Location must be between " + min + " and " + max + "characters");
            throw new IllegalArgumentException("Location must be between " + min + " and " + max + " characters");
        }
        
        
        LOG.debug("setting location to " + location);
        this.loc = location;
    }

    public String getFirstName() {
        LOG.debug("Getting the employee first name");
        return fName;
    }


    public void setFirstName(String firstName) {
        LOG.debug("Setting the employee first name");

        final int min = 1;
        final int max = 20;

        if (firstName == null || firstName.isEmpty()){
            LOG.error("First name cannot be null or empty");
            throw new IllegalArgumentException("First name cannot be null or empty");
        }
        if (firstName.length() < min || firstName.length() > max) {
            LOG.error("First name must be between " + min + " and " + max + " characters");
            throw new IllegalArgumentException("First name must be between " + min + " and " + max + " characters");
        }
        
        LOG.debug("Setting first name to " + firstName);
        this.fName = firstName;
    }


    public String getLastName() {
        LOG.debug("Getting the employee last name");
        return lName;
    }


    public void setLastName(String lastName) {
        LOG.debug("Setting the employee last name");

        final int min = 1;
        final int max = 20;

        if (lastName == null || lastName.isEmpty()){
            LOG.error("Last name cannot be null or empty");
            throw new IllegalArgumentException("Last name cannot be null or empty");
        }
        if (lastName.length() < min || lastName.length() > max) {
            LOG.error("Last name must be between " + min + " and " + max + " characters");
            throw new IllegalArgumentException("Last name must be between " + min + " and " + max + " characters");
        }
        

        LOG.debug("Setting last name to " + lastName);
        this.lName = lastName;
    }


    public String getDepartment() {
        LOG.debug("Getting the employee department");
        return dept;
    }


    public void setDepartment(String department) {
        LOG.debug("Setting the employee department");

        final int min = 1;
        final int max = 40;

        if (department == null || department.isEmpty()){
            LOG.error("Department cannot be null or empty");
            throw new IllegalArgumentException("Department cannot be null or empty");
        }   
        if (department.length() < min || department.length() > max) {
            LOG.error("Department must be between " + min + " and " + max + " characters");
            throw new IllegalArgumentException("Department must be between " + min + " and " + max + " characters");
        }
        
        LOG.debug("Setting department to " + department);
        this.dept = department;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

}

