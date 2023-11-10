/**
 * 
 */
package com.admin.test;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.admin.controller.AdminController;
import com.admin.entity.AdminDetails;
import com.admin.entity.CustomerOverview;
import com.admin.entity.HistoryOfBookings;
import com.admin.entity.WasherOverview;
import com.admin.exception.InvalidAdminException;
import com.admin.service.AdminService;

class AdminControllerTest {
	
	@InjectMocks
    private AdminController adminController;

    @Mock
    private AdminService adminService;
    
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

	@Test
    public void testAddAdmin_AddSuccessfully() {
        // Create valid AdminDetails for testing
        AdminDetails adminDetails = new AdminDetails();
        adminDetails.setUsername("admin123");
        adminDetails.setPassword("password123");

        // Mock the behavior of adminService.addAdmin to return the mock AdminDetails
        when(adminService.addAdmin(adminDetails)).thenReturn(adminDetails);

        // Call the addAdmin method with valid AdminDetails
        ResponseEntity<?> responseEntity = adminController.addAdmin(adminDetails);

        // Verify that it returns an HTTP status code of 200 (OK)
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        // Optionally, you can verify the response body or message as well

        // Verify that adminService.addAdmin is called
        verify(adminService, times(1)).addAdmin(adminDetails);
    }

    @Test
    public void testAddAdmin_InvalidAdminDetails() {
        // Create invalid AdminDetails for testing (e.g., missing username)
        AdminDetails invalidAdminDetails = new AdminDetails();
        invalidAdminDetails.setPassword("password123");

        // Mock the behavior of adminService.addAdmin to throw an InvalidAdminException
        when(adminService.addAdmin(invalidAdminDetails)).thenThrow(InvalidAdminException.class);

        // Call the addAdmin method with invalid AdminDetails and expect an HTTP status code of 400 (Bad Request)
        ResponseEntity<?> responseEntity = adminController.addAdmin(invalidAdminDetails);

        // Verify that it returns an HTTP status code of 400 (Bad Request)
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());

