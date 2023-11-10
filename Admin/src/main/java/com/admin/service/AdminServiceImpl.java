package com.admin.service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.admin.dummyentity.*;
import com.admin.entity.AdminDetails;
import com.admin.entity.CustomerOverview;
import com.admin.entity.HistoryOfBookings;
import com.admin.entity.WasherOverview;
import com.admin.exception.InvalidAdminException;
import com.admin.repository.AdminRepository;
import com.admin.repository.CustomerOverviewRepository;
import com.admin.repository.HistoryRepository;
import com.admin.repository.WasherOverviewRepository;

@Service
public class AdminServiceImpl implements AdminService{

	@Autowired
	private AdminRepository adminRepository;
	@Autowired
	private HistoryRepository historyRepository;
	@Autowired
	private CustomerOverviewRepository customerRepository;
	@Autowired
	private WasherOverviewRepository washerRepository;
	@Autowired
	private RestTemplate rest;
	
	private static final int cPort = 8088;
	private static final int wPort = 8086;
	private static final int bPort = 8082;
	
	@Override
	public AdminDetails addAdmin(AdminDetails adminDetails) {
		List<AdminDetails> list = adminRepository.findAll();
		for(AdminDetails ad : list) {
			if(ad.getUsername().equals(adminDetails.getUsername())){
				throw new InvalidAdminException("Admin username already exists. ");
			}
		}
		String msg = "";
		if(adminDetails.getUsername().trim().length()==0) msg = msg+ "Username cannot be empty.\n";
		String nameRegex = "^[A-Z]{1}[a-z]{0,19}$";
		Pattern nameP = Pattern.compile(nameRegex);
		Matcher m1 = nameP.matcher(adminDetails.getUsername());
		if(!m1.matches()) msg = msg + "Invalid username.\n";
		
		if(adminDetails.getPassword().trim().length()==0) msg = msg + "Password cannot be empty.\n";
		String passRegex = "^(?=.*[A-Z])(?=.*[0-9])(?=.*[^A-Za-z0-9]).{8,}$";
		Pattern passP = Pattern.compile(passRegex);
		Matcher m8 = passP.matcher(adminDetails.getPassword());
		if(!m8.matches())msg = msg + "Password is weak. Make it stronger.\n";
		if(msg.equals("")) {
			return adminRepository.save(adminDetails);
		}
		throw new InvalidAdminException(msg);
	}

	@Override
	public AdminDetails updateAdmin(String username, AdminDetails adminDetails) {
		AdminDetails ad = adminRepository.findByUsername(username).
				orElseThrow(()->new InvalidAdminException("Admin does not exist."));
		if(ad==null) throw new InvalidAdminException("Admin does not exist. ");
		for(AdminDetails a : adminRepository.findAll()) {
			if(!a.getUsername().equals(ad.getUsername())) {
			if(a.getUsername().equals(adminDetails.getUsername())) {
				throw new InvalidAdminException("Admin already exists with the name. ");
				}
			}
		}
		String msg = "";
		if(adminDetails.getUsername().trim().length()==0) msg = msg+ "Username cannot be empty. ";
		String nameRegex = "^[A-Z]{1}[a-z]{0,19}$";
		Pattern nameP = Pattern.compile(nameRegex);
		Matcher m1 = nameP.matcher(adminDetails.getUsername());
		if(!m1.matches()) msg = msg + "Invalid username.\n";
		
		if(adminDetails.getPassword().trim().length()==0) msg = msg + "Password cannot be empty. ";
		String passRegex = "^(?=.*[A-Z])(?=.*[0-9])(?=.*[^A-Za-z0-9]).{8,}$";
		Pattern passP = Pattern.compile(passRegex);
		Matcher m8 = passP.matcher(adminDetails.getPassword());
		if(!m8.matches())msg = msg + "Password is weak. Make it stronger.\n";
		
		if(msg.equals("")) {
			ad.setUsername(adminDetails.getUsername());
			ad.setPassword(adminDetails.getPassword());
			return adminRepository.save(ad);
		}
		throw new InvalidAdminException(msg);
	}

