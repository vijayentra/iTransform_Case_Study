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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.booking.controller.PackageAndAddOnController;
import com.booking.dummyentity.AddOnPrice;
import com.booking.dummyentity.PackagePrice;
import com.booking.entity.AddOns;
import com.booking.entity.Packages;
import com.booking.exception.PackageAndAddOnException;
import com.booking.service.PackageAddOnService;

class PackageAndAddOnControllerTest {

	@InjectMocks
    private PackageAndAddOnController packageAndAddOnController;

    @Mock
    private PackageAddOnService packageAddOnService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testAddPackage_Successful() {
        // Create valid PackagePrice for testing
        PackagePrice packagePrice = new PackagePrice();
        packagePrice.setPackageName("DELUX");
        packagePrice.setPrice(1000);

        // Create a Packages object to represent the expected result
        Packages expectedPackage = new Packages();
        expectedPackage.setPackageName(packagePrice.getPackageName());
        expectedPackage.setPrice(packagePrice.getPrice());

        // Mock the behavior of packageAddOnService.addPackage to return the expected package
        when(packageAddOnService.addPackage(packagePrice.getPackageName(), packagePrice.getPrice())).thenReturn(expectedPackage);

        // Call the addPackage method in the controller
        ResponseEntity<?> responseEntity = packageAndAddOnController.addPackage(packagePrice);

        // Verify that it returns an HTTP status code of 200 (OK)
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        // Optionally, you can verify the response body or message as well
        Packages actualPackage = (Packages) responseEntity.getBody();
        assertEquals(expectedPackage, actualPackage);

        // Verify that packageAddOnService.addPackage is called
        verify(packageAddOnService, times(1)).addPackage(packagePrice.getPackageName(), packagePrice.getPrice());
    }

//    @Test
//    public void testAddPackage_Unsuccessful() {
//        // Create invalid PackagePrice for testing (e.g., missing package name)
//    	 PackagePrice invalidPackagePrice = new PackagePrice();
//    	 invalidPackagePrice.setPackageName("DELUX");
//    	 invalidPackagePrice.setPrice(1000);
//
//        // Mock the behavior of packageAddOnService.addPackage to throw a PackageAndAddOnException
//        when(packageAddOnService.addPackage(null, 50.0)).thenThrow(new PackageAndAddOnException("Invalid package name"));
//
//        // Call the addPackage method in the controller with invalid input
//        ResponseEntity<?> responseEntity = packageAndAddOnController.addPackage(invalidPackagePrice);
//
//        // Optionally, verify the response body or message
//        String errorMessage = (String) responseEntity.getBody();
//        assertEquals("Invalid package name", errorMessage);
//
//        // Verify that packageAddOnService.addPackage is called
//        verify(packageAddOnService, times(1)).addPackage(null, 50.0);
//    }	 

    @Test
    public void testAddAddOn_Successful() {
        // Create valid AddOnPrice for testing
        AddOnPrice addOnPrice = new AddOnPrice();
        addOnPrice.setAddOn("outerwash");
        addOnPrice.setPrice(500);

        // Create an AddOns object to represent the expected result
        AddOns expectedAddOn = new AddOns();
        expectedAddOn.setAddOnName(addOnPrice.getAddOn());
        expectedAddOn.setPrice(addOnPrice.getPrice());

        // Mock the behavior of packageAddOnService.addAddOn to return the expected add-on
        when(packageAddOnService.addAddOn(addOnPrice.getAddOn(), addOnPrice.getPrice())).thenReturn(expectedAddOn);

        // Call the addAddOn method in the controller
        ResponseEntity<?> responseEntity = packageAndAddOnController.addAddOn(addOnPrice);

        // Verify that it returns an HTTP status code of 200 (OK)
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        // Optionally, you can verify the response body or message as well
        AddOns actualAddOn = (AddOns) responseEntity.getBody();
        assertEquals(expectedAddOn, actualAddOn);

        // Verify that packageAddOnService.addAddOn is called
        verify(packageAddOnService, times(1)).addAddOn(addOnPrice.getAddOn(), addOnPrice.getPrice());
    }

//    @Test
//    public void testAddAddOn_Unsuccessful() {
//        // Create invalid AddOnPrice for testing (e.g., missing add-on name)
//    	 AddOnPrice invalidAddOnPrice = new AddOnPrice();
////    	 invalidAddOnPrice.setAddOn("outerwash");
//    	 invalidAddOnPrice.setPrice(500);
//
//        // Mock the behavior of packageAddOnService.addAddOn to throw a PackageAndAddOnException
//        when(packageAddOnService.addAddOn(null, 20.0)).thenThrow(new PackageAndAddOnException("Invalid add-on name"));
//
//        // Call the addAddOn method in the controller with invalid input
//        ResponseEntity<?> responseEntity = packageAndAddOnController.addAddOn(invalidAddOnPrice);
//
//
//        // Optionally, verify the response body or message
//        String errorMessage = (String) responseEntity.getBody();
//        assertEquals("Invalid add-on name. Please try again!", errorMessage);
//
//        // Verify that packageAddOnService.addAddOn is called
//        verify(packageAddOnService, times(1)).addAddOn(null, 20.0);
//    }
    
