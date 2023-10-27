package com.customer.test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.customer.dummyentity.BookingDetails;
import com.customer.entity.CarDetails;
import com.customer.entity.Customer;
import com.customer.exception.InvalidDetailsException;
import com.customer.repository.CustomerRepository;
import com.customer.service.CustomerServiceImpl;

@SpringBootTest
class CustomerServiceImplTest {

	@InjectMocks
    private CustomerServiceImpl customerService;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private RestTemplate restTemplate;


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testUpdateCustomer_ExistingCustomer() throws InvalidDetailsException {
        // Create a mock Customer object in the repository
        Customer existingCustomer = new Customer();
        existingCustomer.setPhoneNumber("1234567890");
        existingCustomer.setFirstName("John");
        existingCustomer.setLastName("Doe");
        
        // Mock the behavior of customerRepository.findByPhoneNumber
        when(customerRepository.findByPhoneNumber("1234567890")).thenReturn(Optional.of(existingCustomer));

        // Create a new Customer with updated information
        Customer updatedCustomer = new Customer();
        updatedCustomer.setPhoneNumber("1234567890");
        updatedCustomer.setFirstName("Jane");
        updatedCustomer.setLastName("Smith");

    }

    @Test
    public void testUpdateCustomer_NonExistingCustomer() {
        // Mock the behavior of customerRepository.findByPhoneNumber to return an empty Optional
        when(customerRepository.findByPhoneNumber("1234567890")).thenReturn(Optional.empty());

        // Create a new Customer with updated information
        Customer updatedCustomer = new Customer();
        updatedCustomer.setPhoneNumber("1234567890");
        updatedCustomer.setFirstName("Jane");
        updatedCustomer.setLastName("Smith");

        // Call the updateCustomer method and expect an InvalidDetailsException
        assertThrows(InvalidDetailsException.class, () -> {
            customerService.updateCustomer("1234567890", updatedCustomer);
        });
    }

    @Test
    void addCustomer_validCustomer(){
    	 Customer customer = new Customer(); 
         List<Customer> existingCustomers = new ArrayList<>(); 
         when(customerRepository.findAll()).thenReturn(existingCustomers);
         when(customerRepository.save(customer)).thenReturn(customer); 

         Customer addedCustomer = customerService.addCustomer(customer);
         assertNotNull(addedCustomer);
    }

    @Test
    void addCustomer_Invalid() throws InvalidDetailsException {
    	Customer customer = new Customer(); // Create a valid customer
        customer.setPhoneNumber("existingPhoneNumber"); // Set the phone number to an existing one
        List<Customer> existingCustomers = new ArrayList<>();
        existingCustomers.add(customer); // Simulate an existing customer with the same phone number
        // Mock any necessary behavior for dependencies
        when(customerRepository.findAll()).thenReturn(existingCustomers);

        assertThrows(InvalidDetailsException.class, () -> {
            customerService.addCustomer(customer);
        });
    }
    
    @Test
    public void testUpdateCarDetails_UpdateExistingCar() throws InvalidDetailsException {
        // Create a mock Customer object with a car
        Customer existingCustomer = new Customer();
        existingCustomer.setPhoneNumber("1234567890");
        CarDetails existingCar = new CarDetails();
        existingCar.setNumberPlate("ABC123");
        existingCustomer.getCarsList().add(existingCar);

        // Mock the behavior of customerRepository.findByPhoneNumber
        when(customerRepository.findByPhoneNumber("1234567890")).thenReturn(Optional.of(existingCustomer));

        // Create a new CarDetails with updated information
        CarDetails updatedCar = new CarDetails();
        updatedCar.setNumberPlate("ABC123");
        updatedCar.setBrand("Toyota");
        updatedCar.setColour("Red");

        // Call the updateCarDetails method
        CarDetails result = customerService.updateCarDetails("1234567890", "ABC123", updatedCar);

        // Verify that the car details are updated
        assertEquals("Toyota", result.getBrand());
        assertEquals("Red", result.getColour());
    }

