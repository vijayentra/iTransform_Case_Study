package com.booking.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import com.booking.entity.AddOns;
import com.booking.entity.Packages;
import com.booking.exception.PackageAndAddOnException;
import com.booking.repository.AddOnsRepository;
import com.booking.repository.PackagesRepository;
import com.booking.service.PackageAddOnServiceImpl;

class PackageAddOnServiceImplTest {

	@InjectMocks
    private PackageAddOnServiceImpl packageAddOnService;

    @Mock
    private PackagesRepository packagesRepository;
    
    @Mock
    private AddOnsRepository addonsRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testAddPackage_Successful() {
        // Mock the behavior of packagesRepository.findAll to return an empty list
        when(packagesRepository.findAll()).thenReturn(new ArrayList<>());

        // Create a package to add
        String packageDeal = "Package1"; 
        double price = 50.0;
        Packages packageToAdd = new Packages();
        packageToAdd.setPackageName(packageDeal);
        packageToAdd.setPrice(price);

        // Mock the behavior of packagesRepository.save to return the package
        when(packagesRepository.save(packageToAdd)).thenReturn(packageToAdd);

        // Call the addPackage method
        Packages addedPackage = packageAddOnService.addPackage(packageDeal, price);

        // Verify that the addedPackage is not null
        assertNotNull(addedPackage);
    }

    @Test
    public void testAddPackage_Unsuccessful_DuplicatePackage() {
        // Mock the behavior of packagesRepository.findAll to return a list with a package with the same name
        String packageDeal = "Package1";  
        double price = 50.0;
        List<Packages> existingPackages = new ArrayList<>();
        Packages existingPackage = new Packages();
        existingPackage.setPackageName(packageDeal);
        existingPackage.setPrice(price);
        existingPackages.add(existingPackage);
        when(packagesRepository.findAll()).thenReturn(existingPackages);

        PackageAndAddOnException exception = assertThrows(PackageAndAddOnException.class, () ->
            packageAddOnService.addPackage(packageDeal, price));

        // Verify that the exception message contains the expected error message
        assertEquals("Package already exists with the given name. ", exception.getMessage());
    }

    @Test
    public void testAddAddOn_Success() {
        String addOnName = "TestAddOn";
        double price = 10.0;

        // Mock the behavior of addonsRepository.findAll()
        when(addonsRepository.findAll()).thenReturn(new ArrayList<>());

        AddOns expectedAddOn = new AddOns();
        expectedAddOn.setAddOnName(addOnName);
        expectedAddOn.setPrice(price);

        // Mock the behavior of addonsRepository.save()
        when(addonsRepository.save(any(AddOns.class))).thenReturn(expectedAddOn);

        AddOns result = packageAddOnService.addAddOn(addOnName, price);

        assertNotNull(result);
        assertEquals(addOnName, result.getAddOnName());
        assertEquals(price, result.getPrice());

        // Verify that addonsRepository.findAll() and addonsRepository.save() were called
        verify(addonsRepository, times(1)).findAll();
        verify(addonsRepository, times(1)).save(any(AddOns.class));
    }

    @Test
    public void testAddAddOn_AddOnAlreadyExists() {
        String addOnName = "TestAddOn";
        double price = 10.0;

        List<AddOns> existingAddOns = new ArrayList<>();
        AddOns existingAddOn = new AddOns();
        existingAddOn.setAddOnName(addOnName);
        existingAddOns.add(existingAddOn);

        // Mock the behavior of addonsRepository.findAll()
        when(addonsRepository.findAll()).thenReturn(existingAddOns);

        // Ensure that PackageAndAddOnException is thrown when adding a duplicate AddOn
        assertThrows(PackageAndAddOnException.class, () -> {
            packageAddOnService.addAddOn(addOnName, price);
        });

        // Verify that addonsRepository.findAll() was called
        verify(addonsRepository, times(1)).findAll();
    }
    
