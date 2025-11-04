package com.beethere.model;

import java.util.*;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Id;

@Entity
public class Room {
	@Id
	public Integer roomId;
	public String location;
	public String building;
	public String roomNumber;
	public String type;
	public Integer seats;
}

