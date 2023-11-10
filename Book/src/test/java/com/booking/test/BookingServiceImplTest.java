package com.booking.test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.booking.dummyentity.CarDetails;
import com.booking.dummyentity.Customer;
import com.booking.dummyentity.Washer;
import com.booking.entity.AddOns;
import com.booking.entity.BookingDetails;
import com.booking.entity.InvoiceDetails;
import com.booking.entity.Packages;
import com.booking.exception.BookingException;
import com.booking.repository.AddOnsRepository;
import com.booking.repository.BookingRepository;
import com.booking.repository.PackagesRepository;
import com.booking.service.BookingServiceImpl;

class BookingServiceImplTest {

	@Mock
    private BookingRepository bookingRepository;

    @Mock
    private PackagesRepository packagesRepository;

    @Mock
    private AddOnsRepository addonsRepository;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private BookingServiceImpl bookingService;
    
    private static final int cPort = 8088;
	private static final int wPort = 8086;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    
    @Test
    public void testCancelWash_Success() {
        // Mock the behavior of the bookingRepository to return a BookingDetails object
        BookingDetails mockBooking = new BookingDetails();
        when(bookingRepository.findByBookingId(anyString())).thenReturn(mockBooking);

        // Call the cancelWash method
        String bookingId = "12345"; 
        BookingDetails result = bookingService.cancelWash(bookingId);

        // Assertions
        assertNotNull(result);
        assertEquals("CANCELLED", result.getWashStatus());
        assertTrue(result.getBookingId().endsWith("*"));
    }

    @Test
    public void testCancelWash_BookingNotFound() {
        // Mock the behavior of the bookingRepository to return null when booking is not found
        when(bookingRepository.findByBookingId(anyString())).thenReturn(null);

        // Call the cancelWash method with a non-existent booking ID
        String bookingId = "nonExistentBooking"; 
        
        BookingException exception = assertThrows(BookingException.class,
                () -> bookingService.cancelWash(bookingId)); 

        assertEquals("Booking does not exist. ", exception.getMessage());
    }
    
    @Test
    public void testRespondWash_Accept_Success() {
        // Mock the behavior of the bookingRepository to return a BookingDetails object
        BookingDetails mockBooking = new BookingDetails();
        when(bookingRepository.findByBookingId(anyString())).thenReturn(mockBooking);

        // Call the respondWash method with "ACCEPT" response
        String bookingId = "12345"; 
        BookingDetails result = bookingService.respondWash(bookingId, "ACCEPT");

        // Assertions
        assertNotNull(result);
        assertEquals("ACCEPTED", result.getWashStatus());
    }

    @Test
    public void testRespondWash_Decline_Success() {
        // Mock the behavior of the bookingRepository to return a BookingDetails object
        BookingDetails mockBooking = new BookingDetails();
        when(bookingRepository.findByBookingId(anyString())).thenReturn(mockBooking);

        // Call the respondWash method with "DECLINE" response
        String bookingId = "12345"; 
        BookingDetails result = bookingService.respondWash(bookingId, "DECLINE");

        // Assertions
        assertNotNull(result);
        assertEquals("DECLINED", result.getWashStatus());
        assertTrue(result.getBookingId().endsWith("#"));
    }

    @Test
    public void testRespondWash_BookingNotFound() {
        // Mock the behavior of the bookingRepository to return null when booking is not found
        when(bookingRepository.findByBookingId(anyString())).thenReturn(null);

        // Call the respondWash method with a non-existent booking ID
        String bookingId = "nonExistentBooking"; 
        
        BookingException exception = assertThrows(BookingException.class,
                () -> bookingService.respondWash(bookingId, "ACCEPT"));

        assertEquals("Booking does not exist. ", exception.getMessage());
    }
    

    @Test
    public void testWashComplete_BookingNotFound() {
        // Mock the behavior of bookingRepository to return null when booking is not found
        when(bookingRepository.findByBookingId(anyString())).thenReturn(null);

        // Call the washComplete method with a non-existent booking ID
        String bookingId = "nonExistentBooking"; 
        int rating = 4; 
        
        BookingException exception = assertThrows(BookingException.class,
                () -> bookingService.washComplete(bookingId, rating));

        assertEquals("Booking does not exist. ", exception.getMessage());
    }
    
