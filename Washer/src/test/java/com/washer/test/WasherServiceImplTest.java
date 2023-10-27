package com.washer.test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

import com.washer.dummyentity.BookingDetails;
import com.washer.entity.Washer;
import com.washer.exception.InvalidDetailsException;
import com.washer.repository.WasherRepository;
import com.washer.service.WasherServiceImpl;

class WasherServiceImplTest {

	 	@InjectMocks
	    private WasherServiceImpl washerService;

	    @Mock
	    private WasherRepository washerRepository;
	    
	    @Mock
	    private RestTemplate rest;

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

	        // Mock the behavior of washerRepository.findAll to return an empty list (no existing washers)
	        when(washerRepository.findAll()).thenReturn(new ArrayList<>());

	        // Mock the behavior of washerRepository.save to return the saved Washer
	        when(washerRepository.save(washer)).thenReturn(washer);

	        // Call the addWasher method with a valid Washer object
	        Washer savedWasher = washerService.addWasher(washer);

	        // Verify that it returns the saved Washer object
	        assertEquals(washer, savedWasher);

	        // Verify that washerRepository.findAll and washerRepository.save are called
	        verify(washerRepository, times(1)).findAll();
	        verify(washerRepository, times(1)).save(washer);
	    }

	    @Test
	    public void testAddWasher_WasherAlreadyExists() {
	        // Create a mock Washer object with a phone number that already exists
	        Washer washer = new Washer();
	        washer.setFirstName("John");
	        washer.setLastName("Geaorge");
	        washer.setAge("40");
	        washer.setPhoneNumber("1234567890");
	        washer.setPassword("John1919");

	        // Create another mock Washer object with the same phone number
	        Washer newWasher = new Washer();
	        newWasher.setFirstName("John");
	        newWasher.setLastName("Geaorge");
	        newWasher.setAge("40");
	        newWasher.setPhoneNumber("1234567890");
	        newWasher.setPassword("John1919");

	        // Mock the behavior of washerRepository.findAll to return a list with the existing Washer
	        List<Washer> washers = new ArrayList<>();
	        washers.add(washer);
	        when(washerRepository.findAll()).thenReturn(washers);

	        // Call the addWasher method with the new Washer object and expect an InvalidDetailsException
	        assertThrows(InvalidDetailsException.class, () -> washerService.addWasher(newWasher));

	        // Verify that washerRepository.findAll is called
	        verify(washerRepository, times(1)).findAll();
	    }

	    @Test
	    public void testUpdateWasher_UpdateSuccessfully() {
	        // Create a mock Washer object
	        Washer originalWasher = new Washer();
	        originalWasher.setPhoneNumber("1234567890");
	        originalWasher.setFirstName("John");
	        originalWasher.setLastName("Doe");

	        // Create an updated Washer object
	        Washer updatedWasher = new Washer();
	        updatedWasher.setPhoneNumber("1234567890");
	        updatedWasher.setFirstName("Jane");
	        updatedWasher.setLastName("Smith");

	        // Mock the behavior of washerRepository.findByPhoneNumber to return the original Washer
	        when(washerRepository.findByPhoneNumber("1234567890")).thenReturn(java.util.Optional.of(originalWasher));

	        // Mock the behavior of washerRepository.save to return the updated Washer
	        when(washerRepository.save(updatedWasher)).thenReturn(updatedWasher);

	        // Mock the external REST API call
	        ResponseEntity<List<BookingDetails>> mockResponse = new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
	        when(rest.exchange(anyString(), eq(HttpMethod.GET), eq(null), any(ParameterizedTypeReference.class)))
	                .thenReturn(mockResponse);

	        // Call the updateWasher method with a valid phoneNumber and Washer object
	        Washer updated = washerService.updateWasher("1234567890", updatedWasher);

	        // Verify that washerRepository.findByPhoneNumber and washerRepository.save are called
	        verify(washerRepository, times(1)).findByPhoneNumber("1234567890");
	    }

	    @Test
	    public void testUpdateWasher_WasherNotFound() {
	        // Mock the behavior of washerRepository.findByPhoneNumber to return an empty result (Washer not found)
	        when(washerRepository.findByPhoneNumber("1234567890")).thenReturn(java.util.Optional.empty());

	        // Call the updateWasher method with a non-existent phoneNumber and expect an InvalidDetailsException
	        assertThrows(InvalidDetailsException.class, () -> washerService.updateWasher("1234567890", new Washer()));

	        // Verify that washerRepository.findByPhoneNumber is called
	        verify(washerRepository, times(1)).findByPhoneNumber("1234567890");
	    }

	    @Test
	    public void testUpdateWasher_ExternalServiceError() {
	        // Mock the behavior of washerRepository.findByPhoneNumber to return a mock Washer
	        Washer washer = new Washer();
	        washer.setPhoneNumber("1234567890");
	        when(washerRepository.findByPhoneNumber("1234567890")).thenReturn(java.util.Optional.of(washer));

	        // Mock the external REST API call to simulate an error response
	        ResponseEntity<List<BookingDetails>> mockResponse = new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
	        when(rest.exchange(anyString(), eq(HttpMethod.GET), eq(null), any(ParameterizedTypeReference.class)))
	                .thenReturn(mockResponse);

	    }
	    
	    @Test
	    public void testDeleteWasher_DeleteSuccessfully() {
	        // Create a mock Washer object
	        Washer washerToDelete = new Washer();
	        washerToDelete.setPhoneNumber("1234567890");
	        washerToDelete.setFirstName("John");
	        washerToDelete.setLastName("Doe");

	        // Mock the behavior of washerRepository.findByPhoneNumber to return the mock Washer
	        when(washerRepository.findByPhoneNumber("1234567890")).thenReturn(Optional.of(washerToDelete));

	        // Call the deleteWasher method with a valid phoneNumber
	        washerService.deleteWasher("1234567890");

	        // Verify that washerRepository.findByPhoneNumber and washerRepository.delete are called
	        verify(washerRepository, times(1)).findByPhoneNumber("1234567890");
	        verify(washerRepository, times(1)).delete(washerToDelete);
	    }
	    
	    @Test
	    public void testViewWasher_ViewSuccessfully() {
	        // Create a mock Washer object
	        Washer washer = new Washer();
	        washer.setPhoneNumber("1234567890");
	        washer.setFirstName("John");
	        washer.setLastName("Doe");

	        // Mock the behavior of washerRepository.findByPhoneNumber to return the mock Washer
	        when(washerRepository.findByPhoneNumber("1234567890")).thenReturn(Optional.of(washer));

	        // Call the viewWasher method with a valid phoneNumber
	        Washer viewedWasher = washerService.viewWasher("1234567890");

	        // Verify that it returns the expected Washer object
	        assertEquals(washer, viewedWasher);

	        // Verify that washerRepository.findByPhoneNumber is called
	        verify(washerRepository, times(1)).findByPhoneNumber("1234567890");
	    }

	    @Test
	    public void testViewWasher_WasherNotFound() {
	        // Mock the behavior of washerRepository.findByPhoneNumber to return an empty result (Washer not found)
	        when(washerRepository.findByPhoneNumber("1234567890")).thenReturn(Optional.empty());

	        // Call the viewWasher method with a non-existent phoneNumber and expect an InvalidDetailsException
	        assertThrows(InvalidDetailsException.class, () -> washerService.viewWasher("1234567890"));

	        // Verify that washerRepository.findByPhoneNumber is called
	        verify(washerRepository, times(1)).findByPhoneNumber("1234567890");
	    }

	    @Test
	    public void testViewAllWasher_ViewAllSuccessfully() {
	        // Create a list of mock Washer objects
	        List<Washer> washers = new ArrayList<>();
	        Washer washer1 = new Washer();
	        washer1.setPhoneNumber("1234567890");
	        washer1.setFirstName("John");
	        washer1.setLastName("Doe");
	        washers.add(washer1);

	        Washer washer2 = new Washer();
	        washer2.setPhoneNumber("9876543210");
	        washer2.setFirstName("Jane");
	        washer2.setLastName("Smith");
	        washers.add(washer2);

	        // Mock the behavior of washerRepository.findAll to return the list of mock Washers
	        when(washerRepository.findAll()).thenReturn(washers);

	        // Call the viewAllWasher method
	        List<Washer> allWashers = washerService.viewAllWasher();

	        // Verify that it returns the list of Washers
	        assertEquals(washers, allWashers);

	        // Verify that washerRepository.findAll is called
	        verify(washerRepository, times(1)).findAll();
	    }

	    @Test
	    public void testViewAllWasher_EmptyList() {
	        // Mock the behavior of washerRepository.findAll to return an empty list
	        when(washerRepository.findAll()).thenReturn(new ArrayList<>());

	        // Call the viewAllWasher method when the repository is empty
	        List<Washer> allWashers = washerService.viewAllWasher();

	        // Verify that it returns an empty list
	        assertEquals(0, allWashers.size());

	        // Verify that washerRepository.findAll is called
	        verify(washerRepository, times(1)).findAll();
	    }
	    
	    @Test
	    public void testUpdateWasherRating_UpdateSuccessfully() {
	        // Create a mock Washer object
	        Washer washer = new Washer();
	        washer.setPhoneNumber("1234567890");
	        washer.setFirstName("John");
	        washer.setLastName("Doe");
	        washer.setRating(4.0); // Initial rating
	        washer.setWashesDone(5); // Initial number of washes done

	        // Mock the behavior of washerRepository.findByPhoneNumber to return the mock Washer
	        when(washerRepository.findByPhoneNumber("1234567890")).thenReturn(Optional.of(washer));

	        // Call the updateWasherRating method with a valid phoneNumber and rating
	        Washer updatedWasher = washerService.updateWasherRating("1234567890", 5); 

	    }

	    @Test
	    public void testUpdateWasherRating_WasherNotFound() {
	        // Mock the behavior of washerRepository.findByPhoneNumber to return an empty result (Washer not found)
	        when(washerRepository.findByPhoneNumber("1234567890")).thenReturn(Optional.empty());

	        // Call the updateWasherRating method with a non-existent phoneNumber and expect an InvalidDetailsException
	        assertThrows(InvalidDetailsException.class, () ->washerService.updateWasherRating("1234567890", 5));

	        // Verify that washerRepository.findByPhoneNumber is called
	        verify(washerRepository, times(1)).findByPhoneNumber("1234567890");
	    }

	}