    @Test
    public void testUpdateCarDetails_CustomerDoesNotExist() {
        // Mock the behavior of customerRepository.findByPhoneNumber to return an empty Optional
        when(customerRepository.findByPhoneNumber("1234567890")).thenReturn(Optional.empty());

        // Create a new CarDetails with updated information
        CarDetails updatedCar = new CarDetails();
        updatedCar.setNumberPlate("ABC123");
        updatedCar.setBrand("Toyota");
        updatedCar.setColour("Red");

        // Call the updateCarDetails method and expect an InvalidDetailsException
        assertThrows(InvalidDetailsException.class, () -> {
            customerService.updateCarDetails("1234567890", "ABC123", updatedCar);
        });
    }

    @Test
    public void testUpdateCarDetails_CarDoesNotExist() {
        // Create a mock Customer object without a matching car
        Customer existingCustomer = new Customer();
        existingCustomer.setPhoneNumber("1234567890");

        // Mock the behavior of customerRepository.findByPhoneNumber
        when(customerRepository.findByPhoneNumber("1234567890")).thenReturn(Optional.of(existingCustomer));

        // Create a new CarDetails with updated information
        CarDetails updatedCar = new CarDetails();
        updatedCar.setNumberPlate("ABC123");
        updatedCar.setBrand("Toyota");
        updatedCar.setColour("Red");

        // Call the updateCarDetails method and expect an InvalidDetailsException
        assertThrows(InvalidDetailsException.class, () -> {
            customerService.updateCarDetails("1234567890", "ABC123", updatedCar);
        });
    }

    @Test
    public void testUpdateCarDetails_DuplicateNumberPlate() {
        // Create a mock Customer object with multiple cars having the same number plate
        Customer existingCustomer = new Customer();
        existingCustomer.setPhoneNumber("1234567890");
        CarDetails car1 = new CarDetails();
        car1.setNumberPlate("ABC123");
        CarDetails car2 = new CarDetails();
        car2.setNumberPlate("ABC123");
        existingCustomer.getCarsList().add(car1);
        existingCustomer.getCarsList().add(car2);

        // Mock the behavior of customerRepository.findByPhoneNumber
        when(customerRepository.findByPhoneNumber("1234567890")).thenReturn(Optional.of(existingCustomer));

        // Create a new CarDetails with updated information
        CarDetails updatedCar = new CarDetails();
        updatedCar.setNumberPlate("ABC123");
        updatedCar.setBrand("Toyota");
        updatedCar.setColour("Red");

        // Call the updateCarDetails method and expect an InvalidDetailsException
        assertThrows(InvalidDetailsException.class, () -> {
            customerService.updateCarDetails("1234567890", "ABC123", updatedCar);
        });
    }
    
    @Test
    public void testAddCarDetails_AddNewCar() throws InvalidDetailsException {
        // Create a mock Customer object
        Customer existingCustomer = new Customer();
        existingCustomer.setPhoneNumber("1234567890");

        // Mock the behavior of customerRepository.findByPhoneNumber
        when(customerRepository.findByPhoneNumber("1234567890")).thenReturn(Optional.of(existingCustomer));

        // Create new CarDetails to add
        CarDetails newCar = new CarDetails();
        newCar.setNumberPlate("ABC123");
        newCar.setBrand("Toyota");

        // Call the addCarDetails method
        CarDetails result = customerService.addCarDetails("1234567890", newCar);

        // Verify that the car is added and the method returns the added car
        assertEquals("ABC123", result.getNumberPlate());
        assertEquals("Toyota", result.getBrand());
    }

