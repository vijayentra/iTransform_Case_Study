package com.customer.controller;

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

import com.customer.entity.Washer;
import com.customer.exception.InvalidDetailsException;
import com.customer.service.WasherService;

@RestController
@RequestMapping("/washer")
public class WasherController {
	@Autowired
	private WasherService washerService;
	
	@PostMapping("/add")
	public ResponseEntity<?> addWasher(@RequestBody Washer washer){
		Washer w = null;
		try {
		w = washerService.addWasher(washer);
		}catch(InvalidDetailsException e) {
			System.out.println(e.getMessage());
			return new ResponseEntity<>(e.getMessage()+ "Please try again!", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(w,HttpStatus.OK); 
	}
	
	@PutMapping("/updateWasher/{phoneNumber}")
	public ResponseEntity<?> updateWasher(@PathVariable String phoneNumber,@RequestBody Washer washer){
		Washer w = null;
		try {
		w = washerService.updateWasher(phoneNumber,washer);
		}catch(InvalidDetailsException  e) {
			return new ResponseEntity<>(e.getMessage()+ "Please try again!", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(w,HttpStatus.OK);
	}
	
	
	@DeleteMapping("/deleteWasher/{phoneNumber}")
	public ResponseEntity<?> deleteWasher(@PathVariable String phoneNumber){
		try {
			washerService.deleteWasher(phoneNumber);
		}catch(InvalidDetailsException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>("Washer detail deleted successfully. ",HttpStatus.OK);
	}
	
	
	@GetMapping("/viewWasher/{phoneNumber}")
	public ResponseEntity<?> viewWasher(@PathVariable String phoneNumber){
		Washer w = null;
		try {
			w = washerService.viewWasher(phoneNumber);
		}catch(InvalidDetailsException e) {
			return new ResponseEntity<>(e.getMessage() + "Try again! ", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(w,HttpStatus.OK);
	}
	
	@GetMapping("/viewAllWasher")
	public ResponseEntity<?> viewAllWasher(){
		List<Washer> list = null;
		try {
			list = washerService.viewAllWasher();
		}catch(InvalidDetailsException e) {
			return new ResponseEntity<>(e.getMessage() + "Try again! ", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(list,HttpStatus.OK);
	}
}
