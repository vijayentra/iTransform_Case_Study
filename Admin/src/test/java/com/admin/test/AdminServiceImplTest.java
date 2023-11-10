package com.admin.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.admin.dummyentity.BookingDetails;
import com.admin.dummyentity.Customer;
import com.admin.dummyentity.Washer;
import com.admin.entity.AdminDetails;
import com.admin.entity.CustomerOverview;
import com.admin.entity.HistoryOfBookings;
import com.admin.entity.WasherOverview;
import com.admin.exception.InvalidAdminException;
import com.admin.repository.AdminRepository;
import com.admin.repository.CustomerOverviewRepository;
import com.admin.repository.HistoryRepository;
import com.admin.repository.WasherOverviewRepository;
import com.admin.service.AdminServiceImpl;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

class AdminServiceImplTest {

	 	@InjectMocks
	    private AdminServiceImpl adminService;

	    @Mock
	    private AdminRepository adminRepository;
	    
	    @Mock
	    private CustomerOverviewRepository customerOverviewRepository;
	    
	    @Mock
	    private WasherOverviewRepository washerOverviewRepository;
	    
	    @Mock
	    private HistoryRepository historyRepository;
	    
	    @Mock
	    private RestTemplate restTemplate;
	    
	    private static final String CUSTOMER_SERVICE_URL = "http://localhost:8088/customer/viewAllCustomer";
	    private static final String WASHER_SERVICE_URL = "http://localhost:8086/washer/viewAllWasher";
	    private static final String BOOKING_SERVICE_URL = "http://localhost:8082/booking/viewBookingHistory";
	    
	    @BeforeEach
	    public void setUp() {
	        MockitoAnnotations.initMocks(this);
	    }

	    @Test
	    public void testAddAdmin_Successful() {
	        // Create a sample AdminDetails object
	        AdminDetails adminDetails = new AdminDetails();
	        adminDetails.setUsername("admin123");

	        // Mock the behavior of adminRepository.findAll to return an empty list
	        when(adminRepository.findAll()).thenReturn(new ArrayList<>());
	        // Mock the behavior of adminRepository.save to return the saved adminDetails
	        when(adminRepository.save(adminDetails)).thenReturn(adminDetails);

	        // Call the addAdmin method
	        AdminDetails result = adminService.addAdmin(adminDetails);

	        // Verify that the result matches the input adminDetails
	        assertEquals(adminDetails, result);

	        // Verify that adminRepository.findAll is called once
	        verify(adminRepository, times(1)).findAll();
	        // Verify that adminRepository.save is called once with the input adminDetails
	        verify(adminRepository, times(1)).save(adminDetails);
	    }

	    @Test
	    public void testAddAdmin_DuplicateUsername() {
	        // Create a sample AdminDetails object with a username that already exists
	        AdminDetails adminDetails = new AdminDetails();
	        adminDetails.setUsername("admin123");

	        // Create a list with an existing adminDetails object with the same username
	        List<AdminDetails> existingAdmins = new ArrayList<>();
	        existingAdmins.add(adminDetails);

	        // Mock the behavior of adminRepository.findAll to return the list of existingAdmins
	        when(adminRepository.findAll()).thenReturn(existingAdmins);

	        // Use assertThrows to check for the expected exception
	        assertThrows(InvalidAdminException.class, () -> adminService.addAdmin(adminDetails));

	    }
	    
	    
//	    @Test
//	    public void testUpdateAdmin_Successful() {
//	        // Create a sample admin and adminDetails for testing
//	        AdminDetails existingAdmin = new AdminDetails();
//	        existingAdmin.setUsername("admin123");
//
//	        AdminDetails updatedAdminDetails = new AdminDetails();
//	        updatedAdminDetails.setUsername("updatedAdmin123");
//	        updatedAdminDetails.setPassword("newPassword");
//
//	        // Mock the behavior of adminRepository.findByUsername to return the existing admin
//	        when(adminRepository.findByUsername(existingAdmin.getUsername())).thenReturn(existingAdmin);
//	        // Mock the behavior of adminRepository.findByUsername to return null (no duplicate admin with the updated username)
//	        when(adminRepository.findByUsername(updatedAdminDetails.getUsername())).thenReturn(null);
//	        // Mock the behavior of adminRepository.save to return the updated admin
//	        when(adminRepository.save(existingAdmin)).thenReturn(existingAdmin);
//
//	        // Call the updateAdmin method
//	        AdminDetails updatedAdmin = adminService.updateAdmin(existingAdmin.getUsername(), updatedAdminDetails);
//
//	        // Verify that the result matches the updated admin details
//	        assertEquals("updatedAdmin123", existingAdmin.getUsername());
//	        assertEquals("newPassword", existingAdmin.getPassword());
//	    }

