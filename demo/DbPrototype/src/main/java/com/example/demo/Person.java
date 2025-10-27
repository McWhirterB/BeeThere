package com.example.demo;

import java.util.*;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Id;

@Entity
public class Person {
	@Id
	public Integer id;
	public String name;
	public String password;
}