    @Test
    public void testUpdatePackage_Success() {
        String packageDeal = "TestPackage";
        double price = 20.0;

        Packages existingPackage = new Packages();
        existingPackage.setPackageName(packageDeal);

        // Mock the behavior of packagesRepository.findByPackageName()
        when(packagesRepository.findByPackageName(packageDeal)).thenReturn(existingPackage);

        Packages updatedPackage = packageAddOnService.updatePackage(packageDeal, price);

        assertNotNull(updatedPackage);
        assertEquals(packageDeal, updatedPackage.getPackageName());
        assertEquals(price, updatedPackage.getPrice());

        // Verify that packagesRepository.findByPackageName() and packagesRepository.save() were called
        verify(packagesRepository, times(1)).findByPackageName(packageDeal);
        verify(packagesRepository, times(1)).save(existingPackage);
    }

    @Test
    public void testUpdatePackage_PackageNotFound() {
        String packageDeal = "NonExistentPackage";
        double price = 20.0;

        // Mock the behavior of packagesRepository.findByPackageName() returning null
        when(packagesRepository.findByPackageName(packageDeal)).thenReturn(null);

        // Ensure that PackageAndAddOnException is thrown when updating a non-existent Package
        assertThrows(PackageAndAddOnException.class, () -> {
            packageAddOnService.updatePackage(packageDeal, price);
        });

        // Verify that packagesRepository.findByPackageName() was called
        verify(packagesRepository, times(1)).findByPackageName(packageDeal);
    }
    
    @Test
    public void testUpdateAddOn_Success() {
        String addOnName = "TestAddOn";
        double price = 15.0;

        AddOns existingAddOn = new AddOns();
        existingAddOn.setAddOnName(addOnName);

        // Mock the behavior of addonsRepository.findByAddOnName()
        when(addonsRepository.findByAddOnName(addOnName)).thenReturn(existingAddOn);

        AddOns updatedAddOn = packageAddOnService.updateAddOn(addOnName, price);

        assertNotNull(updatedAddOn);
        assertEquals(addOnName, updatedAddOn.getAddOnName());
        assertEquals(price, updatedAddOn.getPrice());

        // Verify that addonsRepository.findByAddOnName() and addonsRepository.save() were called
        verify(addonsRepository, times(1)).findByAddOnName(addOnName);
        verify(addonsRepository, times(1)).save(existingAddOn);
    }

    @Test
    public void testUpdateAddOn_AddOnNotFound() {
        String addOnName = "NonExistentAddOn";
        double price = 15.0;

        // Mock the behavior of addonsRepository.findByAddOnName() returning null
        when(addonsRepository.findByAddOnName(addOnName)).thenReturn(null);

        // Ensure that PackageAndAddOnException is thrown when updating a non-existent AddOn
        assertThrows(PackageAndAddOnException.class, () -> {
            packageAddOnService.updateAddOn(addOnName, price);
        });

        // Verify that addonsRepository.findByAddOnName() was called
        verify(addonsRepository, times(1)).findByAddOnName(addOnName);
    }
    
    @Test
    public void testViewPackages_Success() {
        // Mock a list of packages
        List<Packages> packagesList = new ArrayList<>();
        Packages p1 = new Packages();
        p1.setPackageName("new");
        p1.setPrice(2000);
        Packages p2 = new Packages();
        p2.setPackageName("new2");
        p2.setPrice(2000);
        packagesList.add(p1);
        packagesList.add(p2);

        // Mock the behavior of packagesRepository.findAll()
        when(packagesRepository.findAll()).thenReturn(packagesList);

        List<Packages> result = packageAddOnService.viewPackages();

        assertNotNull(result);
        assertEquals(2, result.size());

        // Verify that packagesRepository.findAll() was called
        verify(packagesRepository, times(1)).findAll();
    }

    @Test
    public void testViewPackages_EmptyList() {
        // Mock an empty list of packages
        List<Packages> packagesList = new ArrayList<>();

        // Mock the behavior of packagesRepository.findAll() returning an empty list
        when(packagesRepository.findAll()).thenReturn(packagesList);

        // Ensure that PackageAndAddOnException is thrown when the list is empty
        assertThrows(PackageAndAddOnException.class, () -> {
            packageAddOnService.viewPackages();
        });

        // Verify that packagesRepository.findAll() was called
        verify(packagesRepository, times(1)).findAll();
    }

