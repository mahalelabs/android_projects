package com.interview.carsales.db;

import com.interview.carsales.util.CarSaleConstants;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class MySQLiteHelper extends SQLiteOpenHelper {

	private static final String DATABASE_NAME = "order.db";
	private static final int DATABASE_VERSION = 3;

	String Query = "create table if not exists "
			+ CarSaleConstants.TABLE_ORDER
			 + "(" + "_id"+" integer primary key autoincrement, Manufacturer VARCHAR, Model VARCHAR,"
			+ " Color VARCHAR, OrderDate VARCHAR,DeliveryDate VARCHAR , status integer)";
	
//	  private static final String Query =  "create table "
//		      + "order3" + "(" + "id"
//		      + " integer primary key autoincrement, " + "test1"
//		      + " text not null);";


	
	
	public MySQLiteHelper(Context context) {
	    super(context, DATABASE_NAME, null, DATABASE_VERSION);
	  }
	@Override
	public void onCreate(SQLiteDatabase database) {
		// TODO Auto-generated method stub
		 database.execSQL(Query);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		db.execSQL(Query);

	}

}
