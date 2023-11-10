package com.booking.service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.booking.dummyentity.CarDetails;
import com.booking.dummyentity.Customer;
import com.booking.dummyentity.RatingRequestHelper;
import com.booking.dummyentity.Washer;
import com.booking.entity.AddOns;
import com.booking.entity.BookingDetails;
import com.booking.entity.InvoiceDetails;
import com.booking.entity.Packages;
import com.booking.exception.BookingException;
import com.booking.repository.AddOnsRepository;
import com.booking.repository.BookingRepository;
import com.booking.repository.PackagesRepository;

@Service
public class BookingServiceImpl implements BookingService{ 
	@Autowired
	private BookingRepository bookingRepository;
	@Autowired
	private PackagesRepository packagesRepository;
	@Autowired
	private AddOnsRepository addonsRepository;

	@Autowired
	private RestTemplate rest;

	private static final int cPort = 8088;
	private static final int wPort = 8086;

	@Override
	public BookingDetails bookWash(String phoneNumber, BookingDetails bookingDetails) {
		List<BookingDetails> history = bookingRepository.findAll();
		Customer cust = rest.getForObject
				("http://localhost:"+cPort+"/customer/viewCustomer/"+phoneNumber, Customer.class);
		int n = 0;
		for(CarDetails c : cust.getCarsList()) {
			if(c.getNumberPlate().equals(bookingDetails.getCarNumber())) {  
				n=1;
				break;
			}
		}
		if(n==0) throw new BookingException("Car does not exist. ");

		//setting up the booking date and time
		LocalDateTime localDateTime = LocalDateTime.now();
		// Convert it to IST (Indian Standard Time)
		ZoneId istZoneId = ZoneId.of("Asia/Kolkata");
		ZonedDateTime istDateTime = localDateTime.atZone(istZoneId);
		// Define the desired date and time format
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy : HH:mm:ss");
		// Format the date and time
		String formattedDateTime = istDateTime.format(formatter);
		bookingDetails.setBookingDateAndTime(formattedDateTime);

		String bId = bookingDetails.getWashDate()+"_"+
				bookingDetails.getWashTime()+"_"+bookingDetails.getCarNumber();
		for(BookingDetails d : history) {
			if(d.getBookingId().equals(bId)) {
				throw new BookingException("Booking already exists. ");
			}
		}

		List<Washer> washerList = null;
		ResponseEntity<List<Washer>> response = rest.exchange(
				"http://localhost:"+wPort+"/washer/viewAllWasher",
				HttpMethod.GET,
				null,
				new ParameterizedTypeReference<List<Washer>>() {}
				);

		if (response.getStatusCode()==HttpStatus.OK) {
			washerList = response.getBody();
		}



		for(Washer w : washerList) { 
			int count = 0;
			for(BookingDetails detail : history) {
				if(detail.getWasherPhoneNumber().equals(w.getPhoneNumber())) {
					if(detail.getWashDate().equals(bookingDetails.getWashDate()) && detail.getWashTime().equals(bookingDetails.getWashTime())
							&& (!detail.getWashStatus().equals("CANCELLED"))&&(!detail.getWashStatus().equals("DECLINED"))){
						count = 1;
						break;
					}
				}
			}
			if(count == 0) {
				String msg = "";
				bookingDetails.setCustomerName(cust.getFirstName()+ " " + cust.getLastName());
				bookingDetails.setWasherName(w.getFirstName()+ " "+ w.getLastName());
				bookingDetails.setBookingId(bookingDetails.getWashDate()+"_"+
						bookingDetails.getWashTime()+"_"+bookingDetails.getCarNumber());
				bookingDetails.setCustomerPhoneNumber(cust.getPhoneNumber());
				bookingDetails.setWasherPhoneNumber(w.getPhoneNumber());
				bookingDetails.setCustomerRating(cust.getRating());
				bookingDetails.setWasherRating(w.getRating());

				Packages p = packagesRepository.findByPackageName(bookingDetails.getWashPackage());
				if(p==null) msg = msg + "Invalid Package: "+ bookingDetails.getWashPackage()+ ".\n";
				for(String s : bookingDetails.getWashAddOn()) {
					AddOns a = addonsRepository.findByAddOnName(s);
					if(a==null) msg = msg + "Invalid addOn: "+s+ ".\n";
				}
				if(msg!="") throw new BookingException(msg);
				return bookingRepository.save(bookingDetails); 
			}

		} 
		throw new BookingException("No washer available at the given time slot. Sorry for the inconvenience. "); 
	}

