package com.beethere.model;

import java.util.*;
import jakarta.persistence.*;

@Entity
public class Room {
	@Id
	public String roomNumber;
	public String city;
	public String country;
	public String address;
	public String type;
	public Integer seatCount;
}

