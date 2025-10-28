package com.beethere.model;

public class Room {
    private int ID;
    private String country;
    private String city;
    private String address;
    private String roomNumber;
    private String type;
    private int seatCount;

    public Room(int ID, String country, String city, String address, String roomNumber, String type, int seatCount) {
        setID(ID);
        setCountry(country);
        setCity(city);
        setAddress(address);
        setRoomNumber(roomNumber);
        setType(type);
        setSeatCount(seatCount);
    }

    //TODO: Implement data validation for setters
    public int getID() {
        return ID;
    }
    public void setID(int ID) {
        this.ID = ID;
    }
    public String getCountry() {
        return country;
    }
    public void setCountry(String country) {
        this.country = country;
    }
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getRoomNumber() {
        return roomNumber;
    }
    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public int getSeatCount() {
        return seatCount;
    }
    public void setSeatCount(int seatCount) {
        this.seatCount = seatCount;
    }

    
}
