package com.customer.test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.customer.controller.CustomerController;
import com.customer.entity.CarDetails;
import com.customer.entity.Customer;
import com.customer.exception.InvalidDetailsException;
import com.customer.service.CustomerService;

class CustomerControllerTest {


    @InjectMocks
    @Autowired
    private CustomerController customerController;

    @Mock
    private CustomerService customerService;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void addCustomer_ValidCustomer_ReturnsCustomer() throws InvalidDetailsException {
        Customer customer = new Customer();
        customer.setPhoneNumber("9234567890");
        customer.setPassword("Pass1234!");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customer.setPinCode("400601");

        when(customerService.addCustomer(customer)).thenReturn(customer);

        ResponseEntity<?> responseEntity = customerController.addCustomer(customer);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(customer, responseEntity.getBody());
        verify(customerService, times(1)).addCustomer(ArgumentMatchers.eq(customer));
    }

    @Test
    void addCustomer_InvalidDetails_ThrowsException() throws InvalidDetailsException {
        Customer invalidCustomer = new Customer(); // Invalid customer with missing required fields

        when(customerService.addCustomer(invalidCustomer))
                .thenThrow(new InvalidDetailsException("Invalid customer details. "));

        ResponseEntity<?> responseEntity = customerController.addCustomer(invalidCustomer);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("Invalid customer details. Please try again!", responseEntity.getBody());
        verify(customerService, times(1)).addCustomer(ArgumentMatchers.eq(invalidCustomer));
    }

    @Test
    void addCarDetails_ValidCarDetails_ReturnsCarDetails() throws InvalidDetailsException {
        String phoneNumber = "9234567890";
        CarDetails carDetails = new CarDetails();
        carDetails.setNumberPlate("TN47BV2222");
        carDetails.setBrand("Toyota");

        when(customerService.addCarDetails(phoneNumber, carDetails)).thenReturn(carDetails);

        ResponseEntity<?> responseEntity = customerController.addCarDetails(phoneNumber, carDetails);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(carDetails, responseEntity.getBody());
        verify(customerService, times(1)).addCarDetails(ArgumentMatchers.eq(phoneNumber), ArgumentMatchers.eq(carDetails));
    }

    @Test
    void addCarDetails_InvalidCarDetails_ThrowsException() throws InvalidDetailsException {
        String phoneNumber = "9234567890";
        CarDetails invalidCarDetails = new CarDetails(); // Invalid car details with missing required fields

        when(customerService.addCarDetails(phoneNumber, invalidCarDetails))
                .thenThrow(new InvalidDetailsException("Invalid car details. "));

        ResponseEntity<?> responseEntity = customerController.addCarDetails(phoneNumber, invalidCarDetails);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("Invalid car details. Please try again!", responseEntity.getBody());
        verify(customerService, times(1)).addCarDetails(ArgumentMatchers.eq(phoneNumber), ArgumentMatchers.eq(invalidCarDetails));
    }

    @Test
    void updateCustomer_ValidCustomer_ReturnsUpdatedCustomer() throws InvalidDetailsException {
        String phoneNumber = "9234567890";
        Customer customer = new Customer();
        customer.setPhoneNumber(phoneNumber);
        customer.setPassword("Pass1234!");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customer.setPinCode("400601");

        when(customerService.updateCustomer(phoneNumber, customer)).thenReturn(customer);

        ResponseEntity<?> responseEntity = customerController.updateCustomer(phoneNumber, customer);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(customer, responseEntity.getBody());
        verify(customerService, times(1)).updateCustomer(ArgumentMatchers.eq(phoneNumber), ArgumentMatchers.eq(customer));
    }

    @Test
    void updateCustomer_InvalidDetails_ThrowsException() throws InvalidDetailsException {
        String phoneNumber = "9234567890";
        Customer invalidCustomer = new Customer(); // Invalid customer with missing required fields

        when(customerService.updateCustomer(phoneNumber, invalidCustomer))
                .thenThrow(new InvalidDetailsException("Invalid customer details. "));

        ResponseEntity<?> responseEntity = customerController.updateCustomer(phoneNumber, invalidCustomer);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("Invalid customer details. Please try again!", responseEntity.getBody());
        verify(customerService, times(1)).updateCustomer(ArgumentMatchers.eq(phoneNumber), ArgumentMatchers.eq(invalidCustomer));
    }

