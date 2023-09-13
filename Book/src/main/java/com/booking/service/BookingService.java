package com.booking.service;

import java.time.LocalDate;
import java.time.LocalTime;


import com.booking.entity.BookingDetails;
import com.booking.entity.BookingHistory;

public interface BookingService {
		
	public BookingHistory bookWash(long phoneNumber,BookingDetails bookingDetails);
	public BookingDetails rescheduleWash(String bookingId, LocalDate washdate, LocalTime washTime);
	public BookingDetails cancelWash(String bookingId);
	public BookingDetails respondWash(String bookingId, String response);
	public BookingDetails washComplete(String BookingId, int rating);
	public BookingDetails rateWasher(String bookingId, int rating);
	
//	public BookingDetails addBooking(BookingDetails bookingDetails);
//	public BookingDetails updateRescheduleBooking(String bookingId, LocalDate washdate, LocalTime washTime);
//	public BookingDetails updateCancelBooking(String bookingId);
//	public BookingDetails updateRespondBooking(String bookingId, String response);
//	public BookingDetails 
}