    @Test
    public void testAddCarDetails_CustomerDoesNotExist() {
        // Mock the behavior of customerRepository.findByPhoneNumber to return an empty Optional
        when(customerRepository.findByPhoneNumber("1234567890")).thenReturn(Optional.empty());

        // Create new CarDetails to add
        CarDetails newCar = new CarDetails();
        newCar.setNumberPlate("ABC123");
        newCar.setBrand("Toyota");

        // Call the addCarDetails method and expect an InvalidDetailsException
        assertThrows(InvalidDetailsException.class, () -> {
            customerService.addCarDetails("1234567890", newCar);
        });
    }

    @Test
    public void testAddCarDetails_DuplicateNumberPlate() {
        // Create a mock Customer object with a car having the same number plate
        Customer existingCustomer = new Customer();
        existingCustomer.setPhoneNumber("1234567890");
        CarDetails existingCar = new CarDetails();
        existingCar.setNumberPlate("ABC123");
        existingCustomer.getCarsList().add(existingCar);

        // Mock the behavior of customerRepository.findByPhoneNumber
        when(customerRepository.findByPhoneNumber("1234567890")).thenReturn(Optional.of(existingCustomer));

        // Create new CarDetails with the same number plate to add
        CarDetails newCar = new CarDetails();
        newCar.setNumberPlate("ABC123");
        newCar.setBrand("Toyota");

        // Call the addCarDetails method and expect an InvalidDetailsException
        assertThrows(InvalidDetailsException.class, () -> {
            customerService.addCarDetails("1234567890", newCar);
        });
    }
    
    @Test
    public void testDeleteCustomer_ExistingCustomer() throws InvalidDetailsException {
        // Create a list of mock customers
        List<Customer> customers = new ArrayList<>();
        Customer existingCustomer = new Customer();
        existingCustomer.setPhoneNumber("1234567890");
        customers.add(existingCustomer);

        // Mock the behavior of customerRepository.findAll
        when(customerRepository.findAll()).thenReturn(customers);

        // Call the deleteCustomer method
        customerService.deleteCustomer("1234567890");

        // Verify that the customer is deleted by checking deleteByPhoneNumber method
        verify(customerRepository, times(1)).deleteByPhoneNumber("1234567890");
    }

    @Test
    public void testDeleteCustomer_CustomerDoesNotExist() {
        // Create an empty list of mock customers
        List<Customer> customers = new ArrayList<>();

        // Mock the behavior of customerRepository.findAll
        when(customerRepository.findAll()).thenReturn(customers);

        // Call the deleteCustomer method and expect an InvalidDetailsException
        assertThrows(InvalidDetailsException.class, () -> {
            customerService.deleteCustomer("1234567890");
        });

        verify(customerRepository, never()).deleteByPhoneNumber("");
        
    }
    
    @Test
    public void testDeleteCarDetails_ExistingCar() throws InvalidDetailsException {
        // Create a mock Customer object with a car
        Customer existingCustomer = new Customer();
        existingCustomer.setPhoneNumber("1234567890");
        CarDetails existingCar = new CarDetails();
        existingCar.setNumberPlate("ABC123");
        existingCustomer.getCarsList().add(existingCar);

        // Mock the behavior of customerRepository.findAll
        List<Customer> customers = new ArrayList<>();
        customers.add(existingCustomer);
        when(customerRepository.findAll()).thenReturn(customers);

        // Call the deleteCarDetails method
        customerService.deleteCarDetails("1234567890", "ABC123");

        assertEquals(0, existingCustomer.getCarsList().size());
        verify(customerRepository, times(1)).save(existingCustomer);
    }

    @Test
    public void testDeleteCarDetails_CustomerDoesNotExist() {
        // Mock the behavior of customerRepository.findAll to return an empty list
        when(customerRepository.findAll()).thenReturn(new ArrayList<>());

        // Call the deleteCarDetails method and expect an InvalidDetailsException
        assertThrows(InvalidDetailsException.class, () -> {
            customerService.deleteCarDetails("1234567890", "ABC123");
        });
    }

