package com.customer.service;

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

import com.customer.dummyentity.BookingDetails;
import com.customer.entity.CarDetails;
import com.customer.entity.Customer;
import com.customer.exception.InvalidDetailsException;
import com.customer.repository.CustomerRepository;

@Service
public class CustomerServiceImpl implements CustomerService {
	@Autowired
	private CustomerRepository customerRepository;
	@Autowired 
	private RestTemplate rest;
	
	private static final int bPort = 8082;
	
	@Override
	public Customer addCustomer(Customer customer) throws InvalidDetailsException{
		List<Customer> list = customerRepository.findAll();
		for(Customer c : list) {
			if(c.getPhoneNumber().equals(customer.getPhoneNumber())) {
				throw new InvalidDetailsException(
						"Customer already exists for this phone number. ");
			}
		}
		String msg = "";
	
		String nameRegex = "^[A-Z]{1}[a-z]{0,19}$";
		Pattern nameP = Pattern.compile(nameRegex);
		Matcher m1 = nameP.matcher(customer.getFirstName());
		if(!m1.matches()) msg = msg + "Invalid first name.\n";
		Matcher m2 = nameP.matcher(customer.getLastName());
		if(!m2.matches()) msg = msg + "Invalid last name.\n";
		
		String numberRegex = "^[6-9]{1}[0-9]{9}$";
		Pattern numberP = Pattern.compile(numberRegex);
		Matcher m3 = numberP.matcher(customer.getPhoneNumber());
		if(!m3.matches()) msg = msg+"Invalid phone number.\n";
		
		String pinRegex = "^400[0-9]{3}$";
		Pattern pinP = Pattern.compile(pinRegex);
		Matcher m4 = pinP.matcher(customer.getPinCode());
		if(!m4.matches()) msg = msg+ "Invalid pin-code.\n";
		
		String passRegex = "^(?=.*[A-Z])(?=.*[0-9])(?=.*[^A-Za-z0-9]).{8,}$";
		Pattern passP = Pattern.compile(passRegex);
		Matcher m8 = passP.matcher(customer.getPassword());
		if(!m8.matches())msg = msg + "Password did not match the requirements.\n";
		
		if(msg.equals("")) {
			return customerRepository.save(customer);
		}
		throw new InvalidDetailsException(msg);
	}

	@Override
	public CarDetails addCarDetails(String phoneNumber, CarDetails carDetails) throws InvalidDetailsException{
			Customer customer = customerRepository.findByPhoneNumber(phoneNumber).
					orElseThrow(()-> new InvalidDetailsException("Customer does not exist."));
	        
			int num=0;
			for(CarDetails car : customer.getCarsList()) {
				if(carDetails.getNumberPlate().equals(car.getNumberPlate())) {
				num+=1;
				}
			}
			if(num>0) {
				throw new InvalidDetailsException("Car already exists.");
			}
		
			String msg = "";
			String numRegex = "^[A-Z]{2}[0-9]{2}[A-Z]{2}[0-9]{4}$";
			Pattern carP = Pattern.compile(numRegex);
			Matcher m5 = carP.matcher(carDetails.getNumberPlate());
			if(!m5.matches()) msg = msg + "Check car number plate: "+carDetails.getNumberPlate()+".\n";
			
			String yearRegex = "^20(0[8-9]|1[0-9])|20[0-2][0-3]$";
			Pattern yearP = Pattern.compile(yearRegex);
			Matcher m6 = yearP.matcher(carDetails.getMfgYear());
			if(!m6.matches()) msg = msg + "Invalid manufacturing year.\n";
			
			String lenRegex = "^[2-5]{1}[0-9]{3}";
			Pattern lenP = Pattern.compile(lenRegex);
			Matcher m7 = lenP.matcher(carDetails.getLengthInMM());
			if(!m7.matches()) msg = msg + "Car length not within wash limit.\n";
		
			if(msg.equals("")) {
				customer.getCarsList().add(carDetails);
				customerRepository.save(customer);
				return carDetails;
			}
			throw new InvalidDetailsException(msg);
	}
	
