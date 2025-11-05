package com.beethere.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Id;

@Entity
public class Employee {
	@Id
	public Integer id;
	public String fName;	
	public String lName;
	public String loc;
	public String dept;
	public String title;
}

