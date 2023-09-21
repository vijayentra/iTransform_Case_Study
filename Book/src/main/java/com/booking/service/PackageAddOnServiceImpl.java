package com.booking.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.booking.entity.AddOns;
import com.booking.entity.Packages;
import com.booking.exception.PackageAndAddOnException;
import com.booking.repository.AddOnsRepository;
import com.booking.repository.PackagesRepository;

@Service
public class PackageAddOnServiceImpl implements PackageAddOnService{
	@Autowired
	private PackagesRepository packagesRepository;
	@Autowired
	private AddOnsRepository addonsRepository;

	@Override
	public Packages addPackage(String packageDeal, double price) {
		// TODO Auto-generated method stub
		List<Packages> list = packagesRepository.findAll();
		for(Packages p : list) {
			if(p.getPackageName().equalsIgnoreCase(packageDeal)) {
				throw new PackageAndAddOnException("Package already exists with the given name. ");
			}
		}
		Packages pp = new Packages();
		pp.setPackageName(packageDeal);
		pp.setPrice(price);
		packagesRepository.save(pp);
		return pp;
	}

	@Override
	public AddOns addAddOn(String addOnName, double price) {
		List<AddOns> list = addonsRepository.findAll();
		for(AddOns a : list) {
			if(a.getAddOnName().equalsIgnoreCase(addOnName)) {
				throw new PackageAndAddOnException("AddOn already exists with the given name. ");
			}
		}
		AddOns aa = new AddOns();
		aa.setAddOnName(addOnName);
		aa.setPrice(price);
		addonsRepository.save(aa);
		return aa;
	}

	@Override
	public Packages updatePackage(String packageDeal, double price) {
		Packages p =null;
		p = packagesRepository.findByPackageName(packageDeal);
		if(p==null) throw new PackageAndAddOnException("Package does not exist. ");
		p.setPrice(price);
		packagesRepository.save(p);
		return p;
	}

	@Override
	public AddOns updateAddOn(String addOnName, double price) {
		AddOns a = null;
		a = addonsRepository.findByAddOnName(addOnName);
		if(a==null) throw new PackageAndAddOnException("AddOn does not exist. ");
		a.setPrice(price);
		addonsRepository.save(a);
		return a;
	}

	@Override
	public List<Packages> viewPackages() {
		List<Packages> list = packagesRepository.findAll();
		if(list==null) throw new PackageAndAddOnException("Empty List!");
		return list;
	}

	@Override
	public List<AddOns> viewAddOns() {
		List<AddOns> list = addonsRepository.findAll();
		if(list==null) throw new PackageAndAddOnException("Empty List!");
		return list;
	}


	@Override
	public void deletePackage(String packageDeal) {
		Packages p =null;
		p = packagesRepository.findByPackageName(packageDeal);
		if(p==null) throw new PackageAndAddOnException("Package does not exist. ");
		packagesRepository.delete(p);
	}

	@Override
	public void deleteAddOn(String addOnName) {
		AddOns a = null;
		a = addonsRepository.findByAddOnName(addOnName);
		if(a==null) throw new PackageAndAddOnException("AddOn does not exist. ");
		addonsRepository.delete(a);
	}

}
