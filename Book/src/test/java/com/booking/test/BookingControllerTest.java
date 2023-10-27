package com.booking.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.booking.controller.BookingController;
import com.booking.dummyentity.DateTime;
import com.booking.dummyentity.Rating;
import com.booking.dummyentity.Response;
import com.booking.entity.BookingDetails;
import com.booking.entity.InvoiceDetails;
import com.booking.exception.BookingException;
import com.booking.service.BookingService;

import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

class BookingControllerTest {

	@InjectMocks
    private BookingController bookingController;

    @Mock
    private BookingService bookingService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this); 
    }

    @Test
    public void testBookWash_Successful() {
        // Mock the behavior of bookingService.bookWash to return the mock BookingDetails
        BookingDetails mockBookingDetails = new BookingDetails();
        when(bookingService.bookWash(anyString(), any(BookingDetails.class))).thenReturn(mockBookingDetails);

        // Create a sample request
        String phoneNumber = "1234567890";
        BookingDetails request = new BookingDetails();
        // Set up request details as needed

        // Call the bookWash method in the controller
        ResponseEntity<?> responseEntity = bookingController.bookWash(phoneNumber, request);

        // Verify that it returns an HTTP status code of 200 (OK)
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        // Optionally, verify the response body or message as well

        // Verify that bookingService.bookWash is called with the correct arguments
        verify(bookingService, times(1)).bookWash(phoneNumber, request);
    }
    
    @Test
    public void testBookWash_Unsuccessful() {
        // Mock the behavior of bookingService.bookWash to throw a BookingException
        BookingDetails mockBookingDetails = new BookingDetails();
        when(bookingService.bookWash(anyString(), any(BookingDetails.class))).thenThrow(new BookingException("Booking failed"));

        // Create a sample request
        String phoneNumber = "1234567890";
        BookingDetails request = new BookingDetails(); 
        // Set up request details as needed

        // Call the bookWash method in the controller
        ResponseEntity<?> responseEntity = bookingController.bookWash(phoneNumber, request);

        // Verify that it returns an HTTP status code of 400 (BAD_REQUEST)
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        // Optionally, verify the response body or message as well

        // Verify that bookingService.bookWash is called with the correct arguments
        verify(bookingService, times(1)).bookWash(phoneNumber, request);
    }
    
    @Test
    public void testRescheduleWash_Successful() {
        // Mock the behavior of bookingService.rescheduleWash to return the mock BookingDetails
        BookingDetails mockBookingDetails = new BookingDetails();
        when(bookingService.rescheduleWash(anyString(), anyString(), anyString())).thenReturn(mockBookingDetails);

        // Create a sample request
        String bookingId = "12345";
        DateTime rescheduleRequest = new DateTime();
        rescheduleRequest.setWashDate("2023-09-30");
        rescheduleRequest.setWashTime("10:00 AM");

        // Call the rescheduleWash method in the controller
        ResponseEntity<?> responseEntity = bookingController.rescheduleWash(bookingId, rescheduleRequest);

        // Verify that it returns an HTTP status code of 200 (OK)
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        // Optionally, verify the response body or message as well

        // Verify that bookingService.rescheduleWash is called with the correct arguments
        verify(bookingService, times(1)).rescheduleWash(bookingId, rescheduleRequest.getWashDate(), rescheduleRequest.getWashTime());
    }
    
    @Test
    public void testRescheduleWash_Unsuccessful() {
        // Mock the behavior of bookingService.rescheduleWash to throw a BookingException
        when(bookingService.rescheduleWash(anyString(), anyString(), anyString())).thenThrow(new BookingException("Rescheduling failed"));

        // Create a sample request
        String bookingId = "12345";
        DateTime rescheduleRequest = new DateTime();
        rescheduleRequest.setWashDate("2023-09-30");
        rescheduleRequest.setWashTime("10:00 AM");

        // Call the rescheduleWash method in the controller
        ResponseEntity<?> responseEntity = bookingController.rescheduleWash(bookingId, rescheduleRequest);

        // Verify that it returns an HTTP status code of 400 (BAD_REQUEST)
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        // Optionally, verify the response body or message as well

        // Verify that bookingService.rescheduleWash is called with the correct arguments
        verify(bookingService, times(1)).rescheduleWash(bookingId, rescheduleRequest.getWashDate(), rescheduleRequest.getWashTime());
    }
    
    @Test
    public void testCancelWash_Successful() {
        // Mock the behavior of bookingService.cancelWash to return the mock BookingDetails
        BookingDetails mockBookingDetails = new BookingDetails();
        when(bookingService.cancelWash(anyString())).thenReturn(mockBookingDetails);

        // Create a sample booking ID
        String bookingId = "12345";

        // Call the cancelWash method in the controller
        ResponseEntity<?> responseEntity = bookingController.cancelWash(bookingId);

        // Verify that it returns an HTTP status code of 200 (OK)
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        // Optionally, verify the response body or message as well

        // Verify that bookingService.cancelWash is called with the correct argument
        verify(bookingService, times(1)).cancelWash(bookingId);
    }
    
    @Test
    public void testCancelWash_Unsuccessful() {
        // Mock the behavior of bookingService.cancelWash to throw a BookingException
        when(bookingService.cancelWash(anyString())).thenThrow(new BookingException("Cancellation failed"));

        // Create a sample booking ID
        String bookingId = "12345";

        // Call the cancelWash method in the controller
        ResponseEntity<?> responseEntity = bookingController.cancelWash(bookingId);

        // Verify that it returns an HTTP status code of 400 (BAD_REQUEST)
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        // Optionally, verify the response body or message as well

        // Verify that bookingService.cancelWash is called with the correct argument
        verify(bookingService, times(1)).cancelWash(bookingId);
    }
    
    @Test
    public void testRespondWash_Successful() {
        // Mock the behavior of bookingService.respondWash to return the mock BookingDetails
        BookingDetails mockBookingDetails = new BookingDetails();
        when(bookingService.respondWash(anyString(), anyString())).thenReturn(mockBookingDetails);

        // Create a sample booking ID and response
        String bookingId = "12345";
        Response respondRequest = new Response();
        respondRequest.setResponse("accept");

        // Call the respondWash method in the controller
        ResponseEntity<?> responseEntity = bookingController.respondWash(bookingId, respondRequest);

        // Verify that it returns an HTTP status code of 200 (OK)
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        // Optionally, verify the response body or message as well

        // Verify that bookingService.respondWash is called with the correct arguments
        verify(bookingService, times(1)).respondWash(bookingId, respondRequest.getResponse());
    }
    
    @Test
    public void testRespondWash_Unsuccessful() {
        // Mock the behavior of bookingService.respondWash to throw a BookingException
        when(bookingService.respondWash(anyString(), anyString())).thenThrow(new BookingException("Response failed"));

        // Create a sample booking ID and response
        String bookingId = "12345";
        Response respondRequest = new Response();
        respondRequest.setResponse("accept");

        // Call the respondWash method in the controller
        ResponseEntity<?> responseEntity = bookingController.respondWash(bookingId, respondRequest);

        // Verify that it returns an HTTP status code of 400 (BAD_REQUEST)
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        // Optionally, verify the response body or message as well

        // Verify that bookingService.respondWash is called with the correct arguments
        verify(bookingService, times(1)).respondWash(bookingId, respondRequest.getResponse());
    }
    
    @Test
    public void testWashComplete_Successful() {
        // Mock the behavior of bookingService.washComplete to return the mock BookingDetails
        BookingDetails mockBookingDetails = new BookingDetails();
        when(bookingService.washComplete(anyString(), anyInt())).thenReturn(mockBookingDetails);

        // Create a sample booking ID and wash complete request
        String bookingId = "12345";
        Rating washCompleteRequest = new Rating();
        washCompleteRequest.setRating(5);

        // Call the washComplete method in the controller
        ResponseEntity<?> responseEntity = bookingController.washComplete(bookingId, washCompleteRequest);

        // Verify that it returns an HTTP status code of 200 (OK)
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        // Optionally, verify the response body or message as well

        // Verify that bookingService.washComplete is called with the correct arguments
        verify(bookingService, times(1)).washComplete(bookingId, washCompleteRequest.getRating());
    }
    
    @Test
    public void testWashComplete_Unsuccessful() {
        // Mock the behavior of bookingService.washComplete to throw a BookingException
        when(bookingService.washComplete(anyString(), anyInt())).thenThrow(new BookingException("Wash completion failed"));

        // Create a sample booking ID and wash complete request
        String bookingId = "12345";
        Rating washCompleteRequest = new Rating();
        washCompleteRequest.setRating(5);

        // Call the washComplete method in the controller
        ResponseEntity<?> responseEntity = bookingController.washComplete(bookingId, washCompleteRequest);

        // Verify that it returns an HTTP status code of 400 (BAD_REQUEST)
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        // Optionally, verify the response body or message as well

        // Verify that bookingService.washComplete is called with the correct arguments
        verify(bookingService, times(1)).washComplete(bookingId, washCompleteRequest.getRating());
    }
    
    @Test
    public void testRateWasher_Successful() {
        // Mock the behavior of bookingService.rateWasher to return the mock BookingDetails
        BookingDetails mockBookingDetails = new BookingDetails();
        when(bookingService.rateWasher(anyString(), anyInt())).thenReturn(mockBookingDetails);

        // Create a sample booking ID and rate washer request with an int rating
        String bookingId = "12345";
        int washerRating = 4; // Change to the int rating value
        Rating r = new Rating();
        r.setRating(washerRating);

        // Call the rateWasher method in the controller
        ResponseEntity<?> responseEntity = bookingController.rateWasher(bookingId, r);

        // Verify that it returns an HTTP status code of 200 (OK)
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        // Optionally, verify the response body or message as well

        // Verify that bookingService.rateWasher is called with the correct arguments
        verify(bookingService, times(1)).rateWasher(bookingId, washerRating);
    }
    
    @Test
    public void testRateWasher_Unsuccessful() {
        // Mock the behavior of bookingService.rateWasher to throw a BookingException
        when(bookingService.rateWasher(anyString(), anyInt())).thenThrow(new BookingException("Invalid rating"));

        // Create a sample booking ID and rate washer request with an int rating
        String bookingId = "12345";
        int washerRating = 6; // This rating will cause an exception
        Rating r = new Rating();
        r.setRating(washerRating);

        // Call the rateWasher method in the controller
        ResponseEntity<?> responseEntity = bookingController.rateWasher(bookingId, r);

        // Verify that it returns an HTTP status code of 400 (Bad Request)
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        // Optionally, verify the response body or message as well
    }
    
    @Test
    public void testViewBookingDetails_Successful() {
        // Mock the behavior of bookingService.viewBookingDetails to return a mock BookingDetails object
        BookingDetails mockBookingDetails = new BookingDetails();
        when(bookingService.viewBookingDetails(anyString())).thenReturn(mockBookingDetails);

        // Create a sample booking ID
        String bookingId = "12345";

        // Call the viewBookingDetails method in the controller
        ResponseEntity<?> responseEntity = bookingController.viewBookingDetails(bookingId);

        // Verify that it returns an HTTP status code of 200 (OK) and the expected BookingDetails object
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(mockBookingDetails, responseEntity.getBody());
    }

    @Test
    public void testViewBookingDetails_Unsuccessful() {
        // Mock the behavior of bookingService.viewBookingDetails to throw a BookingException
        when(bookingService.viewBookingDetails(anyString())).thenThrow(new BookingException("Booking not found"));

        // Create a sample booking ID
        String bookingId = "12345";

        // Call the viewBookingDetails method in the controller
        ResponseEntity<?> responseEntity = bookingController.viewBookingDetails(bookingId);

        // Verify that it returns an HTTP status code of 400 (Bad Request)
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        // Optionally, verify the response body or message as well
    }
    
    @Test
    public void testViewInvoiceDetails_Successful() {
        // Mock the behavior of bookingService.viewInvoiceDetails to return a mock InvoiceDetails object
        InvoiceDetails mockInvoiceDetails = new InvoiceDetails();
        when(bookingService.viewInvoiceDetails(anyString())).thenReturn(mockInvoiceDetails);

        // Create a sample booking ID
        String bookingId = "12345";

        // Call the viewInvoiceDetails method in the controller
        ResponseEntity<?> responseEntity = bookingController.viewInvoiceDetails(bookingId);

        // Verify that it returns an HTTP status code of 200 (OK) and the expected InvoiceDetails object
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(mockInvoiceDetails, responseEntity.getBody());
    }

    @Test
    public void testViewInvoiceDetails_Unsuccessful() {
        // Mock the behavior of bookingService.viewInvoiceDetails to throw a BookingException
        when(bookingService.viewInvoiceDetails(anyString())).thenThrow(new BookingException("Invoice not found"));

        // Create a sample booking ID
        String bookingId = "12345";

        // Call the viewInvoiceDetails method in the controller
        ResponseEntity<?> responseEntity = bookingController.viewInvoiceDetails(bookingId);

        // Verify that it returns an HTTP status code of 400 (Bad Request)
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        // Optionally, verify the response body or message as well
    }
    
    @Test
    public void testViewCustomerHistory_Successful() {
        // Mock the behavior of bookingService.viewCustomerHistory to return a list of mock BookingDetails
        List<BookingDetails> mockBookingDetailsList = new ArrayList<>();
        BookingDetails booking1 = new BookingDetails();
        booking1.setBookingId("1");
        // Add more BookingDetails as needed
        mockBookingDetailsList.add(booking1);
        when(bookingService.viewCustomerHistory(anyString())).thenReturn(mockBookingDetailsList);

        // Create a sample phone number
        String phoneNumber = "1234567890";

        // Call the viewCustomerHistory method in the controller
        ResponseEntity<?> responseEntity = bookingController.viewCustomerHistory(phoneNumber);

        // Verify that it returns an HTTP status code of 200 (OK) and the expected list of BookingDetails
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(mockBookingDetailsList, responseEntity.getBody());
    }

    @Test
    public void testViewCustomerHistory_Unsuccessful() {
        // Mock the behavior of bookingService.viewCustomerHistory to throw a BookingException
        when(bookingService.viewCustomerHistory(anyString())).thenThrow(new BookingException("Customer booking history not found"));

        // Create a sample phone number
        String phoneNumber = "1234567890";

        // Call the viewCustomerHistory method in the controller
        ResponseEntity<?> responseEntity = bookingController.viewCustomerHistory(phoneNumber);

        // Verify that it returns an HTTP status code of 400 (Bad Request)
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        // Optionally, verify the response body or message as well
    }
    
    @Test
    public void testViewWasherHistory_Successful() {
        // Mock the behavior of bookingService.viewWasherHistory to return a list of mock BookingDetails
        List<BookingDetails> mockBookingDetailsList = new ArrayList<>();
        BookingDetails booking1 = new BookingDetails();
        booking1.setBookingId("1");
        // Add more BookingDetails as needed
        mockBookingDetailsList.add(booking1);
        when(bookingService.viewWasherHistory(anyString())).thenReturn(mockBookingDetailsList);

        // Create a sample phone number
        String phoneNumber = "1234567890";

        // Call the viewWasherHistory method in the controller
        ResponseEntity<?> responseEntity = bookingController.viewWasherHistory(phoneNumber);

        // Verify that it returns an HTTP status code of 200 (OK) and the expected list of BookingDetails
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(mockBookingDetailsList, responseEntity.getBody());
    }

    @Test
    public void testViewWasherHistory_Unsuccessful() {
        // Mock the behavior of bookingService.viewWasherHistory to throw a BookingException
        when(bookingService.viewWasherHistory(anyString())).thenThrow(new BookingException("Washer booking history not found"));

        // Create a sample phone number
        String phoneNumber = "1234567890";

        // Call the viewWasherHistory method in the controller
        ResponseEntity<?> responseEntity = bookingController.viewWasherHistory(phoneNumber);

        // Verify that it returns an HTTP status code of 400 (Bad Request)
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        // Optionally, verify the response body or message as well
    }
    
    @Test
    public void testViewBookingHistory_Successful() {
        // Mock the behavior of bookingService.viewBookingHistory to return a list of mock BookingDetails
        List<BookingDetails> mockBookingDetailsList = new ArrayList<>();
        BookingDetails booking1 = new BookingDetails();
        booking1.setBookingId("1");
        // Add more BookingDetails as needed
        mockBookingDetailsList.add(booking1);
        when(bookingService.viewBookingHistory()).thenReturn(mockBookingDetailsList);

        // Call the viewBookingHistory method in the controller
        ResponseEntity<?> responseEntity = bookingController.viewBookingHistory();

        // Verify that it returns an HTTP status code of 200 (OK) and the expected list of BookingDetails
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(mockBookingDetailsList, responseEntity.getBody());
    }

    @Test
    public void testViewBookingHistory_Unsuccessful() {
        // Mock the behavior of bookingService.viewBookingHistory to throw a BookingException
        when(bookingService.viewBookingHistory()).thenThrow(new BookingException("Booking history not found"));

        // Call the viewBookingHistory method in the controller
        ResponseEntity<?> responseEntity = bookingController.viewBookingHistory();

        // Verify that it returns an HTTP status code of 400 (Bad Request)
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        // Optionally, verify the response body or message as well
    }
    
    @Test
    public void testUpdateWasherDetails_Successful() {
        // Mock the behavior of bookingService.updateWasherDetails for a successful update
        String oldPhoneNumber = "oldPhoneNumber";
        String washerName = "newWasherName";
        String phoneNumber = "newPhoneNumber";
        doNothing().when(bookingService).updateWasherDetails(oldPhoneNumber, washerName, phoneNumber);

        // Call the updateWasherDetails method in the controller
        ResponseEntity<?> responseEntity = bookingController.updateWasherDetails(oldPhoneNumber, washerName, phoneNumber);

        // Verify that it returns an HTTP status code of 200 (OK)
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void testUpdateWasherDetails_Unsuccessful() {
        // Mock the behavior of bookingService.updateWasherDetails to throw a BookingException
        String oldPhoneNumber = "oldPhoneNumber";
        String washerName = "newWasherName";
        String phoneNumber = "newPhoneNumber";
        doThrow(new BookingException("Failed to update washer details")).when(bookingService)
                .updateWasherDetails(oldPhoneNumber, washerName, phoneNumber);

        // Call the updateWasherDetails method in the controller
        ResponseEntity<?> responseEntity = bookingController.updateWasherDetails(oldPhoneNumber, washerName, phoneNumber);

        // Verify that it returns an HTTP status code of 400 (Bad Request)
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        // Optionally, verify the response body or message as well
    }
    
    @Test
    public void testUpdateCustomerDetails_Successful() {
        // Mock the behavior of bookingService.updateCustomerDetails for a successful update
        String oldPhoneNumber = "oldPhoneNumber";
        String customerName = "newCustomerName";
        String phoneNumber = "newPhoneNumber";
        doNothing().when(bookingService).updateCustomerDetails(oldPhoneNumber, customerName, phoneNumber);

        // Call the updateCustomerDetails method in the controller
        ResponseEntity<?> responseEntity = bookingController.updateCustomerDetails(oldPhoneNumber, customerName, phoneNumber);

        // Verify that it returns an HTTP status code of 200 (OK)
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void testUpdateCustomerDetails_Unsuccessful() {
        // Mock the behavior of bookingService.updateCustomerDetails to throw a BookingException
        String oldPhoneNumber = "oldPhoneNumber";
        String customerName = "newCustomerName";
        String phoneNumber = "newPhoneNumber";
        doThrow(new BookingException("Failed to update customer details")).when(bookingService)
                .updateCustomerDetails(oldPhoneNumber, customerName, phoneNumber);

        // Call the updateCustomerDetails method in the controller
        ResponseEntity<?> responseEntity = bookingController.updateCustomerDetails(oldPhoneNumber, customerName, phoneNumber);

        // Verify that it returns an HTTP status code of 400 (Bad Request)
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        // Optionally, verify the response body or message as well
    }
}
