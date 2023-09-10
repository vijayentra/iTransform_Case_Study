package com.customer.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.customer.entity.Washer;
@Repository
public interface WasherRepository extends MongoRepository<Washer, String>{

	Optional<Washer> findByPhoneNumber(String phoneNumber);
	Washer deleteByPhoneNumber(String phoneNumber);
}
