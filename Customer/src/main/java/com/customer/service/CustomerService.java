package com.customer.service;



import java.util.List;

import com.customer.entity.CarDetails;
import com.customer.entity.Customer;

public interface CustomerService {
		
		public Customer updateCustomer(String PhoneNumber, Customer customer);
		public CarDetails updateCarDetails(String phoneNumber,String plateNumber, CarDetails carDetails);
		
		public Customer addCustomer(Customer customer);
		public CarDetails addCarDetails(String phoneNumber, CarDetails carDetails);
		
		public void deleteCustomer(String phoneNumber);
		public void deleteCarDetails(String phoneNumber, String numberPlate);
		
		public Customer login(String phoneNumber, String password);
		public Customer viewCustomer(String phoneNumber);
		public List<Customer> viewAllCustomer();
		
		public CarDetails viewCarDetails(String phoneNumber, String numberPlate);
		public List<CarDetails> viewAllCarDetails(String phoneNumber);
		
		public Customer updateCustomerRating(String phoneNumber, int rating);
}