    @Test
    public void testDeleteCarDetails_CarDoesNotExist() {
        // Create a mock Customer object without the car to delete
        Customer existingCustomer = new Customer();
        existingCustomer.setPhoneNumber("1234567890");
        CarDetails existingCar = new CarDetails();
        existingCar.setNumberPlate("XYZ789"); // Different number plate
        existingCustomer.getCarsList().add(existingCar);

        // Mock the behavior of customerRepository.findAll
        List<Customer> customers = new ArrayList<>();
        customers.add(existingCustomer);
        when(customerRepository.findAll()).thenReturn(customers);

        // Call the deleteCarDetails method and expect an InvalidDetailsException
        assertThrows(InvalidDetailsException.class, () -> {
            customerService.deleteCarDetails("1234567890", "ABC123");
        });
    }
    
    @Test
    public void testViewCustomer_CustomerExists() {
        // Create a mock Customer object
        Customer existingCustomer = new Customer();
        existingCustomer.setPhoneNumber("1234567890");

        // Mock the behavior of customerRepository.findByPhoneNumber
        when(customerRepository.findByPhoneNumber("1234567890")).thenReturn(Optional.of(existingCustomer));

        // Call the viewCustomer method
        Customer result = customerService.viewCustomer("1234567890");

        assertEquals("1234567890", result.getPhoneNumber());
    }

    @Test
    public void testViewCustomer_CustomerDoesNotExist() {
        // Mock the behavior of customerRepository.findByPhoneNumber to return an empty Optional
        when(customerRepository.findByPhoneNumber("1234567890")).thenReturn(Optional.empty());

        // Call the viewCustomer method and expect an InvalidDetailsException
        assertThrows(InvalidDetailsException.class, () -> {
            customerService.viewCustomer("1234567890");
        });
    }
    
    @Test
    public void testViewAllCustomer_CustomersExist() {
        // Create a list of mock customers
        List<Customer> customers = new ArrayList<>();
        Customer customer1 = new Customer();
        customer1.setPhoneNumber("1234567890");
        Customer customer2 = new Customer();
        customer2.setPhoneNumber("9876543210");
        customers.add(customer1);
        customers.add(customer2);

        // Mock the behavior of customerRepository.findAll
        when(customerRepository.findAll()).thenReturn(customers);

        // Call the viewAllCustomer method
        List<Customer> result = customerService.viewAllCustomer();

        assertEquals(2, result.size());
    }

    @Test
    public void testViewAllCustomer_NoCustomers() {
        // Mock the behavior of customerRepository.findAll to return an empty list
        when(customerRepository.findAll()).thenReturn(new ArrayList<>());

        // Call the viewAllCustomer method and verify that it returns an empty list
        List<Customer> result = customerService.viewAllCustomer();
        assertEquals(0, result.size());
    }
    
    @Test
    public void testViewCarDetails_CarExists() throws InvalidDetailsException {
        // Create a mock Customer object with a car
        Customer existingCustomer = new Customer();
        existingCustomer.setPhoneNumber("1234567890");
        CarDetails existingCar = new CarDetails();
        existingCar.setNumberPlate("ABC123");
        existingCustomer.getCarsList().add(existingCar);

        // Mock the behavior of customerRepository.findByPhoneNumber
        when(customerRepository.findByPhoneNumber("1234567890")).thenReturn(Optional.of(existingCustomer));

        // Call the viewCarDetails method
        CarDetails result = customerService.viewCarDetails("1234567890", "ABC123");

        assertEquals("ABC123", result.getNumberPlate());
    }

    @Test
    public void testViewCarDetails_CustomerDoesNotExist() {
        // Mock the behavior of customerRepository.findByPhoneNumber to return an empty Optional
        when(customerRepository.findByPhoneNumber("1234567890")).thenReturn(Optional.empty());

        // Call the viewCarDetails method and expect an InvalidDetailsException
        assertThrows(InvalidDetailsException.class, () -> {
            customerService.viewCarDetails("1234567890", "ABC123");
        });
    }

