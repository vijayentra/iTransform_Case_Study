package com.booking.dummyentity;

public class RatingRequestHelper {
	private String phoneNumber;
	private int rating;
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public int getRating() {
		return rating;
	}
	public void setRating(int rating) {
		this.rating = rating;
	}
	public RatingRequestHelper(String phoneNumber, int rating) {
		super();
		this.phoneNumber = phoneNumber;
		this.rating = rating;
	}
	public RatingRequestHelper() {
		
	}
}
