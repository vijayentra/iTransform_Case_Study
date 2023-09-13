package com.booking.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.booking.entity.BookingHistory;

@Repository
public interface BookingRepository extends MongoRepository <BookingHistory, String> {

}