package com.customer.dummyentity;

import java.util.ArrayList;

import java.util.List;

public class InvoiceDetails {
	private String bookingId;
	private String billingDateAndTime;
	private String wash_package;
	private List<String> washAddOn = new ArrayList<>();
	private double bill_Amount;
	public String getBookingId() {
		return bookingId;
	}
	public void setBookingId(String bookingId) {
		this.bookingId = bookingId;
	}
	public String getBillingDateAndTime() {
		return billingDateAndTime;
	}
	public void setBillingDateAndTime(String billingDateAndTime) {
		this.billingDateAndTime = billingDateAndTime;
	}
	public String getWash_package() {
		return wash_package;
	}
	public void setWash_package(String wash_package) {
		this.wash_package = wash_package;
	}
	public List<String> getWashAddOn() {
		return washAddOn;
	}
	public void setWashAddOn(List<String> washAddOn) {
		this.washAddOn = washAddOn;
	}
	public double getBill_Amount() {
		return bill_Amount;
	}
	public void setBill_Amount(double bill_Amount) {
		this.bill_Amount = bill_Amount;
	}
}