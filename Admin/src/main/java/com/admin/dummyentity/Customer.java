package com.admin.dummyentity;

import java.util.ArrayList;
import java.util.List;


public class Customer {
	
	private String phoneNumber;
	private String password;
	private String firstName;
	private String lastName;
	private String pinCode;
	private List<CarDetails> carsList = new ArrayList<>();
	private double rating =0.0;
	private int washesDone = 0;
	
	
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPinCode() {
		return pinCode;
	}
	public void setPinCode(String pinCode) {
		this.pinCode = pinCode;
	}
	public double getRating() {
		return rating;
	}
	public void setRating(double rating) {
		this.rating = rating;
	}
	public int getWashesDone() {
		return washesDone;
	}
	public void setWashesDone(int washesDone) {
		this.washesDone = washesDone;
	}
	public List<CarDetails> getCarsList() {
		return carsList;
	}
	public void setCarsList(List<CarDetails> carsList) {
		this.carsList = carsList;
	}
	
}