    @Test
    public void testUpdatePackage_Successful() {
        // Create valid PackagePrice for testing
    	PackagePrice packagePrice = new PackagePrice();
        packagePrice.setPackageName("DELUX");
        packagePrice.setPrice(1000);

        // Create a Packages object to represent the expected result
        Packages expectedPackage = new Packages();
        expectedPackage.setPackageName(packagePrice.getPackageName());
        expectedPackage.setPrice(packagePrice.getPrice());

        // Mock the behavior of packageAddOnService.updatePackage to return the expected package
        when(packageAddOnService.updatePackage(packagePrice.getPackageName(), packagePrice.getPrice())).thenReturn(expectedPackage);

        // Call the updatePackage method in the controller
        ResponseEntity<?> responseEntity = packageAndAddOnController.updatePackage(packagePrice);

        // Verify that it returns an HTTP status code of 200 (OK)
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        // Optionally, you can verify the response body or message as well
        Packages actualPackage = (Packages) responseEntity.getBody();
        assertEquals(expectedPackage, actualPackage);

        // Verify that packageAddOnService.updatePackage is called
        verify(packageAddOnService, times(1)).updatePackage(packagePrice.getPackageName(), packagePrice.getPrice());
    }

//    @Test
//    public void testUpdatePackage_Unsuccessful() {
//        // Create invalid PackagePrice for testing (e.g., missing package name)
//    	PackagePrice invalidPackagePrice = new PackagePrice();
//    	invalidPackagePrice.setPrice(1000);
//
//        // Mock the behavior of packageAddOnService.updatePackage to throw a PackageAndAddOnException
//        when(packageAddOnService.updatePackage(null, 30.0)).thenThrow(new PackageAndAddOnException("Invalid package name"));
//
//        // Call the updatePackage method in the controller with invalid input
//        ResponseEntity<?> responseEntity = packageAndAddOnController.updatePackage(invalidPackagePrice);
//
//        // Optionally, verify the response body or message
//        String errorMessage = (String) responseEntity.getBody();
//        assertEquals("Invalid package name. Please try again!", errorMessage);
//
//        // Verify that packageAddOnService.updatePackage is called
//        verify(packageAddOnService, times(1)).updatePackage(null, 30.0);
//    }
    
    @Test
    public void testUpdateAddOn_Successful() {
        // Create valid AddOnPrice for testing
    	 AddOnPrice addOnPrice = new AddOnPrice();
         addOnPrice.setAddOn("outerwash");
         addOnPrice.setPrice(500);

        // Create an AddOns object to represent the expected result
        AddOns expectedAddOn = new AddOns();
        expectedAddOn.setAddOnName(addOnPrice.getAddOn());
        expectedAddOn.setPrice(addOnPrice.getPrice());

        // Mock the behavior of packageAddOnService.updateAddOn to return the expected add-on
        when(packageAddOnService.updateAddOn(addOnPrice.getAddOn(), addOnPrice.getPrice())).thenReturn(expectedAddOn);

        // Call the updateAddOn method in the controller
        ResponseEntity<?> responseEntity = packageAndAddOnController.updateAddOn(addOnPrice);

        // Verify that it returns an HTTP status code of 200 (OK)
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        // Optionally, you can verify the response body or message as well
        AddOns actualAddOn = (AddOns) responseEntity.getBody();
        assertEquals(expectedAddOn, actualAddOn);

        // Verify that packageAddOnService.updateAddOn is called
        verify(packageAddOnService, times(1)).updateAddOn(addOnPrice.getAddOn(), addOnPrice.getPrice());
    }

//    @Test
//    public void testUpdateAddOn_Unsuccessful() {
//        // Create invalid AddOnPrice for testing (e.g., missing add-on name)
//    	 AddOnPrice invalidAddOnPrice = new AddOnPrice();
////    	 invalidAddOnPrice.setAddOn("outerwash");
//    	 invalidAddOnPrice.setPrice(500);
//    	 
//        // Mock the behavior of packageAddOnService.updateAddOn to throw a PackageAndAddOnException
//        when(packageAddOnService.updateAddOn(null, 10.0)).thenThrow(new PackageAndAddOnException("Invalid add-on name"));
//
//        // Call the updateAddOn method in the controller with invalid input
//        ResponseEntity<?> responseEntity = packageAndAddOnController.updateAddOn(invalidAddOnPrice);
//
//
//        // Optionally, verify the response body or message
//        String errorMessage = (String) responseEntity.getBody();
//        assertEquals("Invalid add-on name. Please try again!", errorMessage);
//
//        // Verify that packageAddOnService.updateAddOn is called
//        verify(packageAddOnService, times(1)).updateAddOn(null, 10.0);
//    }
    
