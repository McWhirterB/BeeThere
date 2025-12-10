package com.beethere.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.beethere.config.ConfigHelper;
import com.beethere.utils.sanitizer.Sanitize;

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

    private static final Logger APPLICATION_LOGGER = LogManager.getLogger("Application");
    private static final int minStringLength = 1;
    private static final int longStringMaxLength = ConfigHelper.getConfig().getLongStringMaxLength();

    public Employee(int id, String loc, String fName, String lName, String dept, String title) {
        APPLICATION_LOGGER.debug("Constructing Employee");
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

    public boolean isManager() {
        if (title.toLowerCase().contains("manager")) {
            return true;
        }
        return false;
    }

    // Getters and Setters
    public int getId() {
        APPLICATION_LOGGER.debug("Getting the employee id");
        return id;
    }

    public void setId(int id) {
        APPLICATION_LOGGER.debug("Setting the employee id");
        if (id < 0) {
            APPLICATION_LOGGER.error("Employee ID cannot be less than 0");
            throw new IllegalArgumentException("Employee ID cannot be less than 0");
        }
        APPLICATION_LOGGER.debug("Employee ID set to " + id);
        this.id = id;
    }

    public String getLoc() {
        APPLICATION_LOGGER.debug("Getting the employee location");
        return loc;
    }

    public void setLoc(String loc) {
        APPLICATION_LOGGER.debug("Setting the employee location");

        if (loc == null || loc.isEmpty()) {
            APPLICATION_LOGGER.error("Location cannot be null or empty");
            throw new IllegalArgumentException("Location cannot be null or empty");
        }
        if (loc.length() < minStringLength || loc.length() > longStringMaxLength) {
            APPLICATION_LOGGER.error("Location must be between " + minStringLength + " and " + longStringMaxLength + " characters");
            throw new IllegalArgumentException("Location must be between " + minStringLength + " and " + longStringMaxLength + " characters");
        }

            String cleanLoc = Sanitize.sanitizeHtml(loc);
        APPLICATION_LOGGER.debug("Setting location to " + cleanLoc);
        this.loc = cleanLoc;
    }

    public String getFName() {
        APPLICATION_LOGGER.debug("Getting the employee first name");
        return fName;
    }

    public void setFName(String fName) {
        APPLICATION_LOGGER.debug("Setting the employee first name");
        
        

        if (fName == null || fName.isEmpty()) {
            APPLICATION_LOGGER.error("First name cannot be null or empty");
            throw new IllegalArgumentException("First name cannot be null or empty");
        }
        if (fName.length() < minStringLength || fName.length() > longStringMaxLength) {
            APPLICATION_LOGGER.error("First name must be between " + minStringLength + " and " + longStringMaxLength + " characters");
            throw new IllegalArgumentException("First name must be between " + minStringLength + " and " + longStringMaxLength + " characters");
        }

        String cleanfName = Sanitize.sanitizeHtml(fName);
        APPLICATION_LOGGER.debug("Setting first name to " + cleanfName);
        this.fName = cleanfName;
    }

    public String getLName() {
        APPLICATION_LOGGER.debug("Getting the employee last name");
        return lName;
    }

    public void setLName(String lName) {
        APPLICATION_LOGGER.debug("Setting the employee last name");
        
        if (lName == null || lName.isEmpty()) {
            APPLICATION_LOGGER.error("Last name cannot be null or empty");
            throw new IllegalArgumentException("Last name cannot be null or empty");
        }
        if (lName.length() < minStringLength || lName.length() > longStringMaxLength) {
            APPLICATION_LOGGER.error("Last name must be between " + minStringLength + " and " + longStringMaxLength + " characters");
            throw new IllegalArgumentException("Last name must be between " + minStringLength + " and " + longStringMaxLength + " characters");
        }

        String cleanlName = Sanitize.sanitizeHtml(lName);
        APPLICATION_LOGGER.debug("Setting last name to " + cleanlName);
        this.lName = cleanlName;
    }

    public String getDept() {
        APPLICATION_LOGGER.debug("Getting the employee department");
        return dept;
    }

    public void setDept(String dept) {
        APPLICATION_LOGGER.debug("Setting the employee department");

        if (dept == null || dept.isEmpty()) {
            APPLICATION_LOGGER.error("Department cannot be null or empty");
            throw new IllegalArgumentException("Department cannot be null or empty");
        }
        if (dept.length() < minStringLength || dept.length() > longStringMaxLength) {
            APPLICATION_LOGGER.error("Department must be between " + minStringLength + " and " + longStringMaxLength + " characters");
            throw new IllegalArgumentException("Department must be between " + minStringLength + " and " + longStringMaxLength + " characters");
        }

        String cleanDept = Sanitize.sanitizeHtml(dept);
        APPLICATION_LOGGER.debug("Setting department to " + cleanDept);
        this.dept = cleanDept;
    }

    public String getTitle() {
        APPLICATION_LOGGER.debug("Getting the employee title");
        return title;
    }

    public void setTitle(String title) {
        String cleanTitle = Sanitize.sanitizeHtml(title);
        APPLICATION_LOGGER.debug("Setting the employee title");
        this.title = cleanTitle;
    }
}