        // Verify that adminService.addAdmin is called
        verify(adminService, times(1)).addAdmin(invalidAdminDetails);
    }
    
    @Test
    public void testUpdateAdmin_UpdateSuccessfully() {
        // Create valid AdminDetails for testing
        AdminDetails adminDetails = new AdminDetails();
        adminDetails.setUsername("admin123");
        adminDetails.setPassword("newpassword123");

        // Mock the behavior of adminService.updateAdmin to return the updated mock AdminDetails
        when(adminService.updateAdmin("admin123", adminDetails)).thenReturn(adminDetails);

        // Call the updateAdmin method with valid username and AdminDetails
        ResponseEntity<?> responseEntity = adminController.updateAdmin("admin123", adminDetails);

        // Verify that it returns an HTTP status code of 200 (OK)
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        // Optionally, you can verify the response body or message as well

        // Verify that adminService.updateAdmin is called
        verify(adminService, times(1)).updateAdmin("admin123", adminDetails);
    }

    @Test
    public void testUpdateAdmin_AdminNotFound() {
        // Create valid AdminDetails for testing
        AdminDetails adminDetails = new AdminDetails();
        adminDetails.setUsername("admin123");
        adminDetails.setPassword("newpassword123");

        // Mock the behavior of adminService.updateAdmin to throw an InvalidAdminException (Admin not found)
        when(adminService.updateAdmin("admin123", adminDetails)).thenThrow(InvalidAdminException.class);

        // Call the updateAdmin method with valid username and AdminDetails and expect an HTTP status code of 400 (Bad Request)
        ResponseEntity<?> responseEntity = adminController.updateAdmin("admin123", adminDetails);

        // Verify that it returns an HTTP status code of 400 (Bad Request)
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());

        // Verify that adminService.updateAdmin is called
        verify(adminService, times(1)).updateAdmin("admin123", adminDetails);
    }

    @Test
    public void testUpdateAdmin_InvalidAdminDetails() {
        // Create invalid AdminDetails for testing (e.g., missing username)
        AdminDetails invalidAdminDetails = new AdminDetails();
        invalidAdminDetails.setPassword("newpassword123");

        // Mock the behavior of adminService.updateAdmin to throw an InvalidAdminException (Invalid AdminDetails)
        when(adminService.updateAdmin("admin123", invalidAdminDetails)).thenThrow(InvalidAdminException.class);

        // Call the updateAdmin method with valid username and invalid AdminDetails and expect an HTTP status code of 400 (Bad Request)
        ResponseEntity<?> responseEntity = adminController.updateAdmin("admin123", invalidAdminDetails);

        // Verify that it returns an HTTP status code of 400 (Bad Request)
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());

        // Verify that adminService.updateAdmin is called
        verify(adminService, times(1)).updateAdmin("admin123", invalidAdminDetails);
    }
    
    @Test
    public void testDeleteAdmin_DeleteSuccessfully() {
        // Mock the behavior of adminService.deleteAdmin to delete the admin successfully
        doNothing().when(adminService).deleteAdmin("admin123");

        // Call the deleteAdmin method with a valid username
        ResponseEntity<?> responseEntity = adminController.deleteAdmin("admin123");

        // Verify that it returns an HTTP status code of 200 (OK)
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        // Verify that adminService.deleteAdmin is called
        verify(adminService, times(1)).deleteAdmin("admin123");
    }

    @Test
    public void testDeleteAdmin_AdminNotFound() {
        // Mock the behavior of adminService.deleteAdmin to throw an InvalidAdminException (Admin not found)
        doThrow(InvalidAdminException.class).when(adminService).deleteAdmin("admin123");

        // Call the deleteAdmin method with a non-existent username and expect an HTTP status code of 400 (Bad Request)
        ResponseEntity<?> responseEntity = adminController.deleteAdmin("admin123");

        // Verify that it returns an HTTP status code of 400 (Bad Request)
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());

        // Verify that adminService.deleteAdmin is called
        verify(adminService, times(1)).deleteAdmin("admin123");
    }
    
