package com.beethere.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Employee {

    @Id
    private Integer id;
    private String fName;
    private String lName;
    private String loc;
    private String dept;
    private String title;

    private static final Logger LOG = LogManager.getLogger();

    public Employee(int id, String loc, String fName, String lName, String dept, String title) {
        setId(id);
        setLoc(loc);
        setFName(fName);
        setLName(lName);
        setDept(dept);
        setTitle(title);
    }

    public void toStringFormat(){
        System.out.println("Employee ID: " + getId());
        System.out.println("Location: " + getLoc());
        System.out.println("First Name: " + getFName());
        System.out.println("Last Name: " + getLName());
        System.out.println("Department: " + getDept());
        System.out.println("Title: " + getTitle());
    }

    // Getters and Setters
    public int getId() {
        LOG.debug("Getting the employee id");
        return id;
    }

    public void setId(int id) {
        LOG.debug("Setting the employee id");
        if (id < 0) {
            LOG.error("Employee ID cannot be less than 0");
            throw new IllegalArgumentException("Employee ID cannot be less than 0");
        }
        LOG.debug("Employee ID set to " + id);
        this.id = id;
    }

    public String getLoc() {
        LOG.debug("Getting the employee location");
        return loc;
    }

    public void setLoc(String loc) {
        LOG.debug("Setting the employee location");
        final int min = 2;
        final int max = 20;

        if (loc == null || loc.isEmpty()) {
            LOG.error("Location cannot be null or empty");
            throw new IllegalArgumentException("Location cannot be null or empty");
        }
        if (loc.length() < min || loc.length() > max) {
            LOG.error("Location must be between " + min + " and " + max + " characters");
            throw new IllegalArgumentException("Location must be between " + min + " and " + max + " characters");
        }

        LOG.debug("Setting location to " + loc);
        this.loc = loc;
    }

    public String getFName() {
        LOG.debug("Getting the employee first name");
        return fName;
    }

    public void setFName(String fName) {
        LOG.debug("Setting the employee first name");
        final int min = 1;
        final int max = 20;

        if (fName == null || fName.isEmpty()) {
            LOG.error("First name cannot be null or empty");
            throw new IllegalArgumentException("First name cannot be null or empty");
        }
        if (fName.length() < min || fName.length() > max) {
            LOG.error("First name must be between " + min + " and " + max + " characters");
            throw new IllegalArgumentException("First name must be between " + min + " and " + max + " characters");
        }

        LOG.debug("Setting first name to " + fName);
        this.fName = fName;
    }

    public String getLName() {
        LOG.debug("Getting the employee last name");
        return lName;
    }

    public void setLName(String lName) {
        LOG.debug("Setting the employee last name");
        final int min = 1;
        final int max = 20;

        if (lName == null || lName.isEmpty()) {
            LOG.error("Last name cannot be null or empty");
            throw new IllegalArgumentException("Last name cannot be null or empty");
        }
        if (lName.length() < min || lName.length() > max) {
            LOG.error("Last name must be between " + min + " and " + max + " characters");
            throw new IllegalArgumentException("Last name must be between " + min + " and " + max + " characters");
        }

        LOG.debug("Setting last name to " + lName);
        this.lName = lName;
    }

    public String getDept() {
        LOG.debug("Getting the employee department");
        return dept;
    }

    public void setDept(String dept) {
        LOG.debug("Setting the employee department");
        final int min = 1;
        final int max = 40;

        if (dept == null || dept.isEmpty()) {
            LOG.error("Department cannot be null or empty");
            throw new IllegalArgumentException("Department cannot be null or empty");
        }
        if (dept.length() < min || dept.length() > max) {
            LOG.error("Department must be between " + min + " and " + max + " characters");
            throw new IllegalArgumentException("Department must be between " + min + " and " + max + " characters");
        }

        LOG.debug("Setting department to " + dept);
        this.dept = dept;
    }

    public String getTitle() {
        LOG.debug("Getting the employee title");
        return title;
    }

    public void setTitle(String title) {
        LOG.debug("Setting the employee title");
        this.title = title;
    }
}