    @Test
    public void testWashComplete_AlreadyCompleted() {
        BookingDetails bookingDetails = new BookingDetails();
        bookingDetails.setWashStatus("COMPLETED");
        String bookingId = "yourBookingId";

        // Mock the behavior of bookingRepository.findByBookingId
        when(bookingRepository.findByBookingId(bookingId)).thenReturn(bookingDetails);

        BookingException exception = assertThrows(BookingException.class,
                () -> bookingService.washComplete(bookingId, 5));

        assertEquals("Wash Completed already. ", exception.getMessage());
    }

    @Test
    public void testRateWasher_RatingGivenAlready() {
        BookingDetails bookingDetails = new BookingDetails();
        bookingDetails.setWasherRatingGiven(4); 
        String bookingId = "yourBookingId";
        int rating = 5; 

        // Mock the behavior of bookingRepository.findByBookingId
        when(bookingRepository.findByBookingId(bookingId)).thenReturn(bookingDetails);

        BookingException exception = assertThrows(BookingException.class,
                () -> bookingService.rateWasher(bookingId, rating));

        assertEquals("Rating given already. ", exception.getMessage());
    }

    @Test
    public void testRateWasher_NotCompletedStatus() {
        BookingDetails bookingDetails = new BookingDetails();
        bookingDetails.setWashStatus("ACCEPTED"); 
        String bookingId = "yourBookingId";
        int rating = 5; 

        // Mock the behavior of bookingRepository.findByBookingId
        when(bookingRepository.findByBookingId(bookingId)).thenReturn(bookingDetails);

        BookingException exception = assertThrows(BookingException.class,
                () -> bookingService.rateWasher(bookingId, rating));

        assertEquals("Rate after the car wash is completed. ", exception.getMessage());
    }
    
    @Test
    public void testViewBookingDetails_Success() {
        // Mock the behavior of bookingRepository to return a BookingDetails object
        BookingDetails mockBooking = new BookingDetails();
        when(bookingRepository.findByBookingId(anyString())).thenReturn(mockBooking);

        // Call the viewBookingDetails method
        String bookingId = "12345"; 
        BookingDetails result = bookingService.viewBookingDetails(bookingId);

        // Assertions
        assertNotNull(result);
        assertSame(mockBooking, result);
    }

    @Test
    public void testViewBookingDetails_BookingNotFound() {
        // Mock the behavior of bookingRepository to return null, indicating no booking found
        when(bookingRepository.findByBookingId(anyString())).thenReturn(null);

        // Call the viewBookingDetails method with a non-existent booking ID
        String bookingId = "nonExistentBooking"; 
        
        BookingException exception = assertThrows(BookingException.class,
                () -> bookingService.viewBookingDetails(bookingId));

        assertEquals("Booking does not exist.", exception.getMessage());
    }

    @Test
    public void testViewInvoiceDetails_Success() {
        // Mock the behavior of bookingRepository to return a BookingDetails object with an associated invoice
        BookingDetails mockBooking = new BookingDetails();
        InvoiceDetails mockInvoice = new InvoiceDetails();
        mockBooking.setInvoice(mockInvoice);
        when(bookingRepository.findByInvoice_BookingId(anyString())).thenReturn(mockBooking);

        // Call the viewInvoiceDetails method
        String bookingId = "12345"; 
        InvoiceDetails result = bookingService.viewInvoiceDetails(bookingId);

        // Assertions
        assertNotNull(result);
        assertSame(mockInvoice, result);
    }

    @Test
    public void testViewInvoiceDetails_InvoiceNotFound() {
        // Mock the behavior of bookingRepository to return null, indicating no associated invoice
        when(bookingRepository.findByInvoice_BookingId(anyString())).thenReturn(null);

        assertThrows(BookingException.class, () -> bookingService.viewInvoiceDetails("booking123"));
    }
    
    @Test
    public void testViewCustomerHistory_Success() {
        // Mock the behavior of bookingRepository to return a list of BookingDetails
        List<BookingDetails> mockHistory = new ArrayList<>();
        // Add some booking details to the list
        mockHistory.add(new BookingDetails());
        mockHistory.add(new BookingDetails());
        when(bookingRepository.findByCustomerPhoneNumber(anyString())).thenReturn(mockHistory);

        // Call the viewCustomerHistory method
        String phoneNumber = "1234567890"; 
        List<BookingDetails> result = bookingService.viewCustomerHistory(phoneNumber);

        // Assertions
        assertNotNull(result);
        assertEquals(mockHistory.size(), result.size());
    }

