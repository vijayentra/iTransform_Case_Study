package com.booking.service;



import java.util.List;

import com.booking.entity.BookingDetails;
import com.booking.entity.InvoiceDetails;

public interface BookingService {
		
	public BookingDetails bookWash(String phoneNumber,BookingDetails bookingDetails);
	public BookingDetails rescheduleWash(String bookingId, String washdate, String washTime);
	public BookingDetails cancelWash(String bookingId);
	public BookingDetails respondWash(String bookingId, String response);
	public BookingDetails washComplete(String BookingId, int rating);
	public BookingDetails rateWasher(String bookingId, int rating);
	
	public void updateWasherDetails(String bookingId, String name, String phoneNumber);
	public void updateCustomerDetails(String bookingId, String name, String phoneNumber);
	
	public BookingDetails viewBookingDetails(String bookingId);
	public List<BookingDetails> viewBookingHistory();
	public List<BookingDetails> viewCustomerHistory(String phoneNumber);
	public List<BookingDetails> viewWasherHistory(String phoneNumber);
	
	public InvoiceDetails viewInvoiceDetails(String billingId);
	
}
