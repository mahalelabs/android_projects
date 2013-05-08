package com.interview.carsales.db;

import java.util.ArrayList;
import java.util.List;

import com.interview.carsales.models.Order;
import com.interview.carsales.util.CarSaleConstants;
import com.interview.carsales.util.CarSaleHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class OrderDataSource {

	// Database fields
	private SQLiteDatabase database;
	private MySQLiteHelper dbHelper;

	
	 private String[] allColumns = { CarSaleConstants.ID,
			 CarSaleConstants.Manufacturer,CarSaleConstants.Model,CarSaleConstants.Color,CarSaleConstants.OrderDate,CarSaleConstants.DeliveryDate,CarSaleConstants.STATUS };
	 

	public OrderDataSource(Context context) {
		dbHelper = new MySQLiteHelper(context);
	}

	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}

	public void close() {
		dbHelper.close();
	}
	
	  public List<Order> getAllOrders() {
		    List<Order> orders = new ArrayList<Order>();

		    Cursor cursor = database.query(CarSaleConstants.TABLE_ORDER,
		        allColumns, null, null, null, null, null);

		    cursor.moveToFirst();
		    while (!cursor.isAfterLast()) {
		      Order order = cursorToOrder(cursor);
		      orders.add(order);
		      cursor.moveToNext();
		    }
		    // Make sure to close the cursor
		    cursor.close();
		    return orders;
		  }
	  public void deleteComment(Order order) {
		    long id = order.get_id();
		    System.out.println("Comment deleted with id: " + id);
		    database.delete(CarSaleConstants.TABLE_ORDER, CarSaleConstants.ID
		        + " = " + id, null);
		  }
	
	public Order createOrder(Order order) {
	    ContentValues values = new ContentValues();
	    values.put(CarSaleConstants.Manufacturer, order.getManufacture());
	    values.put(CarSaleConstants.Model, order.getModel());
	    values.put(CarSaleConstants.Color, order.getColor());
	    values.put(CarSaleConstants.OrderDate, order.getOrderedDate());
	    values.put(CarSaleConstants.DeliveryDate, order.getDeliveryDate());
	    values.put(CarSaleConstants.STATUS, order.getStatus());
	    
	    
	    
	    long insertId = database.insert(CarSaleConstants.TABLE_ORDER, null,
	        values);
	    Cursor cursor = database.query(CarSaleConstants.TABLE_ORDER,
	        allColumns, CarSaleConstants.ID + " = " + insertId, null,
	        null, null, null);
	   boolean test= cursor.moveToFirst();
	    
	    Order newOrder = cursorToOrder(cursor);
	    cursor.close();
	    return newOrder;
	  }

	private Order cursorToOrder(Cursor cursor) {
		
//		long  id=cursor.getLong(0);
//		String manu=cursor.getString(1);
	//long  stat=cursor.getColumnIndexOrThrow("status");
		
		Order order = new Order(cursor.getLong(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(5),cursor.getLong(6));

		
	    
	    return order;
	  }
	
	public void updateRow(Order order) {
        
 
    ContentValues values = new ContentValues();
    values.put(CarSaleConstants.Manufacturer, order.getManufacture());
    values.put(CarSaleConstants.Model, order.getModel());
    values.put(CarSaleConstants.Color, order.getColor());
    values.put(CarSaleConstants.OrderDate, order.getOrderedDate());
    values.put(CarSaleConstants.DeliveryDate, order.getDeliveryDate());
    values.put(CarSaleConstants.STATUS, order.getStatus());

    database.update(CarSaleConstants.TABLE_ORDER, values, CarSaleConstants.ID+"=" + order.get_id(), null);
}

}
