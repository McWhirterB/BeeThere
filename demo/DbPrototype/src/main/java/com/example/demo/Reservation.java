package com.example.demo;

import java.util.*;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Id;

@Entity
public class Reservation {
	@Id
	public Integer reservationId;
	public Date startTime;
	public Date endTime;
	public Integer employeeId;
	public String employeeName;
}
