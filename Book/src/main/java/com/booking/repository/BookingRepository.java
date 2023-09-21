package com.booking.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.booking.entity.BookingDetails;

@Repository
public interface BookingRepository extends MongoRepository <BookingDetails, String> {

		BookingDetails findByBookingId(String bookingId);
		List<BookingDetails> findByCustomerPhoneNumber(String phoneNumber);
		List<BookingDetails> findByWasherPhoneNumber(String phoneNumber);
		BookingDetails findByInvoice_BookingId(String bookingId);
}