    @Test
    void updateCarDetails_ValidCarDetails_ReturnsUpdatedCarDetails() throws InvalidDetailsException {
        String phoneNumber = "9234567890";
        String plateNumber = "ABC1234";
        CarDetails carDetails = new CarDetails();
        carDetails.setNumberPlate(plateNumber);
        carDetails.setBrand("Toyota");

        when(customerService.updateCarDetails(phoneNumber, plateNumber, carDetails)).thenReturn(carDetails);

        ResponseEntity<?> responseEntity = customerController.updateCarDetails(phoneNumber, plateNumber, carDetails);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(carDetails, responseEntity.getBody());
        verify(customerService, times(1)).updateCarDetails(ArgumentMatchers.eq(phoneNumber), ArgumentMatchers.eq(plateNumber), ArgumentMatchers.eq(carDetails));
    }

    @Test
    void updateCarDetails_InvalidCarDetails_ThrowsException() throws InvalidDetailsException {
        String phoneNumber = "9234567890";
        String plateNumber = "ABC1234";
        CarDetails invalidCarDetails = new CarDetails(); // Invalid car details with missing required fields

        when(customerService.updateCarDetails(phoneNumber, plateNumber, invalidCarDetails))
                .thenThrow(new InvalidDetailsException("Car does not exist. "));

        ResponseEntity<?> responseEntity = customerController.updateCarDetails(phoneNumber, plateNumber, invalidCarDetails);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("Car does not exist. ", responseEntity.getBody());
        verify(customerService, times(1)).updateCarDetails(ArgumentMatchers.eq(phoneNumber), ArgumentMatchers.eq(plateNumber), ArgumentMatchers.eq(invalidCarDetails));
    }

    @Test
    void deleteCarDetails_ValidDetails_ReturnsSuccessMessage() throws InvalidDetailsException {
        String phoneNumber = "9234567890";
        String plateNumber = "ABC1234";

        ResponseEntity<?> responseEntity = customerController.deleteCarDetails(phoneNumber, plateNumber);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Car detail deleted successfully. ", responseEntity.getBody());
        verify(customerService, times(1)).deleteCarDetails(ArgumentMatchers.eq(phoneNumber), ArgumentMatchers.eq(plateNumber));
    }

    @Test
    void deleteCustomer_ValidDetails_ReturnsSuccessMessage() throws InvalidDetailsException {
        String phoneNumber = "9234567890";

        ResponseEntity<?> responseEntity = customerController.deleteCustomer(phoneNumber);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Customer detail deleted successfully. ", responseEntity.getBody());
        verify(customerService, times(1)).deleteCustomer(ArgumentMatchers.eq(phoneNumber));
    }

    @Test
    void viewCustomer_ValidDetails_ReturnsCustomer() throws InvalidDetailsException {
        String phoneNumber = "9234567890";
        Customer customer = new Customer();
        customer.setPhoneNumber(phoneNumber);
        customer.setFirstName("John");
        customer.setLastName("Doe");

        when(customerService.viewCustomer(phoneNumber)).thenReturn(customer);

        ResponseEntity<?> responseEntity = customerController.viewCustomer(phoneNumber);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(customer, responseEntity.getBody());
        verify(customerService, times(1)).viewCustomer(ArgumentMatchers.eq(phoneNumber));
    }

    @Test
    void viewCustomer_InvalidDetails_ThrowsException() throws InvalidDetailsException {
        String phoneNumber = "9234567890";

        when(customerService.viewCustomer(phoneNumber))
                .thenThrow(new InvalidDetailsException("Customer does not exist. "));

        ResponseEntity<?> responseEntity = customerController.viewCustomer(phoneNumber);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("Customer does not exist. Please try again!", responseEntity.getBody());
        verify(customerService, times(1)).viewCustomer(ArgumentMatchers.eq(phoneNumber));
    }

    @Test
    void viewAllCarDetails_ValidDetails_ReturnsCarDetailsList() throws InvalidDetailsException {
        String phoneNumber = "9234567890";
        List<CarDetails> carDetailsList = new ArrayList<>();
        carDetailsList.add(new CarDetails("Skoda", "slavia","black","2022","4100","TN47BV2222"));
        carDetailsList.add(new CarDetails("Skoda", "slavia","black","2022","4100","TN47BV4444"));

        when(customerService.viewAllCarDetails(phoneNumber)).thenReturn(carDetailsList);

        ResponseEntity<?> responseEntity = customerController.viewAllCarDetails(phoneNumber);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(carDetailsList, responseEntity.getBody());
        verify(customerService, times(1)).viewAllCarDetails(ArgumentMatchers.eq(phoneNumber));
    }

