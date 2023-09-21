package com.admin.entity;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity
public class CustomerOverview {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	private String phoneNumber;
	private String name;
	private String pinCode;
	private int noOfCars;
	private int washesDone = 0;
	private double rating =0.0;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPinCode() {
		return pinCode;
	}
	public void setPinCode(String pinCode) {
		this.pinCode = pinCode;
	}
	public int getNoOfCars() {
		return noOfCars;
	}
	public void setNoOfCars(int noOfCars) {
		this.noOfCars = noOfCars;
	}
	public int getWashesDone() {
		return washesDone;
	}
	public void setWashesDone(int washesDone) {
		this.washesDone = washesDone;
	}
	public double getRating() {
		return rating;
	}
	public void setRating(double rating) {
		this.rating = rating;
	}
}