//    @Test
//    public void testViewAdmins_ViewSuccessfully() {
//        // Create a list of admin usernames for testing
//        List<String> adminList = new ArrayList<>();
//        adminList.add("admin1");
//        adminList.add("admin2");
//        adminList.add("admin3");
//
//        // Mock the behavior of adminService.viewAdmins to return the list of admin usernames
//        when(adminService.viewAdmins()).thenReturn(adminList);
//
//        // Call the viewAdmins method
//        ResponseEntity<?> responseEntity = adminController.viewAdmins();
//
//        // Verify that it returns an HTTP status code of 200 (OK)
//        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
//        // Verify the response body contains the expected list of admin usernames
//        assertEquals(adminList, responseEntity.getBody());
//
//        // Verify that adminService.viewAdmins is called
//        verify(adminService, times(1)).viewAdmins();
//    }
//
//    @Test
//    public void testViewAdmins_NoAdminsFound() {
//        // Mock the behavior of adminService.viewAdmins to throw an InvalidAdminException (No admins found)
//        when(adminService.viewAdmins()).thenThrow(InvalidAdminException.class);
//
//        // Call the viewAdmins method and expect an HTTP status code of 400 (Bad Request)
//        ResponseEntity<?> responseEntity = adminController.viewAdmins();
//
//        // Verify that it returns an HTTP status code of 400 (Bad Request)
//        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
//
//        // Verify that adminService.viewAdmins is called
//        verify(adminService, times(1)).viewAdmins();
//    }
    
    @Test
    public void testViewCustomerOverview_ViewSuccessfully() {
        // Create a list of customer overviews for testing
        List<CustomerOverview> customerOverviewList = new ArrayList<>();
        // Add sample customer overviews to the list

        // Mock the behavior of adminService.viewCustomerOverview to return the list of customer overviews
        when(adminService.viewCustomerOverview()).thenReturn(customerOverviewList);

        // Call the viewCustomerOverview method
        ResponseEntity<?> responseEntity = adminController.viewCustomerOverview();

        // Verify that it returns an HTTP status code of 200 (OK)
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        // Verify the response body contains the expected list of customer overviews
        assertEquals(customerOverviewList, responseEntity.getBody());

        // Verify that adminService.viewCustomerOverview is called
        verify(adminService, times(1)).viewCustomerOverview();
    }

    @Test
    public void testViewCustomerOverview_NoOverviewsFound() {
        // Mock the behavior of adminService.viewCustomerOverview to throw an InvalidAdminException (No customer overviews found)
        when(adminService.viewCustomerOverview()).thenThrow(InvalidAdminException.class);

        // Call the viewCustomerOverview method and expect an HTTP status code of 400 (Bad Request)
        ResponseEntity<?> responseEntity = adminController.viewCustomerOverview();

        // Verify that it returns an HTTP status code of 400 (Bad Request)
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());

        // Verify that adminService.viewCustomerOverview is called
        verify(adminService, times(1)).viewCustomerOverview();
    }

    @Test
    public void testViewWasherOverview_ViewSuccessfully() {
        // Create a list of washer overviews for testing
        List<WasherOverview> washerOverviewList = new ArrayList<>();
        // Add sample washer overviews to the list

        // Mock the behavior of adminService.viewWasherOverview to return the list of washer overviews
        when(adminService.viewWasherOverview()).thenReturn(washerOverviewList);

        // Call the viewWasherOverview method
        ResponseEntity<?> responseEntity = adminController.viewWasherOverview();

        // Verify that it returns an HTTP status code of 200 (OK)
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        // Verify the response body contains the expected list of washer overviews
        assertEquals(washerOverviewList, responseEntity.getBody());

        // Verify that adminService.viewWasherOverview is called
        verify(adminService, times(1)).viewWasherOverview();
    }

    @Test
    public void testViewWasherOverview_NoOverviewsFound() {
        // Mock the behavior of adminService.viewWasherOverview to throw an InvalidAdminException (No washer overviews found)
        when(adminService.viewWasherOverview()).thenThrow(InvalidAdminException.class);

        // Call the viewWasherOverview method and expect an HTTP status code of 400 (Bad Request)
        ResponseEntity<?> responseEntity = adminController.viewWasherOverview();

        // Verify that it returns an HTTP status code of 400 (Bad Request)
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());

        // Verify that adminService.viewWasherOverview is called
        verify(adminService, times(1)).viewWasherOverview();
    }
    
    @Test
    public void testViewBookingHistory_ViewSuccessfully() {
        // Create a list of booking history for testing
        List<HistoryOfBookings> bookingHistoryList = new ArrayList<>();
        // Add sample booking history entries to the list

        // Mock the behavior of adminService.viewBookingHistory to return the list of booking history
        when(adminService.viewBookingHistory()).thenReturn(bookingHistoryList);

        // Call the viewBookingHistory method
        ResponseEntity<?> responseEntity = adminController.viewBookingHistory();

        // Verify that it returns an HTTP status code of 200 (OK)
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        // Verify the response body contains the expected list of booking history entries
        assertEquals(bookingHistoryList, responseEntity.getBody());

        // Verify that adminService.viewBookingHistory is called
        verify(adminService, times(1)).viewBookingHistory();
    }

    @Test
    public void testViewBookingHistory_NoHistoryFound() {
        // Mock the behavior of adminService.viewBookingHistory to throw an InvalidAdminException (No booking history found)
        when(adminService.viewBookingHistory()).thenThrow(InvalidAdminException.class);

        // Call the viewBookingHistory method and expect an HTTP status code of 400 (Bad Request)
        ResponseEntity<?> responseEntity = adminController.viewBookingHistory();

        // Verify that it returns an HTTP status code of 400 (Bad Request)
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());

        // Verify that adminService.viewBookingHistory is called
        verify(adminService, times(1)).viewBookingHistory();
    }
}
