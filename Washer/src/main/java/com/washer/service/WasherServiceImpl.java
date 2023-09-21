package com.washer.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.washer.dummyentity.*;
import com.washer.entity.Washer;
import com.washer.exception.InvalidDetailsException;
import com.washer.repository.WasherRepository;

@Service
public class WasherServiceImpl implements WasherService{
	
	@Autowired
	private WasherRepository washerRepository;
	@Autowired
	private RestTemplate rest;
	
	private static final int bPort = 8082;

	@Override
	public Washer addWasher(Washer washer) {
		List<Washer> list = washerRepository.findAll();
		for(Washer w : list) {
			if(w.getPhoneNumber().equals(washer.getPhoneNumber())) {
				throw new InvalidDetailsException(
						"Washer already exists for this phone number. ");
			}
		}
		
		String msg = "";
		
		String nameRegex = "^[A-Z]{1}[a-z]{0,19}$";
		Pattern nameP = Pattern.compile(nameRegex);
		Matcher m1 = nameP.matcher(washer.getFirstName());
		if(!m1.matches()) msg = msg + "Invalid first name.\n";
		Matcher m2 = nameP.matcher(washer.getLastName());
		if(!m2.matches()) msg = msg + "Invalid last name.\n";
		
		String numberRegex = "^[6-9]{1}[0-9]{9}$";
		Pattern numberP = Pattern.compile(numberRegex);
		Matcher m3 = numberP.matcher(washer.getPhoneNumber());
		if(!m3.matches()) msg = msg+"Invalid phone number.\n";
		
		String passRegex = "^(?=.*[A-Z])(?=.*[0-9])(?=.*[^A-Za-z0-9]).{8,}$";
		Pattern passP = Pattern.compile(passRegex);
		Matcher m8 = passP.matcher(washer.getPassword());
		if(!m8.matches())msg = msg + "Password did not match the requirements.\n";
		int a = 0;
		try {
			a = Integer.parseInt(washer.getAge());
			if(a<18) msg = msg + "Age can't be below 18. ";
		}catch(NumberFormatException e) {
			msg = msg + "Invalid age. ";
		}
		if(msg.equals("")) {
			return washerRepository.save(washer);
		}
		throw new InvalidDetailsException(msg);
	}

	@Override
	public Washer updateWasher(String phoneNumber, Washer washer) 
	{
		Washer w = washerRepository.findByPhoneNumber(phoneNumber).
				orElseThrow(()-> new InvalidDetailsException("Washer does not exist."));
		String msg = "";
		
		String nameRegex = "^[A-Z]{1}[a-z]{0,19}$";
		Pattern nameP = Pattern.compile(nameRegex);
		Matcher m1 = nameP.matcher(washer.getFirstName());
		if(!m1.matches()) msg = msg + "Invalid first name.\n";
		Matcher m2 = nameP.matcher(washer.getLastName());
		if(!m2.matches()) msg = msg + "Invalid last name.\n";
		
		String numberRegex = "^[6-9]{1}[0-9]{9}$";
		Pattern numberP = Pattern.compile(numberRegex);
		Matcher m3 = numberP.matcher(washer.getPhoneNumber());
		if(!m3.matches()) msg = msg+"Invalid phone number.\n";
		
		String passRegex = "^(?=.*[A-Z])(?=.*[0-9])(?=.*[^A-Za-z0-9]).{8,}$";
		Pattern passP = Pattern.compile(passRegex);
		Matcher m8 = passP.matcher(washer.getPassword());
		if(!m8.matches())msg = msg + "Password did not match the requirements.\n";
		int a = 0;
		try {
			a = Integer.parseInt(washer.getAge());
			if(a<18) msg = msg + "Age can't be below 18. ";
		}catch(NumberFormatException e) {
			msg = msg + "Invalid age. ";
		}
		if(msg.equals("")) {
			/////updating the change into the history
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
				if(bd.getWasherPhoneNumber().equals(phoneNumber)) {
					String name = washer.getFirstName()+" "+ washer.getLastName();
					// Define the URL with the appropriate values for oldPhoneNumber, customerName, and phoneNumber
					String url = "http://localhost:"+bPort+"/booking/updateWasherDetails/{oldPhoneNumber}/{washerName}/{phoneNumber}";

					// Set up the URL parameters
					Map<String, String> urlParams = new HashMap<>();
					urlParams.put("oldPhoneNumber", phoneNumber);
					urlParams.put("washerName", name);
					urlParams.put("phoneNumber", washer.getPhoneNumber());

					// Send a PUT request
					ResponseEntity<String> responseEntity = rest.exchange(
					    url,
					    HttpMethod.PUT,
					    null,
					    String.class,
					    urlParams
					);
				}
			}
			///////
			w.setFirstName(washer.getFirstName());
			w.setLastName(washer.getLastName());
			w.setPassword(washer.getPassword());
			w.setPhoneNumber(washer.getPhoneNumber());
			w.setAge(washer.getAge());
			return washerRepository.save(w);
		}
		throw new InvalidDetailsException(msg);
	}

	@Override
	public void deleteWasher(String phoneNumber) {
		// TODO Auto-generated method stub
		Washer w = washerRepository.findByPhoneNumber(phoneNumber).
				orElseThrow(()-> new InvalidDetailsException("Washer does not exist."));
		washerRepository.delete(w);
	}

	@Override
	public Washer viewWasher(String phoneNumber) {
		Washer w = washerRepository.findByPhoneNumber(phoneNumber).
				orElseThrow(()-> new InvalidDetailsException("Washer does not exist."));
		return w;
	}

	@Override
	public List<Washer> viewAllWasher() {
		List<Washer> list = washerRepository.findAll();
		return list;
	}

	@Override
	public Washer updateWasherRating(String phoneNumber, int rating) {
		Washer w = washerRepository.findByPhoneNumber(phoneNumber).
				orElseThrow(()-> new InvalidDetailsException("Washer does not exist."));
		double r = 0;
		r = (w.getRating()*w.getWashesDone()+rating)/(w.getWashesDone()+1);
		r = Math.round(r * Math.pow(10, 1)) / Math.pow(10, 1);
		w.setRating(r);
		w.setWashesDone(w.getWashesDone()+1);
		return washerRepository.save(w);
	}

}
