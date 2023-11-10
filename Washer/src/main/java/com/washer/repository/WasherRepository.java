package com.washer.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.washer.entity.Washer;
@Repository
public interface WasherRepository extends MongoRepository<Washer, String>{

	Optional<Washer> findByPhoneNumber(String phoneNumber);
	Washer deleteByPhoneNumber(String phoneNumber);
	Optional<Washer> findByPhoneNumberAndPassword(String phoneNumber, String password);
}