	@Override
	public BookingDetails rescheduleWash(String bookingId, String washDate, String washTime) {
		BookingDetails b = null;
		b = bookingRepository.findByBookingId(bookingId);
		if(b==null) throw new BookingException("Booking does not exist. ");
		if(b.getWashStatus().equals("ACCEPTED") && b.getWashStatus().equals("REJECTED") &&
				b.getWashStatus().equals("CANCELLED")){
			throw new BookingException("Cannot be rescheduled. Sorry for the inconvenience. ");
		}

		List<Washer> washerList = null;
		ResponseEntity<List<Washer>> response = rest.exchange(
				"http://localhost:"+wPort+"/washer/viewAllWasher",
				HttpMethod.GET,
				null,
				new ParameterizedTypeReference<List<Washer>>() {}
				);

		if (response.getStatusCode()==HttpStatus.OK) {
			washerList = response.getBody();
		}
		List<BookingDetails> list = bookingRepository.findAll();


		for(Washer w : washerList) {
			int count = 0;
			for(BookingDetails book : list) {
				if(book.getWasherPhoneNumber().equals(w.getPhoneNumber())) {
					if(washDate.equals(book.getWashDate()) && washTime.equals(book.getWashTime())&& 
							(!book.getWashStatus().equals("CANCELLED"))&&(!book.getWashStatus().equals("DECLINED"))){
						count = 1;
						break;
					}
				}
			}
			if(count == 0) {
				b.setWasherName(w.getFirstName()+ " "+ w.getLastName());
				String bId = washDate+"_"+washTime+"_"+b.getCarNumber();
				for(BookingDetails d : list) {
					if(d.getBookingId().equals(bId)) {
						throw new BookingException("Booking already exists. ");
					}
				}
				b.setBookingId(bId);
				b.setWasherPhoneNumber(w.getPhoneNumber());
				b.setWasherRating(w.getRating());
				b.setWashDate(washDate);
				b.setWashTime(washTime);
				b.setWashStatus("REQUESTED");
				bookingRepository.save(b);
				return b;
			}

		}
		throw new BookingException("Washer unavailable at the given time slot. Sorry for the inconvenience. ");
	}

	@Override
	public BookingDetails cancelWash(String bookingId) {
		BookingDetails b = null;
		b = bookingRepository.findByBookingId(bookingId);
		if(b==null) throw new BookingException("Booking does not exist. ");
		if(b.getWashStatus().equals("CANCELLED")) throw new BookingException("Booking already cancelled. ");
		if(b.getWashStatus().equals("ACCEPTED")) throw new BookingException("Accepted by washer. Cannot cancel."); 
		if(b.getWashStatus().equals("DECLINED")) throw new BookingException("Declined by washer. Cannot cancel."); 
		b.setWashStatus("CANCELLED");
		b.setBookingId(b.getBookingId()+"*");
		bookingRepository.save(b);
		return b;
	}

	@Override
	public BookingDetails respondWash(String bookingId, String response) {
		BookingDetails b = null;
		b = bookingRepository.findByBookingId(bookingId);
		if(b==null) throw new BookingException("Booking does not exist. ");
		if(b.getWashStatus().equals("CANCELLED")) throw new BookingException("Booking cancelled. ");
		if(b.getWashStatus().equals("ACCEPTED")) throw new BookingException("Accepted already. "); 
		if(b.getWashStatus().equals("DECLINED")) throw new BookingException("Declined already. "); 
		if(response.equals("ACCEPT")) b.setWashStatus("ACCEPTED");
		if(response.equals("DECLINE")) {
			b.setWashStatus("DECLINED");
			b.setBookingId(b.getBookingId()+"#");
		}
		bookingRepository.save(b); 
		return b; 
	}

	@Override
	public BookingDetails washComplete(String bookingId, int rating) {
		BookingDetails b = null;
		b = bookingRepository.findByBookingId(bookingId);
		if(b==null) throw new BookingException("Booking does not exist. ");
		if(b.getWashStatus().equals("COMPLETED")) throw new BookingException("Wash Completed already. ");


		b.setCustomerRatingGiven(rating);
		b.setWashStatus("COMPLETED");

		//updating the customerRating in customer entity of userManagementModule
		RatingRequestHelper request = new RatingRequestHelper(b.getCustomerPhoneNumber(), rating);
		HttpEntity<RatingRequestHelper> entity = new HttpEntity<>(request);

		ResponseEntity<String> response = rest.exchange(
				"http://localhost:"+cPort+"/customer/updateCustomerRating/" + b.getCustomerPhoneNumber()+"/"+ rating,
				HttpMethod.PUT,
				entity,
				String.class
				);
		//updating the customer rating in the booking details
		Customer cust = rest.getForObject
				("http://localhost:"+cPort+"/customer/viewCustomer/"+b.getCustomerPhoneNumber(), Customer.class);
		b.setCustomerRating(cust.getRating());
		for(BookingDetails bd : bookingRepository.findAll()) {
			if(bd.getCustomerPhoneNumber().equals(cust.getPhoneNumber())) {
				bd.setCustomerRating(cust.getRating());
				bookingRepository.save(bd);
			}
		}
		//INVOICE CREATION
		InvoiceDetails inv = new InvoiceDetails();
		LocalDateTime localDateTime = LocalDateTime.now();
		ZoneId istZoneId = ZoneId.of("Asia/Kolkata");
		ZonedDateTime istDateTime = localDateTime.atZone(istZoneId);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy : HH:mm:ss");
		String formattedDateTime = istDateTime.format(formatter);
		inv.setBillingDateAndTime(formattedDateTime);

		double amount = 0;
		Packages p = packagesRepository.findByPackageName(b.getWashPackage());
		amount += p.getPrice();
		for(String s : b.getWashAddOn()) {
			AddOns a = addonsRepository.findByAddOnName(s);
			amount+=a.getPrice();
		}

		inv.setBill_Amount(amount);
		inv.setBookingId(bookingId);
		inv.setWash_package(b.getWashPackage());
		inv.setWashAddOn(b.getWashAddOn());
		b.setInvoice(inv);
		bookingRepository.save(b);
		return b;
	}

