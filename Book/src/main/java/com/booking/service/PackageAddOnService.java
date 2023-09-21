package com.booking.service;

import java.util.List;

import com.booking.entity.AddOns;
import com.booking.entity.Packages;

public interface PackageAddOnService {
	public Packages addPackage(String packageDeal, double price);
	public AddOns addAddOn(String addOn, double price);
	
	public Packages updatePackage(String packageDeal, double price);
	public AddOns updateAddOn(String packageDeal, double price);
	
	public List<Packages> viewPackages();
	public List<AddOns> viewAddOns();
	
	public void deletePackage(String packageDeal);
	public void deleteAddOn(String addOn);
}