    @Test
    public void testViewCustomerHistory_NoHistory() {
        // Mock the behavior of bookingRepository to return an empty list, indicating no history
        when(bookingRepository.findByCustomerPhoneNumber(anyString())).thenReturn(new ArrayList<>());

        // Call the viewCustomerHistory method for a customer with no history
        String phoneNumber = "1234567890"; 
        BookingException exception = assertThrows(BookingException.class,
                () -> bookingService.viewCustomerHistory(phoneNumber));

        assertEquals("Customer has no wash history. ", exception.getMessage());
    }
    
    @Test
    public void testViewWasherHistory_Success() {
        // Mock the behavior of bookingRepository to return a list of BookingDetails
        List<BookingDetails> mockHistory = new ArrayList<>();
        // Add some booking details to the list
        mockHistory.add(new BookingDetails());
        mockHistory.add(new BookingDetails());
        when(bookingRepository.findByWasherPhoneNumber(anyString())).thenReturn(mockHistory);

        // Call the viewWasherHistory method
        String phoneNumber = "1234567890"; 
        List<BookingDetails> result = bookingService.viewWasherHistory(phoneNumber);

        // Assertions
        assertNotNull(result);
        assertEquals(mockHistory.size(), result.size());
    }

    @Test
    public void testViewWasherHistory_NoHistory() {
        // Mock the behavior of bookingRepository to return an empty list, indicating no history
        when(bookingRepository.findByWasherPhoneNumber(anyString())).thenReturn(new ArrayList<>());

        // Call the viewWasherHistory method for a washer with no history
        String phoneNumber = "1234567890";
        BookingException exception = assertThrows(BookingException.class,
                () -> bookingService.viewWasherHistory(phoneNumber));

        assertEquals("Washer has no wash history. ", exception.getMessage());
    }

    @Test
    public void testViewBookingHistory_Success() {
        // Mock the behavior of bookingRepository to return a list of BookingDetails
        List<BookingDetails> mockHistory = new ArrayList<>();
        // Add some booking details to the list
        mockHistory.add(new BookingDetails());
        mockHistory.add(new BookingDetails());
        when(bookingRepository.findAll()).thenReturn(mockHistory);

        // Call the viewBookingHistory method
        List<BookingDetails> result = bookingService.viewBookingHistory();

        // Assertions
        assertNotNull(result);
        assertEquals(mockHistory.size(), result.size());
    }

    @Test
    public void testViewBookingHistory_NoHistory() {
        // Mock the behavior of bookingRepository to return an empty list, indicating no history
        when(bookingRepository.findAll()).thenReturn(new ArrayList<>());

        // Call the viewBookingHistory method when there is no booking history
        BookingException exception = assertThrows(BookingException.class,
                () -> bookingService.viewBookingHistory());

        assertEquals("No history of car washes. ", exception.getMessage());
    }
    
    @Test
    public void testUpdateWasherDetails() {
        // Mocking a list of booking details
        List<BookingDetails> bookingDetailsList = new ArrayList<>();
        BookingDetails booking1 = new BookingDetails();
        booking1.setWasherPhoneNumber("oldPhoneNumber");
        bookingDetailsList.add(booking1);

        // Set up the mock behavior
        when(bookingRepository.findAll()).thenReturn(bookingDetailsList);

        // Call the method to update washer details
        bookingService.updateWasherDetails("oldPhoneNumber", "New Washer Name", "New Washer Phone");

        // Verify that the save method was called with the updated details
        verify(bookingRepository, times(1)).save(booking1);
        assertEquals("New Washer Name", booking1.getWasherName());
        assertEquals("New Washer Phone", booking1.getWasherPhoneNumber());
    }

