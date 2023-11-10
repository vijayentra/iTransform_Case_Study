package com.admin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.admin.entity.AdminDetails;
import com.admin.entity.CustomerOverview;
import com.admin.entity.HistoryOfBookings;
import com.admin.entity.WasherOverview;
import com.admin.exception.InvalidAdminException;
import com.admin.service.AdminService;

@RestController
@RequestMapping("/admin")
@CrossOrigin("*")
public class AdminController {
	
	@Autowired
	private AdminService adminService;
	
	@GetMapping("/loginAdmin/{username}/{password}")
	public ResponseEntity<?> loginAdmin(@PathVariable String username, @PathVariable String password){
		AdminDetails ad = null;
		try {
		ad = adminService.login(username, password);
		}catch(InvalidAdminException e) {
			System.out.println(e.getMessage());
			return new ResponseEntity<>(e.getMessage()+ "Please try again!", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(ad ,HttpStatus.OK); 
	}
	
	
	@PostMapping("/addAdmin")
	public ResponseEntity<?> addAdmin(@RequestBody AdminDetails adminDetails){
		AdminDetails ad = null;
		try {
		ad = adminService.addAdmin(adminDetails);
		}catch(InvalidAdminException e) {
			System.out.println(e.getMessage());
			return new ResponseEntity<>(e.getMessage()+ "Please try again!", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>("Admin added successfully. ",HttpStatus.OK); 
	}
	
	@PutMapping("/updateAdmin/{username}")
	public ResponseEntity<?> updateAdmin(@PathVariable String username, @RequestBody AdminDetails adminDetails){
		AdminDetails ad = null;
		try {
		ad = adminService.updateAdmin(username,adminDetails);
		}catch(InvalidAdminException e) {
			System.out.println(e.getMessage());
			return new ResponseEntity<>(e.getMessage()+ "Please try again!", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(ad ,HttpStatus.OK); 
	}
	
	@DeleteMapping("/deleteAdmin/{username}")
	public ResponseEntity<?> deleteAdmin(@PathVariable String username){
		try {
		adminService.deleteAdmin(username);
		}catch(InvalidAdminException e) {
			System.out.println(e.getMessage());
			return new ResponseEntity<>(e.getMessage()+ "Please try again!", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>("Admin deleted successfully. ",HttpStatus.OK); 
	}
	
	@GetMapping("/viewAdmin/{username}")
	public ResponseEntity<?> viewAdmin(@PathVariable String username){
		AdminDetails ad = null;
		try {
		ad=  adminService.viewAdmin(username);
		}catch(InvalidAdminException e) {
			System.out.println(e.getMessage());
			return new ResponseEntity<>(e.getMessage()+ "Please try again!", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(ad,HttpStatus.OK); 
	}
	
	@GetMapping("/viewCustomerOverview")
	public ResponseEntity<?> viewCustomerOverview(){
		List<CustomerOverview> list = null;
		try {
		list =  adminService.viewCustomerOverview();
		}catch(InvalidAdminException e) {
			System.out.println(e.getMessage());
			return new ResponseEntity<>(e.getMessage()+ "Please try again!", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(list,HttpStatus.OK); 
	}
	
	@GetMapping("/viewWasherOverview")
	public ResponseEntity<?> viewWasherOverview(){
		List<WasherOverview> list = null;
		try {
		list =  adminService.viewWasherOverview();
		}catch(InvalidAdminException e) {
			System.out.println(e.getMessage());
			return new ResponseEntity<>(e.getMessage()+ "Please try again!", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(list,HttpStatus.OK); 
	}
	
	@GetMapping("/viewHistory")
	public ResponseEntity<?> viewBookingHistory(){
		List<HistoryOfBookings> list = null;
		try {
		list =  adminService.viewBookingHistory();
		}catch(InvalidAdminException e) {
			System.out.println(e.getMessage());
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(list,HttpStatus.OK); 
	}
	
}
