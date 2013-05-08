package com.interview.carsales.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.interview.carsales.BaseActivity;
import com.interview.carsales.OrderInfo;
import com.interview.carsales.models.Order;
import com.interview.carsales.util.CarSaleConstants;
import com.interview.carsales.util.CarSaleHelper;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.widget.Toast;

public class OrderingService extends Service {
	
	BaseActivity  orderInfo;
	
	private final IBinder mBinder = new MyBinder();

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return mBinder;
	}
	 public class MyBinder extends Binder {
		 
		 private Order order;
		 public OrderingService getService() {
		      return OrderingService.this;
		    }
		  }

	 @Override
     public void onCreate() {
           super.onCreate();
         //  Toast.makeText(this,"Service created ...", Toast.LENGTH_LONG).show();
     }
	 
	 @Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		return Service.START_STICKY ;
	 }
	 

	 
	 public Order placeOrder(Order order ){
		 
		
		 
		 int random = (int)Math.ceil(Math.random()*100);
		 
		 if(random<50){
			 
				
			 return new Order(0,order.getManufacture(), order.getModel(), order.getColor(),order.getOrderedDate(), CarSaleHelper.getDateInString(),0);
		 }
		 
		 if(random>=50){
			 return new Order(0,order.getManufacture(), order.getModel(), order.getColor(),order.getOrderedDate(), CarSaleHelper.getDateInString(),1);
		 }
		return order;
		 
		
	 }
	 
	 public void placeOrder(List<Order> orders,BaseActivity orderifo){
		 
		 
		 
		 List<Order> orders1=new ArrayList<Order>();
		 this.orderInfo=orderifo;
		 for (Order order : orders) {
			 int random = (int)Math.ceil(Math.random()*100);
			 if(random<50){
				 
				// Toast.makeText(getApplicationContext(), "ran"+random, 1).show();
				 order= new Order(order.get_id(),order.getManufacture(), order.getModel(), order.getColor(),order.getOrderedDate(), CarSaleHelper.getDateInString(),0);
				 orders1.add(order);
			 }
			 
			 if(random >50){
				
				 order=new Order(order.get_id(),order.getManufacture(), order.getModel(), order.getColor(),order.getOrderedDate(), CarSaleHelper.getDateInString(),1);
				 orders1.add(order);
				// Toast.makeText(getApplicationContext(), "ran"+random, 1).show();
			 }
			 
			 
			
		}
		 
		 
		 orderifo.OrderSatus(orders1);
		
		 
	 }
}