    @Test
    public void testViewCarDetails_CarDoesNotExist() {
        // Create a mock Customer object without the car to view
        Customer existingCustomer = new Customer();
        existingCustomer.setPhoneNumber("1234567890");
        CarDetails existingCar = new CarDetails();
        existingCar.setNumberPlate("XYZ789"); // Different number plate
        existingCustomer.getCarsList().add(existingCar);

        // Mock the behavior of customerRepository.findByPhoneNumber
        when(customerRepository.findByPhoneNumber("1234567890")).thenReturn(Optional.of(existingCustomer));

        // Call the viewCarDetails method and expect an InvalidDetailsException
        assertThrows(InvalidDetailsException.class, () -> {
            customerService.viewCarDetails("1234567890", "ABC123");
        });
    }
    
    @Test
    public void testViewAllCarDetails_CarsExist() {
        // Create a mock Customer object with multiple cars
        Customer existingCustomer = new Customer();
        existingCustomer.setPhoneNumber("1234567890");
        CarDetails car1 = new CarDetails();
        car1.setNumberPlate("ABC123");
        CarDetails car2 = new CarDetails();
        car2.setNumberPlate("XYZ789");
        existingCustomer.getCarsList().add(car1);
        existingCustomer.getCarsList().add(car2);

        // Mock the behavior of customerRepository.findByPhoneNumber
        when(customerRepository.findByPhoneNumber("1234567890")).thenReturn(java.util.Optional.of(existingCustomer));

        // Call the viewAllCarDetails method
        List<CarDetails> result = customerService.viewAllCarDetails("1234567890");

        assertEquals(2, result.size());
    }

    @Test
    public void testViewAllCarDetails_NoCars() {
        // Create a mock Customer object with no cars
        Customer existingCustomer = new Customer();
        existingCustomer.setPhoneNumber("1234567890");

        // Mock the behavior of customerRepository.findByPhoneNumber
        when(customerRepository.findByPhoneNumber("1234567890")).thenReturn(java.util.Optional.of(existingCustomer));

        // Call the viewAllCarDetails method and verify that it returns an empty list
        List<CarDetails> result = customerService.viewAllCarDetails("1234567890");
        assertEquals(0, result.size());
    }

    @Test
    public void testViewAllCarDetails_CustomerDoesNotExist() {
        // Mock the behavior of customerRepository.findByPhoneNumber to return an empty Optional
        when(customerRepository.findByPhoneNumber("1234567890")).thenReturn(java.util.Optional.empty());

        // Call the viewAllCarDetails method and expect an InvalidDetailsException
        assertThrows(InvalidDetailsException.class, () -> {
            customerService.viewAllCarDetails("1234567890");
        });
    }
    
    @Test
    public void testUpdateCustomerRating_CustomerExists() {
        // Create a mock Customer object
        Customer existingCustomer = new Customer();
        existingCustomer.setPhoneNumber("1234567890");
        existingCustomer.setRating(4.0); // Initial rating
        existingCustomer.setWashesDone(5); // Initial number of washes done

        // Mock the behavior of customerRepository.findByPhoneNumber
        when(customerRepository.findByPhoneNumber("1234567890")).thenReturn(Optional.of(existingCustomer));

        // Call the updateCustomerRating method to update the rating
        Customer result = customerService.updateCustomerRating("1234567890", 5);

    }

    @Test
    public void testUpdateCustomerRating_CustomerDoesNotExist() {
        // Mock the behavior of customerRepository.findByPhoneNumber to return an empty Optional
        when(customerRepository.findByPhoneNumber("1234567890")).thenReturn(Optional.empty());

        // Call the updateCustomerRating method and expect an InvalidDetailsException
        assertThrows(InvalidDetailsException.class, () -> {
            customerService.updateCustomerRating("1234567890", 5); 
        });
    }
    

}
    

    
   
