package com.interview.carsales.models;

import java.sql.Date;

public class Order {

	public Order(long _id,String manufacture, String model, String color,
			String orderedDate, String deliveryDate,long status) {
		super();
		this.manufacture = manufacture;
		this._id = _id;
		this.model = model;
		this.color = color;
		this.orderedDate = orderedDate;
		this.deliveryDate = deliveryDate;
		this.status=status;
		
	}


	String manufacture;
	long _id;

	public long get_id() {
		return _id;
	}

	public void set_id(long _id) {
		this._id = _id;
	}

	String model;
	String color;
	String orderedDate;
	String deliveryDate;
	long  status;

	public long getStatus() {
		return status;
	}

	public void setStatus(long status) {
		this.status = status;
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

	public String getOrderedDate() {
		return orderedDate;
	}

	public void setOrderedDate(String orderedDate) {
		this.orderedDate = orderedDate;
	}

	public String getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(String deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

}
