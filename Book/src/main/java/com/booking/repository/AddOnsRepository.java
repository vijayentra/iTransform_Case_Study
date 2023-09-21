package com.booking.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.booking.entity.AddOns;

@Repository
public interface AddOnsRepository extends MongoRepository<AddOns, String>{
	AddOns findByAddOnName(String addOnName);
}
