package com.beethere.model;

/*
 * This is free and unencumbered software released into the public domain.
 * Anyone is free to copy, modify, publish, use, compile, sell, or distribute this software,
 * either in source code form or as a compiled binary, for any purpose, commercial or
 * non-commercial, and by any means.
 *
 * In jurisdictions that recognize copyright laws, the author or authors of this
 * software dedicate any and all copyright interest in the software to the public domain.
 * We make this dedication for the benefit of the public at large and to the detriment of
 * our heirs and successors. We intend this dedication to be an overt act of relinquishment in
 * perpetuity of all present and future rights to this software under copyright law.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR
 * PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS BE LIABLE FOR ANY CLAIM, DAMAGES
 * OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 * For more information, please refer to: https://unlicense.org/
*/

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import jakarta.persistence.*;


@Entity
public class RoomOld {

    private static final Logger LOG = LogManager.getLogger();
		@Id
    private Integer ID;
    private String country;
    private String city;
    private String address;
    private String roomNumber;
    private String type;
    private int seatCount;

		public RoomOld() {
			
		}

    public RoomOld(Integer ID, String country, String city, String address, String roomNumber, String type, int seatCount) {
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
           LOG.debug("returning the ID: " + ID);
        return ID;
    }
    public void setID(int ID) {
        this.ID = ID;
    }
    public String getCountry() {
         LOG.debug("returning the Country: " + country);
        return country;
    }
    public void setCountry(String country) {
     LOG.debug("setting the Country");
        final int max = 20;
        final int min = 2;

        if (country == null)
        {
            LOG.error("Country must not be null");
            throw new IllegalArgumentException("Country must not be null");
        }

        if (country.isEmpty())
        {
            LOG.error("Country must not be empty");
            throw new IllegalArgumentException("Country must not be empty");
        }
        if (country.length() > max || country.length() < min)
        {
            LOG.error("Country must be between 2 and 20 chars in length");
            throw new IllegalArgumentException("Country must be between 2 and 20 chars in length");
        }
        LOG.debug("setting the Country to: " + country);
        this.country = country;
    }
    public String getCity() {
        LOG.debug("returning the City: " + city);
        return city;
    }
    
    public void setCity(String city) {
          LOG.debug("setting the City");
        final int max = 20;
        final int min = 2;

        if (city == null)
        {
            LOG.error("City must not be null");
            throw new IllegalArgumentException("City must not be null");
        }

        if (city.isEmpty())
        {
            LOG.error("City must not be empty");
            throw new IllegalArgumentException("City must not be empty");
        }
        if (city.length() > max || city.length() < min)
        {
            LOG.error("City must be between 2 and 20 chars in length");
            throw new IllegalArgumentException("City must be between 2 and 20 chars in length");
        }
        LOG.debug("setting the City to: " + city);
        this.city = city;
    }
    public String getAddress() {
         LOG.debug("returning the Address: " + address);
        return address;
    }
    public void setAddress(String address) {
           LOG.debug("setting the address");
        final int max = 50;
        final int min = 5;

        if (address == null)
        {
            LOG.error("address must not be null");
            throw new IllegalArgumentException("address must not be null");
        }

        if (address.isEmpty())
        {
            LOG.error("address must not be empty");
            throw new IllegalArgumentException("address must not be empty");
        }
        if (address.length() > max || address.length() < min)
        {
            LOG.error("address must be between 5 and 50 chars in length");
            throw new IllegalArgumentException("address must be between 5 and 50 chars in length");
        }
        LOG.debug("setting the address to: " + address);
        this.address = address;
    }
    public String getRoomNumber() {
              LOG.debug("returning the Room Number: " + roomNumber);
        return roomNumber;
    }
    public void setRoomNumber(String roomNumber) {
         LOG.debug("setting the room number");
        final int max = 5;
        final int min = 3;

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
        if (roomNumber.length() > max || roomNumber.length() < min)
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
        final int max = 20;
        final int min = 5;

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
        if (type.length() > max || type.length() < min)
        {
            LOG.error("Type must be between 2 and 40 chars in length");
            throw new IllegalArgumentException("Type must be between 2 and 40 chars in length");
        }
        LOG.debug("setting the Type to: " + type);
        this.type = type;
    }
    public int getSeatCount() {
        LOG.debug("returning the Seat Count: " + seatCount);
        return seatCount;
    }
    public void setSeatCount(int seatCount) {
        LOG.debug("setting the Seat Count");
        final int max = 250;
        final int min = 1;

        if (seatCount > max || seatCount < min)
        {
            LOG.error("Seat Count must be between 1 and 250");
            throw new IllegalArgumentException("Seat Count must be between 1 and 250");
        }
        LOG.debug("setting the seat count to: " + seatCount);
        this.seatCount = seatCount;
    }

    
}
