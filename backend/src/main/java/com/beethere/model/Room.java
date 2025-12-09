package com.beethere.model;

import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.beethere.utils.sanitizer.Sanitize;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

@Entity
public class Room {

	@Id
    @Column(name = "roomId")
	private Integer roomId;
    @Column(name = "location")
	private String country;
	private String building;
    @Column(name = "room_number")
	private String roomNumber;
	private String type;
	private Integer seats;

    @ManyToMany(mappedBy = "rooms")
    @JsonIgnore 
    private Set<Reservation> reservations; 

    private static final Logger APPLICATION_LOGGER = LogManager.getLogger("Application");
    
    public Room() {
        APPLICATION_LOGGER.debug("Constructing Default Room");
    }

	public Room(int id, String country, String building, String roomNumber, String type, int seatCount) {
        APPLICATION_LOGGER.debug("Constructing Room");
        setId(id);
        setCountry(country);
		setBuilding(building);
        setRoomNumber(roomNumber);
        setType(type);
        setSeatCount(seatCount);
    }

    public int getId() {
        APPLICATION_LOGGER.debug("returning the ID: " + roomId);
        return roomId;
    }
    public void setId(int id) {
        this.roomId = id;
    }
	public String getCountry() {
        APPLICATION_LOGGER.debug("returning the country: " + country);
        return country;
    }
    public void setCountry(String country) {
           APPLICATION_LOGGER.debug("setting the country");

        if (country == null || country.isEmpty())
        {
            APPLICATION_LOGGER.error("country must not be empty");
            throw new IllegalArgumentException("country must not be empty");
        }
        if (country.length() > 50 || country.length() < 1)
        {
            APPLICATION_LOGGER.error("country must be between 1 and 50 chars in length");
            throw new IllegalArgumentException("country must be between 1 and 50 chars in length");
        }
        String cleanCountry = Sanitize.sanitizeHtml(country);
        APPLICATION_LOGGER.debug("setting the country to: " + cleanCountry);
        this.country = cleanCountry;
    }
    public String getBuilding() {
        APPLICATION_LOGGER.debug("returning the building: " + building);
        return building;
    }
    public void setBuilding(String building) {
        APPLICATION_LOGGER.debug("setting the building");

        if (building == null)
        {
            APPLICATION_LOGGER.error("building must not be null");
            throw new IllegalArgumentException("cibuildingty must not be null");
        }

        if (building.isEmpty())
        {
            APPLICATION_LOGGER.error("building must not be empty");
            throw new IllegalArgumentException("building must not be empty");
        }
        if (building.length() > 50 || building.length() < 1)
        {
            APPLICATION_LOGGER.error("building must be between 1 and 50 chars in length");
            throw new IllegalArgumentException("building must be between 1 and 50 chars in length");
        }
        String cleanBuilding = Sanitize.sanitizeHtml(building);
        APPLICATION_LOGGER.debug("setting the building to: " + cleanBuilding);
        this.building = cleanBuilding;
    }
    public String getRoomNumber() {
		APPLICATION_LOGGER.debug("returning the Room Number: " + roomNumber);
        return roomNumber;
	}
    public void setRoomNumber(String roomNumber) {
        APPLICATION_LOGGER.debug("setting the room number");

        if (roomNumber == null)
        {
            APPLICATION_LOGGER.error("room number must not be null");
            throw new IllegalArgumentException("room number must not be null");
        }

        if (roomNumber.isEmpty())
        {
            APPLICATION_LOGGER.error("room number must not be empty");
            throw new IllegalArgumentException("room number must not be empty");
        }
        if (roomNumber.length() > 50 || roomNumber.length() < 1)
        {
            APPLICATION_LOGGER.error("room number must be between 3 and 5 chars in length");
            throw new IllegalArgumentException("room number must be between 3 and 5 chars in length");
        }
        APPLICATION_LOGGER.debug("setting the room number to: " + roomNumber);
        this.roomNumber = roomNumber;
    }
    public String getType() {
        APPLICATION_LOGGER.debug("returning the Type: " + type);
        return type;
    }
    public void setType(String type) {
         APPLICATION_LOGGER.debug("setting the type of the room");

        if (type == null)
        {
            APPLICATION_LOGGER.error("Type must not be null");
            throw new IllegalArgumentException("Type must not be null");
        }

        if (type.isEmpty())
        {
            APPLICATION_LOGGER.error("Type must not be empty");
            throw new IllegalArgumentException("Type must not be empty");
        }
        if (type.length() > 50 || type.length() < 1)
        {
            APPLICATION_LOGGER.error("Type must be between 1 and 50 characters in length");
            throw new IllegalArgumentException("Type must be between 1 and 50 characters in length");
        }
        String cleanType = Sanitize.sanitizeHtml(type);
        APPLICATION_LOGGER.debug("setting the Type to: " + cleanType);
        this.type = cleanType;
    }
    public int getSeatCount() {
        APPLICATION_LOGGER.debug("returning the Seat Count: " + seats);
        return seats;
    }
    public void setSeatCount(int seatCount) {
        APPLICATION_LOGGER.debug("setting the Seat Count");


        if (seatCount > 250 || seatCount < 1)
        {
            APPLICATION_LOGGER.error("Seat Count must be between 1 and 250");
            throw new IllegalArgumentException("Seat Count must be between 1 and 250");
        }
        APPLICATION_LOGGER.debug("setting the seat count to: " + seatCount);
        this.seats = seatCount;
    }

    public Set<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(Set<Reservation> reservations) {
        this.reservations = reservations;
    }

}

