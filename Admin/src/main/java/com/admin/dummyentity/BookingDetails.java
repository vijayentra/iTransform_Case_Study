package com.admin.dummyentity;

import java.util.ArrayList;
import java.util.List;

public class BookingDetails {
	
	 private String bookingDateAndTime = null;
	 private String bookingId = null;
	 
	 private String carNumber;
	 private String washDate;
	 private String washTime;
	 private String washPackage;
	 private List<String> washAddOn = new ArrayList<>();
	 
	 private String customerName = null;
	 private String customerPhoneNumber = null;
	 private double customerRating;
	 private String washerName = null;
	 private String washerPhoneNumber = null;
	 private double washerRating;
	 private String washStatus = "REQUESTED";
	 private int washerRatingGiven;
	 private int customerRatingGiven;
	 private InvoiceDetails invoice = null;
	 
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
	public double getWasherRating() {
		return washerRating;
	}
	public void setWasherRating(double washerRating) {
		this.washerRating = washerRating;
	}
	public double getCustomerRating() {
		return customerRating;
	}
	public void setCustomerRating(double customerRating) {
		this.customerRating = customerRating;
	}
	public String getBookingDateAndTime() {
		return bookingDateAndTime;
	}
	public void setBookingDateAndTime(String bookingDateAndTime) {
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
	public int getWasherRatingGiven() {
		return washerRatingGiven;
	}
	public void setWasherRatingGiven(int washerRatingGiven) {
		this.washerRatingGiven = washerRatingGiven;
	}
	public int getCustomerRatingGiven() {
		return customerRatingGiven;
	}
	public void setCustomerRatingGiven(int customerRatingGiven) {
		this.customerRatingGiven = customerRatingGiven;
	}
	public InvoiceDetails getInvoice() {
		return invoice;
	}
	public void setInvoice(InvoiceDetails invoice) {
		this.invoice = invoice;
	}
}
