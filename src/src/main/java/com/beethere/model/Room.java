package com.beethere.model;

import java.util.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
public class Room {
	@Id
	public String roomNumber;
	@NotBlank(message = "City is required")
	@Size(min = 4, max = 50, message = "City must be between 4 and 50 characters")
	public String city;
	public String country;
	public String address;
	public String type;
	public Integer seatCount;
}

