/**
 * 
 */
package com.washer.test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.washer.controller.WasherController;
import com.washer.entity.Washer;
import com.washer.exception.InvalidDetailsException;
import com.washer.service.WasherService;
class WasherControllerTest {

	@InjectMocks
    private WasherController washerController;

    @Mock
    private WasherService washerService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testAddWasher_AddSuccessfully() {
        // Create a mock Washer object
        Washer washer = new Washer();
        washer.setFirstName("John");
        washer.setLastName("Geaorge");
        washer.setAge("40");
        washer.setPhoneNumber("1234567890");
        washer.setPassword("John1919");
        
        // Mock the behavior of washerService.addWasher to return the added Washer
        when(washerService.addWasher(washer)).thenReturn(washer);

        // Call the addWasher method
        ResponseEntity<?> response = washerController.addWasher(washer);

        // Verify that it returns an HTTP status code of 200 (OK) and the added Washer
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(washer, response.getBody());
    }

    @Test
    public void testAddWasher_InvalidDetailsException() {
        // Create a mock Washer object with invalid details
    	 Washer washer = new Washer();
         washer.setFirstName("John");
         washer.setLastName("Geaorge");
         washer.setAge("40");
         washer.setPhoneNumber("1234567890");
         washer.setPassword("John1919");
         
         when(washerService.addWasher(washer)).thenReturn(washer);
         
         Washer washer2 = new Washer();
         washer2.setFirstName("John");
         washer2.setLastName("Geaorge");
         washer2.setAge("40");
         washer2.setPhoneNumber("1234567890");
         washer2.setPassword("John1919");

        // Mock the behavior of washerService.addWasher to throw an InvalidDetailsException
        when(washerService.addWasher(washer2)).thenThrow(new InvalidDetailsException("Invalid washer details. "));

        // Call the addWasher method and expect a Bad Request response with an error message
        ResponseEntity<?> response = washerController.addWasher(washer2);

        assertEquals("Invalid washer details. Please try again!", response.getBody());
    }
    
    @Test
    public void testUpdateWasher_UpdateSuccessfully() {
        // Create a mock Washer object
        Washer washer = new Washer();
        washer.setFirstName("John");
        washer.setLastName("Geaorge");
        washer.setAge("40");
        washer.setPhoneNumber("1234567890");
        washer.setPassword("John1919");

        // Mock the behavior of washerService.updateWasher to return the updated Washer
        when(washerService.updateWasher("1234567890", washer)).thenReturn(washer);

        // Call the updateWasher method
        ResponseEntity<?> response = washerController.updateWasher("1234567890", washer);

        // Verify that it returns an HTTP status code of 200 (OK) and the updated Washer
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(washer, response.getBody());
    }


    
    @Test
    public void testDeleteWasher_DeleteSuccessfully() {
        // Call the deleteWasher method with a valid phoneNumber
        ResponseEntity<?> response = washerController.deleteWasher("1234567890");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Washer detail deleted successfully. ", response.getBody());
        
        verify(washerService, times(1)).deleteWasher("1234567890");
    }

    @Test
    public void testDeleteWasher_WasherNotFound() {
        // Mock the behavior of washerService.deleteWasher to throw an InvalidDetailsException (Washer not found)
        doThrow(new InvalidDetailsException("Washer not found.")).when(washerService).deleteWasher("1234567890");

        // Call the deleteWasher method with a valid phoneNumber and expect a Bad Request response with an error message
        ResponseEntity<?> response = washerController.deleteWasher("1234567890");

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Washer not found.", response.getBody());
        
        verify(washerService, times(1)).deleteWasher("1234567890");
    }
    
    @Test
    public void testViewWasher_ViewSuccessfully() {
        // Create a mock Washer object
        Washer washer = new Washer();
        washer.setFirstName("John");
        washer.setLastName("Geaorge");
        washer.setAge("40");
        washer.setPhoneNumber("1234567890");
        washer.setPassword("John1919");

        // Mock the behavior of washerService.viewWasher to return the retrieved Washer
        when(washerService.viewWasher("1234567890")).thenReturn(washer);

        // Call the viewWasher method with a valid phoneNumber
        ResponseEntity<?> response = washerController.viewWasher("1234567890");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(washer, response.getBody());
    }

