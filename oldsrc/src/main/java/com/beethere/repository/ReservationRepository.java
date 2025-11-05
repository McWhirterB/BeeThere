package com.beethere.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.beethere.model.*;




@Repository
public interface ReservationRepository extends CrudRepository<Reservation, Integer> {

   
}
