package com.beethere.model;

public class Employee {

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
    public Roles getRole() {
        return role;
    }
    public void setRole(Roles role) {
        this.role = role;
    }

}
