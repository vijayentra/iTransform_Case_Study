package com.booking.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.booking.entity.BookingDetails;
import com.booking.entity.BookingHistory;
import com.booking.entity.CarDetails;
import com.booking.entity.Customer;
import com.booking.entity.Washer;
import com.booking.exception.BookingException;
import com.booking.repository.BookingRepository;

@Service
public class BookingServiceImpl implements BookingService{
	@Autowired
	private BookingRepository bookingRepository;
	
	@Autowired
	private RestTemplate rest;

	@Override
	public BookingHistory bookWash(long phoneNumber, BookingDetails bookingDetails) {
		List<BookingHistory> history = bookingRepository.findAll();
		Customer cust = rest.getForObject
				("http://localhost:8080/customer/viewCustomer/"+phoneNumber, Customer.class);
		int n = 0;
		for(CarDetails c : cust.getCarsList()) {
			if(c.getNumberPlate().equals(bookingDetails.getCarNumber())) {
				n=1;
				break;
			}
		}
		if(n==0) throw new BookingException("Invalid car Number");
		
		for(BookingHistory h : history) {
			BookingDetails d = h.getBookingDetails();
			if(d.getWashDate().equals(bookingDetails.getWashDate()) && 
					d.getWashTime().equals(bookingDetails.getWashTime()) && (!d.getWashStatus().equals("CANCELLED"))) {
				throw new BookingException("Booking already exists.");
			}
		}
		List<Washer> washerList = null;
		ResponseEntity<List<Washer>> response = rest.exchange(
			    "http://localhost:8080/washer/viewAllWasher",
			    HttpMethod.GET,
			    null,
			    new ParameterizedTypeReference<List<Washer>>() {}
			);

			if (response.getStatusCode()==HttpStatus.OK) {
			    washerList = response.getBody();
			}
			
		
		
		for(Washer w : washerList) {
			int count = 0;
			for(BookingHistory b : history) {
				BookingDetails detail = b.getBookingDetails();
				if(detail.getWasherPhoneNumber().equals(w.getPhoneNumber())) {
					if(detail.getWashDate().equals(bookingDetails.getWashDate()) && 
detail.getWashTime().equals(bookingDetails.getWashTime()) && (!detail.getWashStatus().equals("CANCELLED"))){
						count = 1;
						break;
					}
				}
			}
			if(count == 0) {
				bookingDetails.setCustomerName(cust.getFirstName()+ " " + cust.getLastName());
				bookingDetails.setWasherName(w.getFirstName()+ " "+ w.getLastName());
				bookingDetails.setBookingId(bookingDetails.getWashDate()+"/"+
							bookingDetails.getWashTime()+"/"+bookingDetails.getCarNumber());
				bookingDetails.setCustomerPhoneNumber(cust.getPhoneNumber());
				bookingDetails.setWasherPhoneNumber(w.getPhoneNumber());
				
				BookingHistory bh = new BookingHistory();
				bh.setBookingDetails(bookingDetails);
				bh.setBookingId(bh.getBookingDetails().getBookingId());
				return bookingRepository.save(bh);
			}
			
		}
		throw new BookingException("Sorry for the inconvenience. Washer unavailable");
	}

	@Override
	public BookingDetails rescheduleWash(String bookingId, LocalDate washdate, LocalTime washTime) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BookingDetails cancelWash(String bookingId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BookingDetails respondWash(String bookingId, String response) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BookingDetails washComplete(String BookingId, int rating) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BookingDetails rateWasher(String bookingId, int rating) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
