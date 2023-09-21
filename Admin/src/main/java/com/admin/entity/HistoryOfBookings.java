package com.admin.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity
public class HistoryOfBookings {
	 @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     private Long id;
	 private String bookingDateAndTime;
	 private String bookingId;
	 private String customerName;
	 private String customerPhoneNumber;
	 private int customerRatingGiven;
	 private String washerName;
	 private String washerPhoneNumber;
	 private int washerRatingGiven;
	 private String washStatus;
	public String getBookingDateAndTime() {
		return bookingDateAndTime;
	}
	public void setBookingDateAndTime(String bookingDateAndTime) {
		this.bookingDateAndTime = bookingDateAndTime;
	}
	public String getBookingId() {
		return bookingId;
	}
	public void setBookingId(String bookingId) {
		this.bookingId = bookingId;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getCustomerPhoneNumber() {
		return customerPhoneNumber;
	}
	public void setCustomerPhoneNumber(String customerPhoneNumber) {
		this.customerPhoneNumber = customerPhoneNumber;
	}
	public int getCustomerRatingGiven() {
		return customerRatingGiven;
	}
	public void setCustomerRating(int customerRatingGiven) {
		this.customerRatingGiven = customerRatingGiven;
	}
	public String getWasherName() {
		return washerName;
	}
	public void setWasherName(String washerName) {
		this.washerName = washerName;
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
	public void setWasherRating(int washerRatingGiven) {
		this.washerRatingGiven = washerRatingGiven;
	}
	public String getWashStatus() {
		return washStatus;
	}
	public void setWashStatus(String washStatus) {
		this.washStatus = washStatus;
	}
	public HistoryOfBookings(String bookingDateAndTime, String bookingId, String customerName,
			String customerPhoneNumber, int customerRatingGiven, String washerName, String washerPhoneNumber,
			int washerRatingGiven, String washStatus) {
		super();
		this.bookingDateAndTime = bookingDateAndTime;
		this.bookingId = bookingId;
		this.customerName = customerName;
		this.customerPhoneNumber = customerPhoneNumber;
		this.customerRatingGiven = customerRatingGiven;
		this.washerName = washerName;
		this.washerPhoneNumber = washerPhoneNumber;
		this.washerRatingGiven = washerRatingGiven;
		this.washStatus = washStatus;
	}
	public HistoryOfBookings() {
		
	}
}
