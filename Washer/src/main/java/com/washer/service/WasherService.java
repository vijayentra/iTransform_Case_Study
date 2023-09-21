package com.washer.service;

import java.util.List;

import com.washer.entity.Washer;

public interface WasherService {
	public Washer addWasher(Washer washer);
	public  Washer updateWasher(String phoneNumber, Washer washer);
	public void deleteWasher(String phoneNumber);
	public Washer viewWasher(String phoneNumber);
	public List<Washer> viewAllWasher();
	public Washer updateWasherRating(String phoneNumber, int rating);
}