	@Override
	public void deleteAdmin(String userName) { 
		AdminDetails ad = adminRepository.findByUsername(userName).
				orElseThrow(()->new InvalidAdminException("Admin does not exist."));
		if(ad==null) throw new InvalidAdminException("Admin does not exist. ");
		adminRepository.delete(ad);
	}

	@Override
	public AdminDetails viewAdmin(String userName) {
		AdminDetails ad = adminRepository.findByUsername(userName).
				orElseThrow(()->new InvalidAdminException("Admin does not exist."));
		return ad;
	}

	@Override
	public List<CustomerOverview> viewCustomerOverview() {
		customerRepository.deleteAll();
		customerRepository.resetAutoIncrementValue();
		List<Customer> customerList = null;
		ResponseEntity<List<Customer>> response = rest.exchange(
			    "http://localhost:"+cPort+"/customer/viewAllCustomer",
			    HttpMethod.GET,
			    null,
			    new ParameterizedTypeReference<List<Customer>>() {}
			);

			if (response.getStatusCode()==HttpStatus.OK) {
			    customerList = response.getBody(); 
			}
			
		for(Customer c : customerList) {
				CustomerOverview u = new CustomerOverview();
					u.setName(c.getFirstName()+" "+ c.getLastName());
					u.setPhoneNumber(c.getPhoneNumber());
					u.setPinCode(c.getPinCode());
					u.setRating(c.getRating());
					u.setWashesDone(c.getWashesDone());
					u.setNoOfCars(c.getCarsList().size());
					customerRepository.save(u);
			}
		List<CustomerOverview> list = customerRepository.findAll();
		if(list.isEmpty()) throw new InvalidAdminException("Empty List.");
		return list;
	}

	@Override
	public List<WasherOverview> viewWasherOverview() {
		washerRepository.deleteAll();
		washerRepository.resetAutoIncrementValue();
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
			
			for(Washer b : washerList) {
				WasherOverview u = new WasherOverview();
					u.setName(b.getFirstName()+" "+ b.getLastName());
					u.setPhoneNumber(b.getPhoneNumber());
					u.setRating(b.getRating());
					u.setAge(b.getAge());
					u.setWashesDone(b.getWashesDone());
					washerRepository.save(u);
				}
		List<WasherOverview> list = washerRepository.findAll();
		if(list.isEmpty()) throw new InvalidAdminException("Empty List.");
		return list;
	}

	@Override
	public List<HistoryOfBookings> viewBookingHistory() { 
		historyRepository.deleteAll();
		historyRepository.resetAutoIncrementValue();
		List<BookingDetails> list = null;
		ResponseEntity<List<BookingDetails>> response = rest.exchange(
			    "http://localhost:"+bPort+"/booking/viewBookingHistory",
			    HttpMethod.GET,
			    null,
			    new ParameterizedTypeReference<List<BookingDetails>>() {}
			);

			if (response.getStatusCode()==HttpStatus.OK) {
			    list =  response.getBody();
			}
		
		for(BookingDetails bd : list) {
			HistoryOfBookings h = new HistoryOfBookings(bd.getBookingDateAndTime(),bd.getBookingId(),
						bd.getCustomerName(),bd.getCustomerPhoneNumber(),bd.getCustomerRatingGiven(),bd.getWasherName(),
						bd.getWasherPhoneNumber(),bd.getWasherRatingGiven(),bd.getWashStatus());
			historyRepository.save(h); 
		}
		List<HistoryOfBookings> res = historyRepository.findAll();
		if(res.isEmpty()) throw new InvalidAdminException("No history of bookings. ");
		return res;
	}

	@Override
	public AdminDetails login(String username, String password) {
		AdminDetails ad = adminRepository.findByUsernameAndPassword(username, password).
				orElseThrow(()->new InvalidAdminException("Invalid Credentials!"));
		return ad;
	}

	
}
