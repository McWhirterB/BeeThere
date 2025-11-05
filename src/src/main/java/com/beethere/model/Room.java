package com.beethere.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;



@Entity
public class Room {

	@Id
    @Column(name = "roomId")
	private Integer roomId;
	private String location;
	private String building;
    @Column(name = "room_number")
	private String roomNumber;
	private String type;
	private Integer seats;

    private static final Logger LOG = LogManager.getLogger();

    public Room() {}

	public Room(int id, String location, String building, String roomNumber, String type, int seatCount) {
        setId(id);
        setLocation(location);
		setBuilding(building);
        setRoomNumber(roomNumber);
        setType(type);
        setSeatCount(seatCount);
    }

    public int getId() {
           LOG.debug("returning the ID: " + roomId);
        return roomId;
    }
    public void setId(int id) {
        this.roomId = id;
    }
	public String getLocation() {
         LOG.debug("returning the Location: " + location);
        return location;
    }
    public void setLocation(String location) {
           LOG.debug("setting the building");

        if (location == null || location.isEmpty())
        {
            LOG.error("building must not be empty");
            throw new IllegalArgumentException("building must not be empty");
        }
        if (location.length() > 50 || location.length() < 1)
        {
            LOG.error("building must be between 1 and 50 chars in length");
            throw new IllegalArgumentException("building must be between 1 and 50 chars in length");
        }
        LOG.debug("setting the building to: " + location);
        this.location = location;
    }
    public String getBuilding() {
         LOG.debug("returning the Address: " + building);
        return building;
    }
    public void setBuilding(String building) {
           LOG.debug("setting the building");

        if (building == null)
        {
            LOG.error("building must not be null");
            throw new IllegalArgumentException("building must not be null");
        }

        if (building.isEmpty())
        {
            LOG.error("building must not be empty");
            throw new IllegalArgumentException("building must not be empty");
        }
        if (building.length() > 50 || building.length() < 1)
        {
            LOG.error("building must be between 1 and 50 chars in length");
            throw new IllegalArgumentException("building must be between 1 and 50 chars in length");
        }
        LOG.debug("setting the building to: " + building);
        this.building = building;
    }
    public String getRoomNumber() {
		LOG.debug("returning the Room Number: " + roomNumber);
        return roomNumber;
	}
    public void setRoomNumber(String roomNumber) {
         LOG.debug("setting the room number");

        if (roomNumber == null)
        {
            LOG.error("room number must not be null");
            throw new IllegalArgumentException("room number must not be null");
        }

        if (roomNumber.isEmpty())
        {
            LOG.error("room number must not be empty");
            throw new IllegalArgumentException("room number must not be empty");
        }
        if (roomNumber.length() > 50 || roomNumber.length() < 1)
        {
            LOG.error("room number must be between 3 and 5 chars in length");
            throw new IllegalArgumentException("room number must be between 3 and 5 chars in length");
        }
        LOG.debug("setting the room number to: " + roomNumber);
        this.roomNumber = roomNumber;
    }
    public String getType() {
          LOG.debug("returning the Type: " + type);
        return type;
    }
    public void setType(String type) {
         LOG.debug("setting the type of the room");

        if (type == null)
        {
            LOG.error("Type must not be null");
            throw new IllegalArgumentException("Type must not be null");
        }

        if (type.isEmpty())
        {
            LOG.error("Type must not be empty");
            throw new IllegalArgumentException("Type must not be empty");
        }
        if (type.length() > 50 || type.length() < 1)
        {
            LOG.error("Type must be between 1 and 50 characters in length");
            throw new IllegalArgumentException("Type must be between 1 and 50 characters in length");
        }
        LOG.debug("setting the Type to: " + type);
        this.type = type;
    }
    public int getSeatCount() {
        LOG.debug("returning the Seat Count: " + seats);
        return seats;
    }
    public void setSeatCount(int seatCount) {
        LOG.debug("setting the Seat Count");


        if (seatCount > 250 || seatCount < 1)
        {
            LOG.error("Seat Count must be between 1 and 250");
            throw new IllegalArgumentException("Seat Count must be between 1 and 250");
        }
        LOG.debug("setting the seat count to: " + seatCount);
        this.seats = seatCount;
    }

}

