package com.booking.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;


//@Document
public class BookingDetails {
	 private String carNumber;
	 private String washDate;
	 private String washTime;
	 private String washPackage;
	 private List<String> washAddOn = new ArrayList<>();
	 
	 private String customerName = null;
	 private String customerPhoneNumber = null;
	 private String washerPhoneNumber = null;
	 private String washerName = null;
	 private String bookingId = null;
	 private int washerRating = 0;
	 private int customerRating = 0;
	 private LocalDateTime bookingDateAndTime = LocalDateTime.now();
	 private String washStatus = "REQUESTED";
	 private double washCost = 0.0;
	 private double invoiceId = 0;
	 
//	public String getId() {
//		return id;
//	}
//	public void setId(String id) {
//		this.id = id;
//	}
	public String getBookingId() {
		return bookingId;
	}
	public void setBookingId(String bookingId) {
		this.bookingId = bookingId;
	}
	public String getCarNumber() {
		return carNumber;
	}
	public void setCarNumber(String carNumber) {
		this.carNumber = carNumber;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getWasherName() {
		return washerName;
	}
	public void setWasherName(String washerName) {
		this.washerName = washerName;
	}
	public int getWasherRating() {
		return washerRating;
	}
	public void setWasherRating(int washerRating) {
		this.washerRating = washerRating;
	}
	public int getCustomerRating() {
		return customerRating;
	}
	public void setCustomerRating(int customerRating) {
		this.customerRating = customerRating;
	}
	public LocalDateTime getBookingDateAndTime() {
		return bookingDateAndTime;
	}
	public void setBookingDateAndTime(LocalDateTime bookingDateAndTime) {
		this.bookingDateAndTime = bookingDateAndTime;
	}
	public String getWashDate() {
		return washDate;
	}
	public void setWashDate(String washDate) {
		this.washDate = washDate;
	}
	public String getWashTime() {
		return washTime;
	}
	public void setWashTime(String washTime) {
		this.washTime = washTime;
	}
	public String getWashStatus() {
		return washStatus;
	}
	public void setWashStatus(String washStatus) {
		this.washStatus = washStatus;
	}
	public String getWashPackage() {
		return washPackage;
	}
	public void setWashPackage(String washPackage) {
		this.washPackage = washPackage;
	}
	public List<String> getWashAddOn() {
		return washAddOn;
	}
	public void setWashAddOn(List<String> washAddOn) {
		this.washAddOn = washAddOn;
	}
	public double getInvoiceId() {
		return invoiceId;
	}
	public void setInvoiceId(double invoiceId) {
		this.invoiceId = invoiceId;
	}
	public double getWashCost() {
		return washCost;
	}
	public void setWashCost(double washCost) {
		this.washCost = washCost;
	}
	public String getCustomerPhoneNumber() {
		return customerPhoneNumber;
	}
	public void setCustomerPhoneNumber(String customerPhoneNumber) {
		this.customerPhoneNumber = customerPhoneNumber;
	}
	public String getWasherPhoneNumber() {
		return washerPhoneNumber;
	}
	public void setWasherPhoneNumber(String washerPhoneNumber) {
		this.washerPhoneNumber = washerPhoneNumber;
	}
}