    @Test
    void viewAllCarDetails_InvalidDetails_ThrowsException() throws InvalidDetailsException {
        String phoneNumber = "9234567890";

        when(customerService.viewAllCarDetails(phoneNumber))
                .thenThrow(new InvalidDetailsException("Customer does not exist. "));

        ResponseEntity<?> responseEntity = customerController.viewAllCarDetails(phoneNumber);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("Customer does not exist. Please try again!", responseEntity.getBody());
        verify(customerService, times(1)).viewAllCarDetails(ArgumentMatchers.eq(phoneNumber));
    }

    @Test
    void updateCustomerRating_ValidDetails_ReturnsUpdatedCustomer() throws InvalidDetailsException {
        String phoneNumber = "9234567890";
        int rating = 4;
        Customer customer = new Customer();
        customer.setPhoneNumber(phoneNumber);
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customer.setRating(rating);

        when(customerService.updateCustomerRating(phoneNumber, rating)).thenReturn(customer);

        ResponseEntity<?> responseEntity = customerController.updateCustomerRating(phoneNumber, rating);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(customer, responseEntity.getBody());
        verify(customerService, times(1)).updateCustomerRating(ArgumentMatchers.eq(phoneNumber), ArgumentMatchers.eq(rating));
    }

    @Test
    void updateCustomerRating_InvalidDetails_ThrowsException() throws InvalidDetailsException {
        String phoneNumber = "8903570055";
        int rating = 6; // Invalid rating value

        when(customerService.updateCustomerRating(phoneNumber, rating))
                .thenThrow(new InvalidDetailsException("Invalid rating value. "));

        ResponseEntity<?> responseEntity = customerController.updateCustomerRating(phoneNumber, rating);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("Invalid rating value. ", responseEntity.getBody());
        verify(customerService, times(1)).updateCustomerRating(ArgumentMatchers.eq(phoneNumber), ArgumentMatchers.eq(rating));
    }
    
    @Test
    void viewAllCustomer_ValidDetails_ReturnsCustomerList() throws InvalidDetailsException {
        List<Customer> customerList = new ArrayList<>();
        Customer c = new Customer();
        c.setPhoneNumber("8903570055");
        c.setFirstName("Vijay");
        c.setLastName("Kumar");
        c.setPassword("Vijay123!");
        c.setPinCode("400601");
        
        Customer c2 = new Customer();
        c2.setPhoneNumber("8903570066");
        c2.setFirstName("Vijay");
        c2.setLastName("Kumar");
        c2.setPassword("Vijay123!");
        c2.setPinCode("400601");
      
        customerList.add(c);
        customerList.add(c2);

        when(customerService.viewAllCustomer()).thenReturn(customerList);

        ResponseEntity<?> responseEntity = customerController.viewAllCustomer();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(customerList, responseEntity.getBody());
        verify(customerService, times(1)).viewAllCustomer();
    }

    @Test
    void viewAllCustomer_InvalidDetails_ThrowsException() throws InvalidDetailsException {
        when(customerService.viewAllCustomer())
                .thenThrow(new InvalidDetailsException("Invalid customer details. "));

        ResponseEntity<?> responseEntity = customerController.viewAllCustomer();

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("Invalid customer details. Please try again!", responseEntity.getBody());
        verify(customerService, times(1)).viewAllCustomer();
    }

    @Test
    void viewCarDetails_ValidDetails_ReturnsCarDetails() throws InvalidDetailsException {
        String phoneNumber = "1234567890";
        String plateNumber = "TN47BV4444";
        CarDetails carDetails = new CarDetails("Skoda", "slavia","black","2022","4100","TN47BV4444");

        when(customerService.viewCarDetails(phoneNumber, plateNumber)).thenReturn(carDetails);

        ResponseEntity<?> responseEntity = customerController.viewCarDetails(phoneNumber, plateNumber);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(carDetails, responseEntity.getBody());
        verify(customerService, times(1)).viewCarDetails(ArgumentMatchers.eq(phoneNumber), ArgumentMatchers.eq(plateNumber));
    }

    @Test
    void viewCarDetails_InvalidDetails_ThrowsException() throws InvalidDetailsException {
        String phoneNumber = "1234567890";
        String plateNumber = "ABC1234";

        when(customerService.viewCarDetails(phoneNumber, plateNumber))
                .thenThrow(new InvalidDetailsException("Invalid car details. "));

        ResponseEntity<?> responseEntity = customerController.viewCarDetails(phoneNumber, plateNumber);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("Invalid car details. Please try Again!", responseEntity.getBody());
        verify(customerService, times(1)).viewCarDetails(ArgumentMatchers.eq(phoneNumber), ArgumentMatchers.eq(plateNumber));
    }
}