    @Test
    public void testViewWasher_WasherNotFound() {
        // Mock the behavior of washerService.viewWasher to throw an InvalidDetailsException (Washer not found)
        when(washerService.viewWasher("1234567890")).thenThrow(new InvalidDetailsException("Washer not found."));

        // Call the viewWasher method with a valid phoneNumber and expect a Bad Request response with an error message
        ResponseEntity<?> response = washerController.viewWasher("1234567890");

        // Verify that it returns an HTTP status code of 400 (Bad Request) and the error message
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Washer not found.Try again! ", response.getBody());
    }

    @Test
    public void testViewAllWasher_ViewAllSuccessfully() {
        // Create a list of mock Washer objects
        List<Washer> washers = new ArrayList<>();
        Washer washer1 = new Washer();
        washer1.setFirstName("John");
        washer1.setLastName("Geaorge");
        washer1.setAge("40");
        washer1.setPhoneNumber("1234567890");
        washer1.setPassword("John1919");
        washers.add(washer1);

        Washer washer2 = new Washer();
        washer2.setFirstName("John");
        washer2.setLastName("Geaorge");
        washer2.setAge("40");
        washer2.setPhoneNumber("2234567890");
        washer2.setPassword("John1919");
        washers.add(washer2);

        // Mock the behavior of washerService.viewAllWasher to return the list of Washers
        when(washerService.viewAllWasher()).thenReturn(washers);

        // Call the viewAllWasher method
        ResponseEntity<?> response = washerController.viewAllWasher();

        // Verify that it returns an HTTP status code of 200 (OK) and the list of retrieved Washers
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(washers, response.getBody());
    }

    @Test
    public void testViewAllWasher_NoWashersFound() {
        // Mock the behavior of washerService.viewAllWasher to throw an InvalidDetailsException (No Washers found)
        when(washerService.viewAllWasher()).thenThrow(new InvalidDetailsException("No Washers found."));

        // Call the viewAllWasher method and expect a Bad Request response with an error message
        ResponseEntity<?> response = washerController.viewAllWasher();

        // Verify that it returns an HTTP status code of 400 (Bad Request) and the error message
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("No Washers found.Try again! ", response.getBody());
    }
    
    @Test
    public void testUpdateWasherRating_UpdateSuccessfully() {
        // Create a mock Washer object 
        Washer washer = new Washer();
        washer.setFirstName("John");
        washer.setLastName("Geaorge");
        washer.setAge("40");
        washer.setPhoneNumber("1234567890");
        washer.setPassword("John1919");
        washer.setRating(4.5); // Initial rating

        // Mock the behavior of washerService.updateWasherRating to return the updated Washer
        when(washerService.updateWasherRating("1234567890", 4)).thenReturn(washer); // New rating is 4

        // Call the updateWasherRating method with a valid phoneNumber and rating
        ResponseEntity<?> response = washerController.updateWasherRating("1234567890", 4);

        // Verify that it returns an HTTP status code of 200 (OK) and the updated Washer
        assertEquals(HttpStatus.OK, response.getStatusCode()); 
        assertEquals(washer, response.getBody());
    }


    @Test
    public void testUpdateWasherRating_WasherNotFound() {
        // Mock the behavior of washerService.updateWasherRating to throw an InvalidDetailsException (Washer not found)
        when(washerService.updateWasherRating("1234567890", 4)).thenThrow(new InvalidDetailsException("Washer not found."));

        // Call the updateWasherRating method with a valid phoneNumber and rating and expect a Bad Request response with an error message
        ResponseEntity<?> response = washerController.updateWasherRating("1234567890", 4);

        // Verify that it returns an HTTP status code of 400 (Bad Request) and the error message
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Washer not found.", response.getBody());
    }
}
