package com.interview.carsales.models;

public class Car {

	String manufacture;
	String model;
	String color;
	String budget;
	String sales;
	public Car(String manufacture, String model, String color, String budget,
			String sales) {
		super();
		this.manufacture = manufacture;
		this.model = model;
		this.color = color;
		this.budget = budget;
		this.sales = sales;
	}
	public String getManufacture() {
		return manufacture;
	}
	public void setManufacture(String manufacture) {
		this.manufacture = manufacture;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getBudget() {
		return budget;
	}
	public void setBudget(String budget) {
		this.budget = budget;
	}
	public String getSales() {
		return sales;
	}
	public void setSales(String sales) {
		this.sales = sales;
	}
	
}