    @Test
    public void testUpdateCustomerDetails() {
        // Mocking a list of booking details
        List<BookingDetails> bookingDetailsList = new ArrayList<>();
        BookingDetails booking1 = new BookingDetails();
        booking1.setCustomerPhoneNumber("oldPhoneNumber");
        bookingDetailsList.add(booking1);

        // Set up the mock behavior
        when(bookingRepository.findAll()).thenReturn(bookingDetailsList);

        // Call the method to update customer details
        bookingService.updateCustomerDetails("oldPhoneNumber", "New Customer Name", "New Customer Phone");

        // Verify that the save method was called with the updated details
        verify(bookingRepository, times(1)).save(booking1);
        assertEquals("New Customer Name", booking1.getCustomerName());
        assertEquals("New Customer Phone", booking1.getCustomerPhoneNumber());
    }
    
    
    @Test
    public void testWashComplete_Success() {
        // Mock the behavior of bookingRepository.findByBookingId
        BookingDetails mockBooking = new BookingDetails();
        mockBooking.setWashStatus("ACCEPTED");
        mockBooking.setWashPackage("BasicPackage");
        when(bookingRepository.findByBookingId(anyString())).thenReturn(mockBooking);
        
        // Mock the behavior of rest.exchange for updating customer rating
        ResponseEntity<String> mockCustomerRatingResponse = ResponseEntity.ok("Rating updated");
        when(restTemplate.exchange(
            eq("http://localhost:" + cPort + "/customer/updateCustomerRating/" + mockBooking.getCustomerPhoneNumber() + "/" + 5), 
            eq(HttpMethod.PUT), 
            any(HttpEntity.class), 
            eq(String.class)))
        .thenReturn(mockCustomerRatingResponse);

        // Mock the behavior of rest.getForObject for getting customer details
        Customer mockCustomer = new Customer();
        mockCustomer.setRating(5);
        when(restTemplate.getForObject(anyString(), eq(Customer.class))).thenReturn(mockCustomer);

        // Mock the behavior of packagesRepository.findByPackageName
        Packages mockPackage = new Packages();
        mockPackage.setPrice(10.0); 
        when(packagesRepository.findByPackageName(anyString())).thenReturn(mockPackage);

        // Mock the behavior of addonsRepository.findByAddOnName
        AddOns mockAddon = new AddOns();
        mockAddon.setPrice(5.0); 
        when(addonsRepository.findByAddOnName(anyString())).thenReturn(mockAddon);

        BookingDetails result = bookingService.washComplete("booking123", 5);

        // Verify that the booking status is updated to "COMPLETED"
        assertEquals("COMPLETED", result.getWashStatus());

        // Verify that the customer rating is updated
        assertEquals(5, result.getCustomerRatingGiven());

    }

    @Test
    public void testRateWasher_Success() {
        // Mock the behavior of bookingRepository.findByBookingId
        BookingDetails mockBooking = new BookingDetails();
        mockBooking.setWashStatus("COMPLETED"); 
        when(bookingRepository.findByBookingId(anyString())).thenReturn(mockBooking);

        // Mock the behavior of rest.exchange for updating washer rating
        ResponseEntity<String> mockWasherRatingResponse = ResponseEntity.ok("Rating updated");
        when(restTemplate.exchange(
            eq("http://localhost:" + wPort + "/washer/updateWasherRating/" + mockBooking.getWasherPhoneNumber() + "/" + 5), 
            eq(HttpMethod.PUT), 
            any(HttpEntity.class), 
            eq(String.class)))
        .thenReturn(mockWasherRatingResponse);

        Washer mockWasher = new Washer();
        mockWasher.setRating(5);
        when(restTemplate.getForObject(anyString(), eq(Washer.class))).thenReturn(mockWasher);

        BookingDetails result = bookingService.rateWasher("booking123", 5);

        // Verify that the booking status is not changed
        assertEquals("COMPLETED", result.getWashStatus());

        // Verify that the washer rating is updated
        assertEquals(5, result.getWasherRatingGiven());

        // Verify that the booking is saved
        verify(bookingRepository, times(1)).save(mockBooking);
    }
    