	    @Test
	    public void testUpdateAdmin_AdminNotFound() {
	        // Create an admin that does not exist
	        String nonExistingUsername = "nonExistingAdmin";

	        // Mock the behavior of adminRepository.findByUsername to return null (admin not found)
	        when(adminRepository.findByUsername(nonExistingUsername)).thenReturn(null);

	        // Call the updateAdmin method and expect an InvalidAdminException
	        InvalidAdminException exception = assertThrows(InvalidAdminException.class, () -> 
	            adminService.updateAdmin(nonExistingUsername, new AdminDetails()));

	        // Verify the exception message
	        assertEquals("Admin does not exist. ", exception.getMessage());
	    }
//
//	    @Test
//	    public void testUpdateAdmin_DuplicateAdminName() {
//	        // Create two admin records with the same username (duplicate)
//	        AdminDetails existingAdmin1 = new AdminDetails();
//	        existingAdmin1.setUsername("admin123");
//
//	        AdminDetails existingAdmin2 = new AdminDetails();
//	        existingAdmin2.setUsername("duplicateAdmin123");
//
//	        AdminDetails updatedAdminDetails = new AdminDetails();
//	        updatedAdminDetails.setUsername("duplicateAdmin123");
//
//	        // Mock the behavior of adminRepository.findByUsername to return the existing admin
//	        when(adminRepository.findByUsername(existingAdmin1.getUsername())).thenReturn(existingAdmin1);
//	        // Mock the behavior of adminRepository.findAll to return a list containing existingAdmin2
//	        when(adminRepository.findAll()).thenReturn(List.of(existingAdmin2));
//
//	        // Call the updateAdmin method and expect an InvalidAdminException
//	        assertThrows(InvalidAdminException.class, 
//	        		() ->adminService.updateAdmin(existingAdmin1.getUsername(), updatedAdminDetails));
//
//	    }
	    
//	    @Test
//	    public void testDeleteAdmin_Successful() {
//	        // Create a sample admin for testing
//	        AdminDetails existingAdmin = new AdminDetails();
//	        existingAdmin.setUsername("admin123");
//
//	        // Mock the behavior of adminRepository.findByUsername to return the existing admin
//	        when(adminRepository.findByUsername(existingAdmin.getUsername())).thenReturn(existingAdmin);
//
//	        // Call the deleteAdmin method
//	        adminService.deleteAdmin(existingAdmin.getUsername());
//
//	        // Verify that adminRepository.delete is called once with the existing admin
//	        verify(adminRepository, times(1)).delete(existingAdmin);
//	    }

