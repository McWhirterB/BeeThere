package com.beethere.repository;

import java.util.*;
import com.beethere.model.*;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository extends CrudRepository<Reservation, Integer> {
	 List<Reservation> findByEmployeeId(Integer employeeId);	
}
