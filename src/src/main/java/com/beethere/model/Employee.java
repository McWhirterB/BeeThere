package com.beethere.model;

public class Employee {
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
        return ID;
    }
    public void setID(int ID) {
        this.ID = ID;
    }
    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getDepartment() {
        return department;
    }
    public void setDepartment(String department) {
        this.department = department;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

}
