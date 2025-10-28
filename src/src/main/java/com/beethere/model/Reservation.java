package com.beethere.model;

import java.util.ArrayList;
import java.util.Date;

public class Reservation {
    private int ID;
    private Date startTime;
    private Date endTime;
    private String owner;
    private int ownerID;
    private ArrayList<Room> rooms;

    public Reservation(int ID, Date startTime, Date endTime, String owner, int ownerID, ArrayList<Room> rooms){
        setID(ID);
        setStartTime(startTime);
        setEndTime(endTime);
        setOwner(owner);
        setOwnerID(ownerID);
        setRooms(rooms);
    }

    
    //TODO: Implement data validation for setters
    public int getID() {
        return ID;
    }
    public void setID(int ID) {
        this.ID = ID;
    }
    public Date getStartTime() {
        return startTime;
    }
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }
    public Date getEndTime() {
        return endTime;
    }
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
    public String getOwner() {
        return owner;
    }
    public void setOwner(String owner) {
        this.owner = owner;
    }
    public int getOwnerID() {
        return ownerID;
    }
    public void setOwnerID(int ownerID) {
        this.ownerID = ownerID;
    }
    public ArrayList<Room> getRooms() {
        return rooms;
    }
    public void setRooms(ArrayList<Room> rooms) {
        this.rooms = rooms;
    }

    
}
