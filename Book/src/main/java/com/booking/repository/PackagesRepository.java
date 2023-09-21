package com.booking.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.booking.entity.Packages;

@Repository
public interface PackagesRepository extends MongoRepository<Packages, String>{
	Packages findByPackageName(String packageName);
}
