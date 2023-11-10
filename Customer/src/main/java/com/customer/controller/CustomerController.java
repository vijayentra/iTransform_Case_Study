package com.customer.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.customer.dummyentity.loginRequest;
import com.customer.entity.CarDetails;
import com.customer.entity.Customer;
import com.customer.exception.InvalidDetailsException;
import com.customer.service.CustomerService;

@RestController
@RequestMapping("/customer")
@CrossOrigin("*")
public class CustomerController {

	@Autowired
	private CustomerService customerService;
	
	//LOGIN
	@GetMapping("/loginCustomer/{username}/{password}")
	public ResponseEntity<?> loginCustomer(@PathVariable String username, @PathVariable String password){
		Customer cus = null;
		try {
			cus = customerService.login(username,password);
		}catch(InvalidDetailsException e) {
			System.out.println(e.getMessage());
			return new ResponseEntity<>(e.getMessage()+ "Please try again!", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(cus,HttpStatus.OK); 
	}
	
	//ADD A CUSTOMER
	@PostMapping("/add")
	public ResponseEntity<?> addCustomer(@RequestBody Customer customer){
		Customer cus = null;
		try {
		cus = customerService.addCustomer(customer);
		}catch(InvalidDetailsException e) {
			System.out.println(e.getMessage());
			return new ResponseEntity<>(e.getMessage()+ "Please try again!", HttpStatus.BAD_REQUEST);
		}catch(NullPointerException e) {
			return new ResponseEntity<>("Fields can't be empty. Please try again!", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(cus,HttpStatus.OK); 
	}
	
	//ADD A CAR DETAIL TO A CUSTOMER
	@PostMapping("/addCar/{phoneNumber}")
	public ResponseEntity<?> addCarDetails(@PathVariable String phoneNumber,@RequestBody CarDetails carDetails ){
		CarDetails car = null;
		try {
			car = customerService.addCarDetails(phoneNumber, carDetails);
		}catch(InvalidDetailsException e) {
			return new ResponseEntity<>(e.getMessage()+ "Please try again!", HttpStatus.BAD_REQUEST);
		}catch(NullPointerException e) {
			return new ResponseEntity<>("Fields can't be empty. Please try again!", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(car,HttpStatus.OK); 
	}
	
	//UPDATE DETAILS OF A CUSTOMER
	@PutMapping("/updateCustomer/{phoneNumber}")
	public ResponseEntity<?> updateCustomer(@PathVariable String phoneNumber,@RequestBody Customer customer){
		Customer cus=null;
		try {
		cus = customerService.updateCustomer(phoneNumber,customer);
		}catch(InvalidDetailsException  e) {
			return new ResponseEntity<>(e.getMessage()+ "Please try again!", HttpStatus.BAD_REQUEST);
		}catch(NullPointerException e) {
			return new ResponseEntity<>("Fields can't be empty. Please try again!", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(cus,HttpStatus.OK);
	}
	
	//UPDATE A CAR'S DETAILS OF A CUSTOMER
	@PutMapping("/updateCarDetails/{phoneNumber}/{plateNumber}")
	public ResponseEntity<?> updateCarDetails(@PathVariable String phoneNumber,@PathVariable String plateNumber,
			@RequestBody CarDetails  carDetails){
		CarDetails car = null;
		try {
			car = customerService.updateCarDetails(phoneNumber, plateNumber, carDetails);
		}catch(InvalidDetailsException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}catch(NullPointerException e) {
			return new ResponseEntity<>("Fields can't be empty. Please try again!", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(car,HttpStatus.OK);
	}
	
	//DELETE A CAR'S DETAILS FROM A CUSTOMER
	@DeleteMapping("/deleteCarDetails/{phoneNumber}/{plateNumber}")
	public ResponseEntity<?> deleteCarDetails(@PathVariable String phoneNumber,@PathVariable String plateNumber){
		try {
			customerService.deleteCarDetails(phoneNumber, plateNumber);
		}catch(InvalidDetailsException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>("Car detail deleted successfully. ",HttpStatus.OK);
	}
	
	//DELETE A CUSTOMER'S DETAILS
	@DeleteMapping("/deleteCustomer/{phoneNumber}")
	public ResponseEntity<?> deleteCustomer(@PathVariable String phoneNumber){
		try {
			customerService.deleteCustomer(phoneNumber);
		}catch(InvalidDetailsException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>("Customer detail deleted successfully. ",HttpStatus.OK);
	}
	
	//VIEW DETAILS OF A CUSTOMER
	@GetMapping("/viewCustomer/{phoneNumber}")
	public ResponseEntity<Object> viewCustomer(@PathVariable String phoneNumber){
		Customer cus = null;
		try {
			cus = customerService.viewCustomer(phoneNumber);
		}catch(InvalidDetailsException e) {
			return new ResponseEntity<>(e.getMessage() + "Please try again!", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(cus,HttpStatus.OK);
	}
	
	//VIEW DETAILS OF ALL THE CUSTOMERS
	@GetMapping("/viewAllCustomer")
	public ResponseEntity<?> viewAllCustomer(){
		List<Customer> list = null;
		try {
			list = customerService.viewAllCustomer();
		}catch(InvalidDetailsException e) {
			return new ResponseEntity<>(e.getMessage() + "Please try again!", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(list,HttpStatus.OK);
	}
	
	//VIEW A CAR'S DETAILS OF A CUSTOMER
	@GetMapping("/viewCarDetails/{phoneNumber}/{plateNumber}")
	public ResponseEntity<?> viewCarDetails(@PathVariable String phoneNumber,@PathVariable String plateNumber){
		CarDetails car = null;
		try {
			car = customerService.viewCarDetails(phoneNumber, plateNumber);
		}catch(InvalidDetailsException e) {
			return new ResponseEntity<>(e.getMessage()+ "Please try Again!", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(car,HttpStatus.OK);
	}
	
	//VIEW ALL CARS DETAILS OF  CUSTOMER
	@GetMapping("/viewAllCarDetails/{phoneNumber}")
	public ResponseEntity<?> viewAllCarDetails(@PathVariable String phoneNumber){
		List<CarDetails> list = null;
		try {
			list = customerService.viewAllCarDetails(phoneNumber);
		}catch(InvalidDetailsException e) {
			return new ResponseEntity<>(e.getMessage() + "Please try again!", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(list,HttpStatus.OK);
	}
	
	@PutMapping("/updateCustomerRating/{phoneNumber}/{rating}")
	public ResponseEntity<?> updateCustomerRating(@PathVariable String phoneNumber,@PathVariable int rating){
		Customer c = null;
		try {
			c = customerService.updateCustomerRating(phoneNumber, rating);
		}catch(InvalidDetailsException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(c,HttpStatus.OK);
	}
}
