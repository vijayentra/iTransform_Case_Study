package com.booking.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.booking.entity.BookingDetails;
import com.booking.entity.BookingHistory;
import com.booking.exception.BookingException;
import com.booking.service.BookingService;

@RestController
@RequestMapping("/booking")
public class BookingController {
	@Autowired
	private BookingService bookingService;
	
//	@PostMapping("/bookWash")
//	public BookingHistory bookWash(@PathVariable Long phoneNumber, @RequestBody BookingDetails bookingDetails) {
//		return bookingService.bookWash(phoneNumber, bookingDetails);
//	}
	
	@PostMapping("/bookWash/{phoneNumber}")
	public ResponseEntity<?> bookWash(@PathVariable Long phoneNumber, @RequestBody BookingDetails bookingDetails){
		BookingHistory b = null;
		try {
		b = bookingService.bookWash(phoneNumber, bookingDetails);
		}catch(BookingException e) {
			System.out.println(e.getMessage());
			return new ResponseEntity<>(e.getMessage()+ "Please try again!", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(b,HttpStatus.OK); 
	}
}
