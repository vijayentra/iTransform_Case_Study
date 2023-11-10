package com.booking.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.booking.dummyentity.BookingId;
import com.booking.dummyentity.Rating;
import com.booking.dummyentity.Response;
import com.booking.dummyentity.DateTime;
import com.booking.entity.BookingDetails;
import com.booking.entity.InvoiceDetails;
import com.booking.exception.BookingException;
import com.booking.service.BookingService;


@RestController
@RequestMapping("/booking")
@CrossOrigin("*")
public class BookingController {  
	@Autowired
	private BookingService bookingService;
	
	@PostMapping("/bookWash/{phoneNumber}")
	public ResponseEntity<?> bookWash(@PathVariable String phoneNumber, @RequestBody BookingDetails bookingDetails){
		BookingDetails b = null;
		try {
		b = bookingService.bookWash(phoneNumber, bookingDetails);
		}catch(BookingException e) {
			System.out.println(e.getMessage());
			return new ResponseEntity<>(e.getMessage()+ "Please try again!", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(b,HttpStatus.OK);  
	}
	
	@PutMapping("/rescheduleWash/{bookingId}")
	public ResponseEntity<?> rescheduleWash(@PathVariable String bookingId,@RequestBody DateTime rescheduleRequest){
		BookingDetails b = null;
		try {
		b = bookingService.rescheduleWash(bookingId, rescheduleRequest.getWashDate(), rescheduleRequest.getWashTime());
		}catch(BookingException e) {
			System.out.println(e.getMessage());
			return new ResponseEntity<>(e.getMessage()+ "Please try again!", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(b,HttpStatus.OK); 
	}
	
	@PutMapping("/cancelWash/{bookingId}")
	public ResponseEntity<?> cancelWash(@PathVariable String bookingId){
		BookingDetails b = null;
		try {
		b = bookingService.cancelWash(bookingId);
		}catch(BookingException e) {
			System.out.println(e.getMessage());
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(b,HttpStatus.OK); 
	}
	
	@PutMapping("/respondWash/{bookingId}")
	public ResponseEntity<?> respondWash(@PathVariable String bookingId, @RequestBody Response respondRequest){
		BookingDetails b = null;
		try {
		b = bookingService.respondWash(bookingId, respondRequest.getResponse());
		}catch(BookingException e) {
			System.out.println(e.getMessage());
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(b,HttpStatus.OK); 
	}
	
	@PutMapping("/washComplete/{bookingId}")
	public ResponseEntity<?> washComplete(@PathVariable String bookingId, @RequestBody Rating washCompleteRequest){
	BookingDetails b = null;
		try {
		b = bookingService.washComplete(bookingId, washCompleteRequest.getRating());
		}catch(BookingException e) {
			System.out.println(e.getMessage());
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(b,HttpStatus.OK); 
	}
	
	@PutMapping("/rateWasher/{bookingId}")
	public ResponseEntity<?> rateWasher(@PathVariable String bookingId,@RequestBody Rating rateWasherRequest){
	BookingDetails b = null;
		try {
		b = bookingService.rateWasher(bookingId, rateWasherRequest.getRating());
		}catch(BookingException e) {
			System.out.println(e.getMessage());
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(b,HttpStatus.OK); 
	}
	
	@GetMapping("/viewBookingDetails/{bookingId}")
	public ResponseEntity<?> viewBookingDetails(@PathVariable String bookingId){
	BookingDetails b = null;
		try {
		b = bookingService.viewBookingDetails(bookingId);
		}catch(BookingException e) {
			System.out.println(e.getMessage());
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(b,HttpStatus.OK); 
	}
	
	@GetMapping("/viewInvoiceDetails/{bookingId}")
	public ResponseEntity<?> viewInvoiceDetails(@PathVariable String bookingId){
	InvoiceDetails Inv = null;
		try {
		Inv = bookingService.viewInvoiceDetails(bookingId);
		}catch(BookingException e) {
			System.out.println(e.getMessage());
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(Inv,HttpStatus.OK); 
	}
	
	@GetMapping("/viewCustomerHistory/{phoneNumber}")
	public ResponseEntity<?> viewCustomerHistory(@PathVariable String phoneNumber){
	List<BookingDetails> list = null;
		try {
		 list = bookingService.viewCustomerHistory(phoneNumber);
		}catch(BookingException e) {
			System.out.println(e.getMessage());
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(list,HttpStatus.OK); 
	}
	
	
	
	@GetMapping("/viewWasherHistory/{phoneNumber}")
	public ResponseEntity<?> viewWasherHistory(@PathVariable String phoneNumber){
	List<BookingDetails> list = null;
		try {
		 list = bookingService.viewWasherHistory(phoneNumber);
		}catch(BookingException e) {
			System.out.println(e.getMessage());
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(list,HttpStatus.OK); 
	}
	
	@GetMapping("/viewBookingHistory")
	public ResponseEntity<?> viewBookingHistory(){
	List<BookingDetails> list = null;
		try {
		 list = bookingService.viewBookingHistory();
		}catch(BookingException e) {
			System.out.println(e.getMessage());
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(list,HttpStatus.OK); 
	}
	
	@PutMapping("/updateWasherDetails/{oldPhoneNumber}/{washerName}/{phoneNumber}")
	public ResponseEntity<?> updateWasherDetails(@PathVariable String oldPhoneNumber, @PathVariable String washerName,
												@PathVariable String phoneNumber){
		try {
		bookingService.updateWasherDetails(oldPhoneNumber, washerName, phoneNumber);
		}catch(BookingException e) {
			System.out.println(e.getMessage());
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>("updated!",HttpStatus.OK); 
	}
	
	@PutMapping("/updateCustomerDetails/{oldPhoneNumber}/{customerName}/{phoneNumber}")
	public ResponseEntity<?> updateCustomerDetails(@PathVariable String oldPhoneNumber, @PathVariable String customerName,
												@PathVariable String phoneNumber){
		try {
		bookingService.updateCustomerDetails(oldPhoneNumber, customerName, phoneNumber);
		}catch(BookingException e) {
			System.out.println(e.getMessage());
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>("updated!",HttpStatus.OK); 
	}
}
