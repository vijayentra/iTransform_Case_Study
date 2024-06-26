package com.customer.entity;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class CarDetails {
	private String brand;
	private String model;
	private String colour;
	private String mfgYear;
	private String lengthInMM;
	private String numberPlate;
	
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getColour() {
		return colour;
	}
	public void setColour(String colour) {
		this.colour = colour;
	}
	public String getLengthInMM() {
		return lengthInMM;
	}
	public void setLengthInMM(String lengthInMM) {
		this.lengthInMM = lengthInMM;
	}
	public String getMfgYear() {
		return mfgYear;
	}
	public void setMfgYear(String mfgYear) {
		this.mfgYear = mfgYear;
	}
	public String getNumberPlate() {
		return numberPlate;
	}
	public void setNumberPlate(String numberPlate) {
		this.numberPlate = numberPlate;
	}
	public CarDetails(String brand, String model, String colour, String mfgYear, String lengthInMM,
			String numberPlate) {
		super();
		this.brand = brand;
		this.model = model;
		this.colour = colour;
		this.mfgYear = mfgYear;
		this.lengthInMM = lengthInMM;
		this.numberPlate = numberPlate;
	}
	public CarDetails() {
		
	}
	
	
}
