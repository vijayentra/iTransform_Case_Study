package com.customer.service;

import java.util.List;

import com.customer.entity.Washer;

public interface WasherService {
	public Washer addWasher(Washer washer);
	public  Washer updateWasher(String phoneNumber, Washer washer);
	public void deleteWasher(String phoneNumber);
	public Washer viewWasher(String phoneNumber);
	public List<Washer> viewAllWasher();
}