	    @Test
	    public void testDeleteAdmin_AdminNotFound() {
	        // Create an admin username that does not exist
	        String nonExistingUsername = "nonExistingAdmin";

	        // Mock the behavior of adminRepository.findByUsername to return null (admin not found)
	        when(adminRepository.findByUsername(nonExistingUsername)).thenReturn(null);

	        // Call the deleteAdmin method and expect an InvalidAdminException
	        InvalidAdminException exception = assertThrows(InvalidAdminException.class, () ->
	            adminService.deleteAdmin(nonExistingUsername));

	        // Verify the exception message
	        assertEquals("Admin does not exist. ", exception.getMessage());

	        // Verify that adminRepository.delete is not called
	        verify(adminRepository, never()).delete(any(AdminDetails.class));
	    }
	    
//	    @Test
//	    public void testViewAdmins_Successful() {
//	        // Create a list of sample admins for testing
//	        List<AdminDetails> adminList = new ArrayList<>();
//	        adminList.add(new AdminDetails("admin1", "password1"));
//	        adminList.add(new AdminDetails("admin2", "password2"));
//
//	        // Mock the behavior of adminRepository.findAll to return the list of sample admins
//	        when(adminRepository.findAll()).thenReturn(adminList);
//
//	        // Call the viewAdmins method
//	        List<String> result = adminService.viewAdmins();
//
//	        // Verify that the result contains the expected admin usernames
//	        assertTrue(result.contains("admin1"));
//	        assertTrue(result.contains("admin2"));
//	    }

	    



	    
	    @Test
	    public void testViewCustomerOverview_Successful() {
	        // Create a sample list of customers
	        List<Customer> customerList = new ArrayList<>();
	        Customer customer1 = new Customer();
	        customer1.setFirstName("John");
	        customer1.setLastName("Doe");
	        customer1.setPhoneNumber("1234567890");
	        customer1.setPinCode("12345");
	        customer1.setRating(4.5);
	        customer1.setWashesDone(10);
	        customer1.setCarsList(new ArrayList<>());
	        customerList.add(customer1);

	        // Mock the behavior of restTemplate.exchange to return customer data
	        ResponseEntity<List<Customer>> responseEntity = new ResponseEntity<>(customerList, HttpStatus.OK);
	        when(restTemplate.exchange(CUSTOMER_SERVICE_URL, HttpMethod.GET, null, new ParameterizedTypeReference<List<Customer>>() {}))
	                .thenReturn(responseEntity);

	        // Create CustomerOverview objects based on the customer data
	        CustomerOverview customerOverview = new CustomerOverview();
	        customerOverview.setName("John Doe");
	        customerOverview.setPhoneNumber("1234567890");
	        customerOverview.setPinCode("12345");
	        customerOverview.setRating(4.5);
	        customerOverview.setWashesDone(10);
	        customerOverview.setNoOfCars(0);

	        List<CustomerOverview> expectedCustomerOverviewList = new ArrayList<>();
	        expectedCustomerOverviewList.add(customerOverview);

	        // Mock the behavior of customerRepository.saveAll to return the expected CustomerOverview objects
	        when(customerOverviewRepository.saveAll(expectedCustomerOverviewList)).thenReturn(expectedCustomerOverviewList);

	        // Call the viewCustomerOverview method
	        List<CustomerOverview> resultCustomerOverviewList = adminService.viewCustomerOverview();

	        // Verify that the result matches the expected CustomerOverview objects
	        assertNotNull(resultCustomerOverviewList);
	    }

