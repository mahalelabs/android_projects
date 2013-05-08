package com.interview.carsales.interfaces;
import java.util.List;

import com.interview.carsales.models.Order;


public interface IServiceCallbacks {
	
	public void OrderSatus(List<Order> orders);

}
