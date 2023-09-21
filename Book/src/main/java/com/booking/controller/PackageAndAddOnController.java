package com.booking.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.booking.dummyentity.AddOnPrice;
import com.booking.dummyentity.PackagePrice;
import com.booking.dummyentity.DateTime;
import com.booking.entity.AddOns;
import com.booking.entity.Packages;
import com.booking.exception.PackageAndAddOnException;
import com.booking.service.PackageAddOnService;

@RestController
@RequestMapping("/packageAddOn")
public class PackageAndAddOnController {
	
	@Autowired
	private PackageAddOnService packageoddonService;
	
	@PostMapping("/addPackage")
	public ResponseEntity<?> addPackage(@RequestBody PackagePrice pp){
		Packages p = null;
		try {
		p = packageoddonService.addPackage(pp.getPackageName(), pp.getPrice());
		}catch(PackageAndAddOnException e) {
			System.out.println(e.getMessage());
			return new ResponseEntity<>(e.getMessage()+ "Please try again!", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(p,HttpStatus.OK); 
	}
	
	@PostMapping("/addAddOn")
	public ResponseEntity<?> addAddOn(@RequestBody AddOnPrice ap){
		AddOns a = null;
		try {
		a = packageoddonService.addAddOn(ap.getAddOn(), ap.getPrice());
		}catch(PackageAndAddOnException e) {
			System.out.println(e.getMessage());
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(a,HttpStatus.OK); 
	}
	
	@PutMapping("/updatePackage")
	public ResponseEntity<?> updatePackage(@RequestBody PackagePrice pp){
		Packages p = null;
		try {
		p = packageoddonService.updatePackage(pp.getPackageName(), pp.getPrice());
		}catch(PackageAndAddOnException e) {
			System.out.println(e.getMessage());
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(p,HttpStatus.OK); 
	}
	
	@PutMapping("/updateAddOn")
	public ResponseEntity<?> updateAddOn(@RequestBody AddOnPrice ap){
		AddOns a = null;
		try {
		a = packageoddonService.updateAddOn(ap.getAddOn(), ap.getPrice());
		}catch(PackageAndAddOnException e) {
			System.out.println(e.getMessage());
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(a,HttpStatus.OK); 
	}
	
	@GetMapping("/viewPackages")
	public ResponseEntity<?> viewPackages(){
		List<Packages> list = null;
		try {
		list = packageoddonService.viewPackages();
		}catch(PackageAndAddOnException e) {
			System.out.println(e.getMessage());
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(list,HttpStatus.OK); 
	}
	
	@GetMapping("/viewAddOns")
	public ResponseEntity<?> viewAddOns(){
		List<AddOns> list = null;
		try {
		list = packageoddonService.viewAddOns();
		}catch(PackageAndAddOnException e) {
			System.out.println(e.getMessage());
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(list,HttpStatus.OK); 
	}
	
	@DeleteMapping("/deletePackage")
	public ResponseEntity<?> deletePackage(@RequestBody PackagePrice rescheduleRequest){
		try {
		packageoddonService.deletePackage(rescheduleRequest.getPackageName());
		}catch(PackageAndAddOnException e) {
			System.out.println(e.getMessage());
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>("Package '"+rescheduleRequest.getPackageName()+"' deleted!",HttpStatus.OK); 
	}
	
	@DeleteMapping("/deleteAddOn")
	public ResponseEntity<?> deleteAddOn(@RequestBody AddOnPrice rescheduleRequest){
		try {
		packageoddonService.deleteAddOn(rescheduleRequest.getAddOn());
		}catch(PackageAndAddOnException e) {
			System.out.println(e.getMessage());
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>("AddOn '"+rescheduleRequest.getAddOn()+"' deleted!",HttpStatus.OK); 
	}
}
