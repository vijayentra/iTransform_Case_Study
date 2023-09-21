package com.admin.service;

import java.util.ArrayList;
import java.util.List;

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
		return adminRepository.save(adminDetails);
	}

	@Override
	public AdminDetails updateAdmin(String username, AdminDetails adminDetails) {
		AdminDetails ad = adminRepository.findByUsername(username);
		if(ad==null) throw new InvalidAdminException("Admin does not exist. ");
		for(AdminDetails a : adminRepository.findAll()) {
			if(a.getUsername().equals(adminDetails.getUsername())) {
				throw new InvalidAdminException("Admin already exists with the name. ");
			}
		}
		ad.setUsername(adminDetails.getUsername());
		ad.setPassword(adminDetails.getPassword());
		return adminRepository.save(ad);
	}

	@Override
	public void deleteAdmin(String userName) {
		AdminDetails ad = adminRepository.findByUsername(userName);
		if(ad==null) throw new InvalidAdminException("Admin does not exist. ");
		adminRepository.delete(ad);
	}

	@Override
	public List<String> viewAdmins() {
		List<AdminDetails> list = adminRepository.findAll();
		if(list==null) throw new InvalidAdminException("No admin registered. ");
		List<String> res = new ArrayList<>();
		for(AdminDetails ad : list) {
			res.add(ad.getUsername());
		}
		return res;
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
		if(list==null) throw new InvalidAdminException("Empty List.");
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
		if(list==null) throw new InvalidAdminException("Empty List.");
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
		if(res==null) throw new InvalidAdminException("No history of bookings. ");
		return res;
	}

	
}