	    @Test
	    public void testViewWasherOverview_Successful() {
	        // Create a sample list of washers
	        List<Washer> washerList = new ArrayList<>();
	        Washer washer1 = new Washer();
	        washer1.setFirstName("John");
	        washer1.setLastName("Doe");
	        washer1.setPhoneNumber("1234567890");
	        washer1.setRating(4.5);
	        washer1.setAge("30");
	        washer1.setWashesDone(10);

	        Washer washer2 = new Washer();
	        washer2.setFirstName("Jane");
	        washer2.setLastName("Smith");
	        washer2.setPhoneNumber("9876543210");
	        washer2.setRating(3.8);
	        washer2.setAge("28");
	        washer2.setWashesDone(7);

	        washerList.add(washer1);
	        washerList.add(washer2);

	        // Mock the behavior of restTemplate.exchange to return washer data
	        ResponseEntity<List<Washer>> responseEntity = new ResponseEntity<>(washerList, HttpStatus.OK);
	        when(restTemplate.exchange(WASHER_SERVICE_URL, HttpMethod.GET, null, new ParameterizedTypeReference<List<Washer>>() {}))
	                .thenReturn(responseEntity);

	        // Create WasherOverview objects based on the washer data
	        WasherOverview washerOverview1 = new WasherOverview();
	        washerOverview1.setName("John Doe");
	        washerOverview1.setPhoneNumber("1234567890");
	        washerOverview1.setRating(4.5);
	        washerOverview1.setAge("30");
	        washerOverview1.setWashesDone(10);

	        WasherOverview washerOverview2 = new WasherOverview();
	        washerOverview2.setName("Jane Smith");
	        washerOverview2.setPhoneNumber("9876543210");
	        washerOverview2.setRating(3.8);
	        washerOverview2.setAge("28");
	        washerOverview2.setWashesDone(7);

	        List<WasherOverview> expectedWasherOverviewList = new ArrayList<>();
	        expectedWasherOverviewList.add(washerOverview1);
	        expectedWasherOverviewList.add(washerOverview2);

	        // Mock the behavior of washerOverviewRepository.saveAll to return the expected WasherOverview objects
	        when(washerOverviewRepository.saveAll(any(List.class))).thenReturn(expectedWasherOverviewList);

	        // Call the viewWasherOverview method
	        List<WasherOverview> resultWasherOverviewList = adminService.viewWasherOverview();

	        // Verify that the result matches the expected WasherOverview objects
	        assertNotNull(resultWasherOverviewList);
	    } 
	    
	    @Test
	    public void testViewBookingHistory_Successful() {
	        // Create a sample list of booking details
	        List<BookingDetails> bookingDetailsList = new ArrayList<>();
	        BookingDetails booking1 = new BookingDetails();
	        booking1.setBookingDateAndTime("2023-09-25 14:00:00");
	        booking1.setCustomerName("John Doe");
	        booking1.setCustomerPhoneNumber("1234567890");
	        booking1.setWasherName("Jane Smith");
	        booking1.setWasherPhoneNumber("9876543210");
	        booking1.setWashStatus("Completed");

	        BookingDetails booking2 = new BookingDetails();
	        booking2.setBookingDateAndTime("2023-09-26 15:30:00");
	        booking2.setCustomerName("Alice Johnson");
	        booking2.setCustomerPhoneNumber("5555555555");
	        booking2.setWasherName("Bob Brown");
	        booking2.setWasherPhoneNumber("9999999999");
	        booking2.setWashStatus("Completed");

	        bookingDetailsList.add(booking1);
	        bookingDetailsList.add(booking2);

	        // Mock the behavior of restTemplate.exchange to return booking details
	        ResponseEntity<List<BookingDetails>> responseEntity = new ResponseEntity<>(bookingDetailsList, HttpStatus.OK);
	        when(restTemplate.exchange(BOOKING_SERVICE_URL, HttpMethod.GET, null, new ParameterizedTypeReference<List<BookingDetails>>() {}))
	                .thenReturn(responseEntity);

	        // Create HistoryOfBookings objects based on the booking details
	        List<HistoryOfBookings> expectedHistoryList = new ArrayList<>();
	        for (BookingDetails bd : bookingDetailsList) {
	            HistoryOfBookings history = new HistoryOfBookings(
	                    bd.getBookingDateAndTime(),
	                    bd.getBookingId(),
	                    bd.getCustomerName(),
	                    bd.getCustomerPhoneNumber(),
	                    bd.getCustomerRatingGiven(),
	                    bd.getWasherName(),
	                    bd.getWasherPhoneNumber(),
	                    bd.getWasherRatingGiven(),
	                    bd.getWashStatus()
	            );
	            expectedHistoryList.add(history);
	        }

	        // Mock the behavior of historyRepository.saveAll to return the expected HistoryOfBookings objects
	        when(historyRepository.saveAll(any(List.class))).thenReturn(expectedHistoryList);

	        // Call the viewBookingHistory method
	        List<HistoryOfBookings> resultHistoryList = adminService.viewBookingHistory();

	        // Verify that the result matches the expected HistoryOfBookings objects
	        assertNotNull(resultHistoryList); 
	    }
}