    @Test
    public void testViewPackages_Successful() {
        // Create a sample list of packages for testing
        List<Packages> packagesList = new ArrayList<>();
        Packages package1 = new Packages();
        package1.setPackageName("Package 1");
        package1.setPrice(20.0);
        Packages package2 = new Packages();
        package2.setPackageName("Package 2");
        package2.setPrice(30.0);
        packagesList.add(package1);
        packagesList.add(package2);

        // Mock the behavior of packageAddOnService.viewPackages to return the sample packages
        when(packageAddOnService.viewPackages()).thenReturn(packagesList);

        // Call the viewPackages method in the controller
        ResponseEntity<?> responseEntity = packageAndAddOnController.viewPackages();

        // Verify that it returns an HTTP status code of 200 (OK)
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        // Optionally, verify the response body
        List<Packages> actualPackagesList = (List<Packages>) responseEntity.getBody();
        assertEquals(packagesList, actualPackagesList);

        // Verify that packageAddOnService.viewPackages is called
        verify(packageAddOnService, times(1)).viewPackages();
    }

    @Test
    public void testViewAddOns_Successful() {
        // Create a sample list of add-ons for testing
        List<AddOns> addOnsList = new ArrayList<>();
        AddOns addOn1 = new AddOns();
        addOn1.setAddOnName("Add-On 1");
        addOn1.setPrice(10.0);
        AddOns addOn2 = new AddOns();
        addOn2.setAddOnName("Add-On 2");
        addOn2.setPrice(15.0);
        addOnsList.add(addOn1);
        addOnsList.add(addOn2);

        // Mock the behavior of packageAddOnService.viewAddOns to return the sample add-ons
        when(packageAddOnService.viewAddOns()).thenReturn(addOnsList);

        // Call the viewAddOns method in the controller
        ResponseEntity<?> responseEntity = packageAndAddOnController.viewAddOns();

        // Verify that it returns an HTTP status code of 200 (OK)
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        // Optionally, verify the response body
        List<AddOns> actualAddOnsList = (List<AddOns>) responseEntity.getBody();
        assertEquals(addOnsList, actualAddOnsList);

        // Verify that packageAddOnService.viewAddOns is called
        verify(packageAddOnService, times(1)).viewAddOns();
    }
    
    @Test
    public void testDeletePackage_Successful() {
        // Mock the behavior of packageAddOnService.deletePackage to not throw an exception
        doNothing().when(packageAddOnService).deletePackage(anyString());

        // Create a sample request
        PackagePrice request = new PackagePrice();
        request.setPackageName("Package 1");

        // Call the deletePackage method in the controller
        ResponseEntity<?> responseEntity = packageAndAddOnController.deletePackage(request);

        // Verify that it returns an HTTP status code of 200 (OK)
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        // Optionally, verify the response body or message as well

        // Verify that packageAddOnService.deletePackage is called
        verify(packageAddOnService, times(1)).deletePackage(request.getPackageName());
    }

    @Test
    public void testDeleteAddOn_Successful() {
        // Mock the behavior of packageAddOnService.deleteAddOn to not throw an exception
        doNothing().when(packageAddOnService).deleteAddOn(anyString());

        // Create a sample request
        AddOnPrice request = new AddOnPrice();
        request.setAddOn("Add-On 1");

        // Call the deleteAddOn method in the controller
        ResponseEntity<?> responseEntity = packageAndAddOnController.deleteAddOn(request);

        // Verify that it returns an HTTP status code of 200 (OK)
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        // Optionally, verify the response body or message as well

        // Verify that packageAddOnService.deleteAddOn is called
        verify(packageAddOnService, times(1)).deleteAddOn(request.getAddOn());
    }
}
