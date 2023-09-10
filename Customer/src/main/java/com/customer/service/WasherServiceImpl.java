package com.customer.service;

import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.customer.entity.Washer;
import com.customer.exception.InvalidDetailsException;
import com.customer.repository.WasherRepository;

@Service
public class WasherServiceImpl implements WasherService{
	
	@Autowired
	private WasherRepository washerRepository;

	@Override
	public Washer addWasher(Washer washer) {
		List<Washer> list = washerRepository.findAll();
		for(Washer w : list) {
			if(w.getPhoneNumber().equals(washer.getPhoneNumber())) {
				throw new InvalidDetailsException(
						"Washer already exists for this phone number. ");
			}
		}
		
		String msg = "";
		
		String nameRegex = "^[A-Z]{1}[a-z]{0,19}$";
		Pattern nameP = Pattern.compile(nameRegex);
		Matcher m1 = nameP.matcher(washer.getFirstName());
		if(!m1.matches()) msg = msg + "Invalid first name.\n";
		Matcher m2 = nameP.matcher(washer.getLastName());
		if(!m2.matches()) msg = msg + "Invalid last name.\n";
		
		String numberRegex = "^[6-9]{1}[0-9]{9}$";
		Pattern numberP = Pattern.compile(numberRegex);
		Matcher m3 = numberP.matcher(washer.getPhoneNumber());
		if(!m3.matches()) msg = msg+"Invalid phone number.\n";
		
		String passRegex = "^(?=.*[A-Z])(?=.*[0-9])(?=.*[^A-Za-z0-9]).{8,}$";
		Pattern passP = Pattern.compile(passRegex);
		Matcher m8 = passP.matcher(washer.getPassword());
		if(!m8.matches())msg = msg + "Password did not match the requirements.\n";
		int a = 0;
		try {
			a = Integer.parseInt(washer.getAge());
			if(a<18) msg = msg + "Age can't be below 18. ";
		}catch(NumberFormatException e) {
			msg = msg + "Invalid age. ";
		}
		if(msg.equals("")) {
			return washerRepository.save(washer);
		}
		throw new InvalidDetailsException(msg);
	}

	@Override
	public Washer updateWasher(String phoneNumber, Washer washer) 
	{
		Washer w = washerRepository.findByPhoneNumber(phoneNumber).
				orElseThrow(()-> new InvalidDetailsException("Washer does not exist."));
		String msg = "";
		
		String nameRegex = "^[A-Z]{1}[a-z]{0,19}$";
		Pattern nameP = Pattern.compile(nameRegex);
		Matcher m1 = nameP.matcher(washer.getFirstName());
		if(!m1.matches()) msg = msg + "Invalid first name.\n";
		Matcher m2 = nameP.matcher(washer.getLastName());
		if(!m2.matches()) msg = msg + "Invalid last name.\n";
		
		String numberRegex = "^[6-9]{1}[0-9]{9}$";
		Pattern numberP = Pattern.compile(numberRegex);
		Matcher m3 = numberP.matcher(washer.getPhoneNumber());
		if(!m3.matches()) msg = msg+"Invalid phone number.\n";
		
		String passRegex = "^(?=.*[A-Z])(?=.*[0-9])(?=.*[^A-Za-z0-9]).{8,}$";
		Pattern passP = Pattern.compile(passRegex);
		Matcher m8 = passP.matcher(washer.getPassword());
		if(!m8.matches())msg = msg + "Password did not match the requirements.\n";
		int a = 0;
		try {
			a = Integer.parseInt(washer.getAge());
			if(a<18) msg = msg + "Age can't be below 18. ";
		}catch(NumberFormatException e) {
			msg = msg + "Invalid age. ";
		}
		if(msg.equals("")) {
			w.setFirstName(washer.getFirstName());
			w.setLastName(washer.getLastName());
			w.setPassword(washer.getPassword());
			w.setPhoneNumber(washer.getPhoneNumber());
			w.setAge(washer.getAge());
			return washerRepository.save(w);
		}
		throw new InvalidDetailsException(msg);
	}

	@Override
	public void deleteWasher(String phoneNumber) {
		// TODO Auto-generated method stub
		Washer w = washerRepository.findByPhoneNumber(phoneNumber).
				orElseThrow(()-> new InvalidDetailsException("Washer does not exist."));
		washerRepository.delete(w);
	}

	@Override
	public Washer viewWasher(String phoneNumber) {
		Washer w = washerRepository.findByPhoneNumber(phoneNumber).
				orElseThrow(()-> new InvalidDetailsException("Washer does not exist."));
		return w;
	}

	@Override
	public List<Washer> viewAllWasher() {
		List<Washer> list = washerRepository.findAll();
		return list;
	}

}