    @Test
    public void testViewAddOns_Success() {
        // Mock a list of add-ons
        List<AddOns> addonsList = new ArrayList<>();
        AddOns a1 = new AddOns();
        a1.setAddOnName("interior");
        a1.setPrice(800);
        AddOns a2 = new AddOns();
        a2.setAddOnName("wheel");
        a2.setPrice(500);
        addonsList.add(a1);
        addonsList.add(a2);

        // Mock the behavior of addonsRepository.findAll()
        when(addonsRepository.findAll()).thenReturn(addonsList);

        List<AddOns> result = packageAddOnService.viewAddOns();

        assertNotNull(result);
        assertEquals(2, result.size());

        // Verify that addonsRepository.findAll() was called
        verify(addonsRepository, times(1)).findAll();
    }

    @Test
    public void testViewAddOns_EmptyList() {
        // Mock an empty list of add-ons
        List<AddOns> addonsList = new ArrayList<>();

        // Mock the behavior of addonsRepository.findAll() returning an empty list
        when(addonsRepository.findAll()).thenReturn(addonsList);

        // Ensure that PackageAndAddOnException is thrown when the list is empty
        assertThrows(PackageAndAddOnException.class, () -> 
            packageAddOnService.viewAddOns());

        // Verify that addonsRepository.findAll() was called
        verify(addonsRepository, times(1)).findAll();
    }
    
    @Test
    public void testDeletePackage_Success() {
        String packageName = "Package1";
        Packages packageToDelete = new Packages();
        packageToDelete.setPackageName(packageName);
        packageToDelete.setPrice(2000);

        // Mock the behavior of packagesRepository.findByPackageName()
        when(packagesRepository.findByPackageName(packageName)).thenReturn(packageToDelete);

        packageAddOnService.deletePackage(packageName);

        // Verify that packagesRepository.delete() was called with the correct packageToDelete
        verify(packagesRepository, times(1)).delete(packageToDelete);
    }

    @Test
    public void testDeletePackage_PackageNotFound() {
        String packageName = "NonExistentPackage";

        // Mock the behavior of packagesRepository.findByPackageName() returning null
        when(packagesRepository.findByPackageName(packageName)).thenReturn(null);

        // Ensure that PackageAndAddOnException is thrown when the package is not found
        assertThrows(PackageAndAddOnException.class, () -> {
            packageAddOnService.deletePackage(packageName);
        });

        // Verify that packagesRepository.findByPackageName() was called
        verify(packagesRepository, times(1)).findByPackageName(packageName);
        // Verify that packagesRepository.delete() was not called
        verify(packagesRepository, never()).delete(any());
    }

    @Test
    public void testDeleteAddOn_Success() {
        String addOnName = "AddOn1";
        AddOns addOnToDelete = new AddOns();
        addOnToDelete.setAddOnName(addOnName);
        addOnToDelete.setPrice(800);

        // Mock the behavior of addonsRepository.findByAddOnName()
        when(addonsRepository.findByAddOnName(addOnName)).thenReturn(addOnToDelete);

        packageAddOnService.deleteAddOn(addOnName);

        // Verify that addonsRepository.delete() was called with the correct addOnToDelete
        verify(addonsRepository, times(1)).delete(addOnToDelete);
    }

    @Test
    public void testDeleteAddOn_AddOnNotFound() {
        String addOnName = "NonExistentAddOn";

        // Mock the behavior of addonsRepository.findByAddOnName() returning null
        when(addonsRepository.findByAddOnName(addOnName)).thenReturn(null);

        // Ensure that PackageAndAddOnException is thrown when the add-on is not found
        assertThrows(PackageAndAddOnException.class, () -> {
            packageAddOnService.deleteAddOn(addOnName);
        });

        // Verify that addonsRepository.findByAddOnName() was called
        verify(addonsRepository, times(1)).findByAddOnName(addOnName);
        // Verify that addonsRepository.delete() was not called
        verify(addonsRepository, never()).delete(any());
    }
}