	@Override
	public BookingDetails rateWasher(String bookingId, int rating) {
		BookingDetails b = bookingRepository.findByBookingId(bookingId);
		if(b.getWasherRatingGiven()!=0) throw new BookingException("Rating given already. ");
		if(!b.getWashStatus().equals("COMPLETED")) throw new BookingException("Rate after the car wash is completed. ");
		b.setWasherRatingGiven(rating);
		//updating the washerRating in Washer Module
		RatingRequestHelper request = new RatingRequestHelper(b.getWasherPhoneNumber(), rating);
		HttpEntity<RatingRequestHelper> entity = new HttpEntity<>(request);

		ResponseEntity<String> response = rest.exchange(
				"http://localhost:"+wPort+"/washer/updateWasherRating/" + b.getWasherPhoneNumber()+"/"+ rating,
				HttpMethod.PUT,
				entity,
				String.class
				);
		//updating the washer rating in the booking details
		Washer w = rest.getForObject
				("http://localhost:"+wPort+"/washer/viewWasher/"+b.getWasherPhoneNumber(), Washer.class);
		b.setWasherRating(w.getRating());
		for(BookingDetails bd : bookingRepository.findAll()) {
			if(bd.getWasherPhoneNumber().equals(w.getPhoneNumber())) {
				bd.setWasherRating(w.getRating());
				bookingRepository.save(bd); 
			}
		}
		bookingRepository.save(b); 
		return b;
	}

	@Override
	public BookingDetails viewBookingDetails(String bookingId) {
		BookingDetails b=null;
		b = bookingRepository.findByBookingId(bookingId);
		if(b == null) throw new BookingException("Booking does not exist.");
		return b;
	}

	@Override
	public InvoiceDetails viewInvoiceDetails(String bookingId) {
		BookingDetails b = bookingRepository.findByInvoice_BookingId(bookingId);
		if(b == null) throw new BookingException("Invoice does not exist for the given bookingId.");
		InvoiceDetails inv = b.getInvoice(); 
		return inv;
	}

	@Override
	public List<BookingDetails> viewCustomerHistory(String phoneNumber) {
		List<BookingDetails> list = null;
		list = bookingRepository.findByCustomerPhoneNumber(phoneNumber);
		if(list.isEmpty()) throw new BookingException("Customer has no wash history. ");
		return list;
	}

	@Override
	public List<BookingDetails> viewWasherHistory(String phoneNumber) {
		List<BookingDetails> list = null;
		list = bookingRepository.findByWasherPhoneNumber(phoneNumber);
		if(list.isEmpty()) throw new BookingException("Washer has no wash history. ");
		return list;
	}

	@Override
	public List<BookingDetails> viewBookingHistory() {
		List<BookingDetails> list = null;
		list = bookingRepository.findAll();
		if(list.isEmpty()) throw new BookingException("No history of car washes. ");
		return list;
	}

	@Override
	public void updateWasherDetails(String oldPhoneNumber, String name, String phoneNumber) {
		List<BookingDetails> b = bookingRepository.findAll();
		if(b.isEmpty()) throw new BookingException("no washes");
		for(BookingDetails bd : b) {
			if(bd.getWasherPhoneNumber().equals(oldPhoneNumber)) {
				bd.setWasherName(name);
				bd.setWasherPhoneNumber(phoneNumber);
				bookingRepository.save(bd);
			}
		}
	}

	@Override
	public void updateCustomerDetails(String oldPhoneNumber, String name, String phoneNumber) {
		List<BookingDetails> b = bookingRepository.findAll();
		if(b.isEmpty()) throw new BookingException("no washes");
		for(BookingDetails bd : b) {
			if(bd.getCustomerPhoneNumber().equals(oldPhoneNumber)) {
				bd.setCustomerName(name);
				bd.setCustomerPhoneNumber(phoneNumber);
				bookingRepository.save(bd);
			}
		}
	}

}
