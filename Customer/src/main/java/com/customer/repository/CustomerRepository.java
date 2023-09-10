package com.customer.repository;


import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.customer.entity.Customer;

@Repository
public interface CustomerRepository extends MongoRepository<Customer, String>{
	Optional<Customer> findByPhoneNumber(String phoneNumber);
	Customer deleteByPhoneNumber(String phoneNumber);
}