    @Test
    void testBookWash_Success2() {
        String phoneNumber = "1234567890";
        BookingDetails bookingDetails = new BookingDetails();
        bookingDetails.setCarNumber("TN47BV3456");
        bookingDetails.setWashDate("2023-09-25");
        bookingDetails.setWashTime("10:00 AM");
        bookingDetails.setWashPackage("Basic");
        bookingDetails.setWashAddOn(new ArrayList<>());

        Customer mockCustomer = new Customer();
        mockCustomer.setFirstName("John");
        mockCustomer.setLastName("Doe");
        mockCustomer.setPhoneNumber(phoneNumber);
        CarDetails c = new CarDetails();
        c.setNumberPlate("TN47BV3456"); 
        c.setBrand("VW");
        c.setMfgYear("2022");
        List<CarDetails> l = new ArrayList<>();
        l.add(c);
        mockCustomer.setCarsList(l);

        // Mock the behavior of rest.getForObject for getting customer details
        when(restTemplate.getForObject("http://localhost:" + cPort + "/customer/viewCustomer/" + phoneNumber, Customer.class))
                .thenReturn(mockCustomer);

        // Mock the behavior of packagesRepository.findByPackageName
        Packages mockPackage = new Packages();
        mockPackage.setPackageName("Basic");
        when(packagesRepository.findByPackageName("Basic")).thenReturn(mockPackage);

        // Mock the behavior of addonsRepository.findByAddOnName
        when(addonsRepository.findByAddOnName(anyString())).thenReturn(null);

        // Mock the behavior of bookingRepository.findAll
        when(bookingRepository.findAll()).thenReturn(new ArrayList<>());

        // Mock the behavior of rest.exchange for getting washer list
        Washer w = new Washer();
        w.setAge("20");
        w.setFirstName("ram");
        w.setLastName("Kumar");
        w.setPassword("Qweerty123!");
        w.setPhoneNumber("9876543210");
        w.setRating(5);
        w.setWashesDone(4);
        List<Washer> mockWasherList = new ArrayList<>();
        mockWasherList.add(w);
        ResponseEntity<List<Washer>> mockWasherResponse = ResponseEntity.ok(mockWasherList);
        when(restTemplate.exchange(
                "http://localhost:" + wPort + "/washer/viewAllWasher",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Washer>>() {}
        )).thenReturn(mockWasherResponse);
        
        when(bookingRepository.save(any(BookingDetails.class))).thenReturn(bookingDetails);

        BookingDetails result = bookingService.bookWash(phoneNumber, bookingDetails);

        verify(bookingRepository, times(1)).save(bookingDetails); 
        
        assertEquals("John Doe", result.getCustomerName()); 
        assertEquals("ram Kumar", result.getWasherName());
        assertNotNull(result.getCarNumber());
    }
    
    @Test
    void testRescheduleWash_Success() {
        String bookingId = "booking123";
        String washDate = "2023-09-26";
        String washTime = "2:00 PM";

        BookingDetails existingBooking = new BookingDetails();
        existingBooking.setWashStatus("REQUESTED");

        // Mock the behavior of bookingRepository.findByBookingId
        when(bookingRepository.findByBookingId(bookingId)).thenReturn(existingBooking);

        // Mock the behavior of rest.exchange for getting washer list
        Washer w = new Washer();
        w.setFirstName("John");
        w.setLastName("Doe");
        w.setPhoneNumber("9876543210");
        w.setRating(4.5); // Washer rating
        List<Washer> mockWasherList = new ArrayList<>();
        mockWasherList.add(w);

        ResponseEntity<List<Washer>> mockWasherResponse = ResponseEntity.ok(mockWasherList);
        when(restTemplate.exchange(
                "http://localhost:" + wPort + "/washer/viewAllWasher",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Washer>>() {}
        )).thenReturn(mockWasherResponse);

        List<BookingDetails> existingBookings = new ArrayList<>();
        // Mock the behavior of bookingRepository.findAll
        when(bookingRepository.findAll()).thenReturn(existingBookings);

        // Mock the behavior of bookingRepository.save
        when(bookingRepository.save(any(BookingDetails.class))).thenReturn(existingBooking);

        BookingDetails result = bookingService.rescheduleWash(bookingId, washDate, washTime);

        verify(bookingRepository, times(1)).save(existingBooking);
        assertNotNull(result);

        assertEquals(w.getFirstName() + " " + w.getLastName(), result.getWasherName());
        assertEquals(w.getPhoneNumber(), result.getWasherPhoneNumber());
        assertEquals(w.getRating(), result.getWasherRating());
        assertEquals(washDate, result.getWashDate());
        assertEquals(washTime, result.getWashTime());
        assertEquals("REQUESTED", result.getWashStatus());
    }

    
}
