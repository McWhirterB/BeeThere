package com.beethere.model;

import java.util.*;
import jakarta.persistence.*;

@Entity
public class Reservation {
	@Id
	public Integer reservationId;
	public Integer employeeId;
	public String employeeName;
	public Date startTime;
	public Date endTime;
}