	@Override
	public Customer updateCustomer(String phoneNumber,Customer customer) throws InvalidDetailsException{
			Customer c = customerRepository.findByPhoneNumber(phoneNumber).
					orElseThrow(()->new InvalidDetailsException("Customer does not exist. ")); 
			int i=0;
			for(Customer cus : customerRepository.findAll()) {
				if(!(cus.getPhoneNumber().equals(c.getPhoneNumber()))) {
					if(customer.getPhoneNumber().equals(cus.getPhoneNumber())) {
						throw new InvalidDetailsException("Customer already exists with this phone number. ");
				}
				}
			}
			
			String msg = "";
			
			String nameRegex = "^[A-Z]{1}[a-z]{0,19}$";
			Pattern nameP = Pattern.compile(nameRegex);
			Matcher m1 = nameP.matcher(customer.getFirstName());
			if(!m1.matches()) msg = msg + "Invalid first name.\n";
			Matcher m2 = nameP.matcher(customer.getLastName());
			if(!m2.matches()) msg = msg + "Invalid last name.\n";
			
			String numberRegex = "^[6-9]{1}[0-9]{9}$";
			Pattern numberP = Pattern.compile(numberRegex);
			Matcher m3 = numberP.matcher(customer.getPhoneNumber());
			if(!m3.matches()) msg = msg+"Invalid phone number.\n";
			
			String pinRegex = "^400[0-9]{3}$";
			Pattern pinP = Pattern.compile(pinRegex);
			Matcher m4 = pinP.matcher(customer.getPinCode());
			if(!m4.matches()) msg = msg+ "Invalid pin-code.\n";
			
			String passRegex = "^(?=.*[A-Z])(?=.*[0-9])(?=.*[^A-Za-z0-9]).{8,}$";
			Pattern passP = Pattern.compile(passRegex);
			Matcher m8 = passP.matcher(customer.getPassword());
			if(!m8.matches())msg = msg + "Password did not match the requirements.\n";
			
			if(msg.equals("")) {
			
			//UPDATING THE UPDATED DETAILS TO THE BOOKING DETAILS HISTORY
					List<BookingDetails> list = null;
					ResponseEntity<List<BookingDetails>> response = rest.exchange(
						    "http://localhost:"+bPort+"/booking/viewCustomerHistory/"+phoneNumber,
						    HttpMethod.GET,
						    null,
						    new ParameterizedTypeReference<List<BookingDetails>>() {}
						);

						if (response.getStatusCode()==HttpStatus.OK) {
						    list =  response.getBody();
						    for(BookingDetails bd : list) {
									String name = customer.getFirstName()+" "+ customer.getLastName();
									String url = "http://localhost:"+bPort+"/booking/updateCustomerDetails/{oldPhoneNumber}/{customerName}/{phoneNumber}";

									// Set up the URL parameters
									Map<String, String> urlParams = new HashMap<>();  
									urlParams.put("oldPhoneNumber", phoneNumber);
									urlParams.put("customerName", name);
									urlParams.put("phoneNumber", customer.getPhoneNumber());

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
					
					c.setPhoneNumber(customer.getPhoneNumber());
					c.setFirstName(customer.getFirstName());
					c.setLastName(customer.getLastName());
					c.setPassword(customer.getPassword());
					c.setPinCode(customer.getPinCode());
					return customerRepository.save(c);
			}
			throw new InvalidDetailsException(msg);
	}

	@Override
	public CarDetails updateCarDetails(String phoneNumber, String plateNumber, CarDetails carDetails)
							throws InvalidDetailsException{
		
		Customer cus = customerRepository.findByPhoneNumber(phoneNumber).
				orElseThrow(()->new InvalidDetailsException("Customer does not exist. "));
		
		String msg = "";
		String numRegex = "^[A-Z]{2}[0-9]{2}[A-Z]{2}[0-9]{4}$";
		Pattern carP = Pattern.compile(numRegex);
		Matcher m5 = carP.matcher(carDetails.getNumberPlate());
		if(!m5.matches()) msg = msg + "Check car number plate: "+carDetails.getNumberPlate()+".\n";
		
		String yearRegex = "^20(0[8-9]|1[0-9])|20[0-2][0-3]$";
		Pattern yearP = Pattern.compile(yearRegex);
		Matcher m6 = yearP.matcher(carDetails.getMfgYear());
		if(!m6.matches()) msg = msg + "Invalid manufacturing year.\n";
		
		String lenRegex = "^[2-5]{1}[0-9]{3}";
		Pattern lenP = Pattern.compile(lenRegex);
		Matcher m7 = lenP.matcher(carDetails.getLengthInMM());
		if(!m7.matches()) msg = msg + "Car length not within wash limit.\n";
	
		if(msg.equals("")) {
			for(CarDetails car : cus.getCarsList()) {
					if(plateNumber.equals(car.getNumberPlate())) {
						car.setBrand(carDetails.getBrand());
						car.setColour(carDetails.getColour());
						car.setModel(carDetails.getModel());
						car.setLengthInMM(carDetails.getLengthInMM());
						car.setMfgYear(carDetails.getMfgYear());
						car.setNumberPlate(carDetails.getNumberPlate());
						int num=0;
						for(CarDetails cc : cus.getCarsList()) {
							if(carDetails.getNumberPlate().equals(cc.getNumberPlate())) {
							num+=1;
							}
						}
						if(num>1) {
							throw new InvalidDetailsException("Car already exists.");
						}
						customerRepository.save(cus);
						return carDetails;
				}	
			}
			throw new InvalidDetailsException("Car does not exist. Check the car number.");
		}
		throw new InvalidDetailsException(msg);
	}

	@Override
	public void deleteCustomer(String phoneNumber) throws InvalidDetailsException{
		List<Customer> list = customerRepository.findAll();
		for(Customer c: list) {
			if(c.getPhoneNumber().equals(phoneNumber)) {
				customerRepository.deleteByPhoneNumber(phoneNumber);
				return;
			}
		}
		throw new InvalidDetailsException("Customer does not exist. ");
	}

	@Override
	public void deleteCarDetails(String phoneNumber, String numberPlate) {
		List<BookingDetails> list2 = null;
		ResponseEntity<List<BookingDetails>> response = rest.exchange(
			    "http://localhost:"+bPort+"/booking/viewCustomerHistory/"+phoneNumber,
			    HttpMethod.GET,
			    null,
			    new ParameterizedTypeReference<List<BookingDetails>>() {}
			);

			if (response.getStatusCode()==HttpStatus.OK) {
				list2 =  response.getBody();
				if(!list2.isEmpty()) {
					for(BookingDetails bd : list2) {
						String id = bd.getBookingId();
						if(id.substring(17,27).equals(numberPlate)&&(bd.getWashStatus().equals("ACCEPTED")||bd.getWashStatus().equals("REQUESTED"))) {
							throw new InvalidDetailsException("Unfinished booking exist for this car. Can't be deleted. ");
						}
					}
				}
			}
		
		List<Customer> list = customerRepository.findAll();
		for(Customer c: list) {
			if(c.getPhoneNumber().equals(phoneNumber)) {
				for(CarDetails car : c.getCarsList()) {
					if(car.getNumberPlate().equals(numberPlate)) {
						List<CarDetails> l = c.getCarsList();
						l.remove(car);
						c.setCarsList(l);
						customerRepository.save(c);
						return;
					}
				}
				throw new InvalidDetailsException("Car does not exist. ");
			}
		}
		throw new InvalidDetailsException("Customer does not exist. ");
	}

	@Override
	public Customer viewCustomer(String phoneNumber) {
		Customer cus = customerRepository.findByPhoneNumber(phoneNumber).
				orElseThrow(()-> new InvalidDetailsException("Customer does not exist. "));
		return cus;
	}

	@Override
	public List<Customer> viewAllCustomer() {
		return customerRepository.findAll();
	}

	@Override
	public CarDetails viewCarDetails(String phoneNumber, String numberPlate) throws InvalidDetailsException{
		Customer cus = customerRepository.findByPhoneNumber(phoneNumber).
				orElseThrow(()-> new InvalidDetailsException("Customer does not exist. "));
		for(CarDetails car : cus.getCarsList()) {
			if(car.getNumberPlate().equals(numberPlate)) {
				return car;
			}
		}
		throw new InvalidDetailsException("Car does not exist. ");
	}

	@Override
	public List<CarDetails> viewAllCarDetails(String phoneNumber) {
		Customer cus = customerRepository.findByPhoneNumber(phoneNumber).
				orElseThrow(()-> new InvalidDetailsException("Customer does not exist. "));
		return cus.getCarsList();
	}

	@Override
	public Customer updateCustomerRating(String phoneNumber, int rating) {
		Customer cus = customerRepository.findByPhoneNumber(phoneNumber).
				orElseThrow(()-> new InvalidDetailsException("Customer does not exist. "));
		double r = 0;
		r = (cus.getRating()*cus.getWashesDone()+rating)/(cus.getWashesDone()+1);
		r = Math.round(r * Math.pow(10, 1)) / Math.pow(10, 1);
		cus.setRating(r);
		cus.setWashesDone(cus.getWashesDone()+1);
		return customerRepository.save(cus);
	}

	@Override
	public Customer login(String phoneNumber, String password) {
		Customer c = customerRepository.findByPhoneNumberAndPassword(phoneNumber, password).
				orElseThrow(()->new InvalidDetailsException("Invalid Credentials. "));
		return c;
	}

	
}
