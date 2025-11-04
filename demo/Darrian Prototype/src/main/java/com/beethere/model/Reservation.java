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
        //LOG.debug("Setting the reservation id");
        if (ID < 0){
            //LOG.error("Reservation ID cannot be less than 0");
            throw new IllegalArgumentException("Reservation ID cannot be less than 0");
        }

        this.ID = ID;
    }

    
    public Date getStartTime() {
        return startTime;
    }


    public void setStartTime(Date startTime) {
        Date now = new Date();
        if (startTime == null){
            throw new IllegalArgumentException("Start time cannot be null");
        }
        if (startTime.after(endTime)) {
            throw new IllegalArgumentException("Start time cannot be after end time");
        }
        if (startTime.equals(endTime)) {
            throw new IllegalArgumentException("Start time cannot be the same as end time");
        }
        if (startTime.before(now)) {
            throw new IllegalArgumentException("Start time cannot be in the past");
        }
        this.startTime = startTime;
    }


    public Date getEndTime() {
        return endTime;
    }


    public void setEndTime(Date endTime) {
        Date now = new Date();

        if (endTime == null){
            throw new IllegalArgumentException("End time cannot be null");
        }
        if (endTime.before(startTime)) {
            throw new IllegalArgumentException("End time cannot be before start time");
        }
        if (endTime.equals(startTime)) {
            throw new IllegalArgumentException("End time cannot be the same as start time");
        }
        if (endTime.before(now)) {
            throw new IllegalArgumentException("End time cannot be in the past");
        }
        this.endTime = endTime;
    }


    public String getOwner() {
        return owner;
    }
    
    
    public void setOwner(String owner) {
        final int min = 2;
        final int max = 40;

        if (owner == null || owner.isEmpty()){
            throw new IllegalArgumentException("Owner cannot be null or empty");
        }
        if (owner.length() < min || owner.length() > max) {
            throw new IllegalArgumentException("Owner name must be between " + min + " and " + max + " characters");
        }
        
        this.owner = owner;
    }


    public int getOwnerID() {
        return ownerID;
    }
    
    
    public void setOwnerID(int ownerID) {
        if (ownerID < 0){
            throw new IllegalArgumentException("Owner ID cannot be less than 0");
        }
        this.ownerID = ownerID;
    }


    public ArrayList<Room> getRooms() {
        return rooms;
    }


    public void setRooms(ArrayList<Room> rooms) {
        if (rooms == null || rooms.isEmpty()){
            throw new IllegalArgumentException("Rooms cannot be null or empty");
        }
        this.rooms = rooms;
    }

    
}
