package com.interview.carsales;

import java.util.ArrayList;
import java.util.List;

import com.interview.carsales.adapters.OrdersAdapter;
import com.interview.carsales.db.OrderDataSource;
import com.interview.carsales.models.Order;
import com.interview.carsales.services.OrderingService;
import com.interview.carsales.util.CarSaleConstants;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.LiveFolders;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

public class OrderInfo extends BaseActivity {
	ImageButton add, refresh;

	private OrderDataSource datasource;
	ListView lvPendingOrders, lvDeliveredOrder;
	private OrderingService orderingService;
	List<Order> savingOrder;
	List<Order> orders;
	int operation = 0; // 0=save. 1=update

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.orderlist_layout);

		add = (ImageButton) findViewById(R.id.imageButtonADDorder);
		refresh = (ImageButton) findViewById(R.id.imageButton1Refresh);
		lvPendingOrders = (ListView) findViewById(R.id.listView1);

		lvDeliveredOrder = (ListView) findViewById(R.id.listView2);
		registerReceivers();
		datasource = new OrderDataSource(this);

		loadOrders();

		OnClickListener customListener = new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (v.getId() == R.id.imageButton1Refresh) {
					operation = 1;
					reSubmitPendingOrders();

				}

				if (v.getId() == R.id.imageButtonADDorder) {

					Intent intent = new Intent(getApplicationContext(),
							MainActivity.class);
					startActivityForResult(intent, 0);
				}

			}

		};

		add.setOnClickListener(customListener);
		refresh.setOnClickListener(customListener);

		if (getIntent().getExtras() != null) {

			if (getIntent().getExtras().get("network").equals("netowrkchange")) {

				Log.d("test", " inside networkchange get intent");

				reSubmitPendingOrders();
			}
		}

		reSubmitPendingOrders();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode,
			Intent intent) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, intent);
		
		if(intent!=null){

		orders = new ArrayList<Order>();

		Order savingOrder = new Order(0, intent.getExtras().getString(
				CarSaleConstants.Manufacturer), intent.getExtras().getString(
				CarSaleConstants.Model), intent.getExtras().getString(
				CarSaleConstants.Color), intent.getExtras().getString(
				CarSaleConstants.OrderDate), "data", 0);
		// placeOrderAsyntask.execute("");
		orders.add(savingOrder);
		// Toast.makeText(getApplicationContext(), "AcitviytResult", 0).show();
		if (orderingService != null) {
			Log.d("test", " onActivityResult" + "orderingService!=null");
			if (orders != null) {
				operation = 0;
				orderingService.placeOrder(orders, OrderInfo.this);

			}
		} else {
			operation = 0;
			bindService();

		}
		}

	}

	private void saveOrder() {

		OrderDataSource datasource1 = new OrderDataSource(this);
		datasource1.open();

		if (operation == 0) {

			for (Order order : savingOrder) {

				datasource1.createOrder(order);

			}
		}

		if (operation == 1) {
			for (Order order : savingOrder) {

				datasource1.updateRow(order);
			}
		}

		datasource1.close();
		datasource1 = null;

	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		// unRegisterListener();
	}

	public void bindService() {
		Log.d("test", " bindService");

		bindService(new Intent(OrderInfo.this, OrderingService.class),
				serviceConnection, Context.BIND_AUTO_CREATE);

	}

	private ServiceConnection serviceConnection = new ServiceConnection() {

		@Override
		public void onServiceDisconnected(ComponentName name) {

			// // Toast.makeText(getApplicationContext(),
			// "Service Disconnected", 0)
			// .show();

		}

		@Override
		public void onServiceConnected(ComponentName name, IBinder binder) {
			// TODO Auto-generated method stub

			orderingService = ((OrderingService.MyBinder) binder).getService();
			if (orders != null) {
				orderingService.placeOrder(orders, OrderInfo.this);
			}

			// orderingService.unbindService(serviceConnection);

		}
	};

	@Override
	public void OrderSatus(List<Order> orders) {
		savingOrder = orders;

		placeOrderAsyntask p1 = new placeOrderAsyntask();
		p1.execute("");

	};

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

		loadOrders();
		// reSubmitPendingOrders() ;
	}

	private void loadOrders() {

		datasource.open();
		List<Order> orders = datasource.getAllOrders();
		datasource.close();
		List<Order> pendingOrders = new ArrayList<Order>();
		List<Order> deliveredOrders = new ArrayList<Order>();
		for (Order order : orders) {

			if (order.getStatus() == CarSaleConstants.ORDER_PENDING) {
				pendingOrders.add(order);
			}

			if (order.getStatus() == CarSaleConstants.ORDER_DELIVERED) {
				deliveredOrders.add(order);
			}
		}

		lvPendingOrders.setAdapter(new OrdersAdapter(getApplicationContext(),
				pendingOrders));

		lvDeliveredOrder.setAdapter(new OrdersAdapter(getApplicationContext(),
				deliveredOrders));

	}

	public class placeOrderAsyntask extends android.os.AsyncTask {
		@Override
		protected Object doInBackground(Object... params) {

			saveOrder();

			return "test";
		}

		@Override
		protected void onPostExecute(Object result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			loadOrders();
			// Toast.makeText(getApplicationContext(), "done!!", 1).show();

		}

	}

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
		// reSubmitPendingOrders() ;
	}

	public void reSubmitPendingOrders() {

		OrderDataSource orderDS = new OrderDataSource(this);
		orderDS.open();

		List<Order> orders = orderDS.getAllOrders();
		orderDS.close();
		orderDS = null;
		List<Order> pendingOrders = new ArrayList<Order>();
		for (Order order : orders) {
			if (order.getStatus() == 0) {
				pendingOrders.add(order);
			}
		}

		if (pendingOrders.size() != 0) {
			Log.d("test", " reSubmitPendingOrders");
			if (orderingService != null) {
				operation = 1;
				orderingService.placeOrder(pendingOrders, OrderInfo.this);
			} else {
				orders = pendingOrders;
				operation = 1;
				bindService();

			}

		}

	}

	private BroadcastReceiver mConnReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			boolean noConnectivity = intent.getBooleanExtra(
					ConnectivityManager.EXTRA_NO_CONNECTIVITY, false);
			String reason = intent
					.getStringExtra(ConnectivityManager.EXTRA_REASON);
			boolean isFailover = intent.getBooleanExtra(
					ConnectivityManager.EXTRA_IS_FAILOVER, false);

			NetworkInfo currentNetworkInfo = (NetworkInfo) intent
					.getParcelableExtra(ConnectivityManager.EXTRA_NETWORK_INFO);
			NetworkInfo otherNetworkInfo = (NetworkInfo) intent
					.getParcelableExtra(ConnectivityManager.EXTRA_OTHER_NETWORK_INFO);

			reSubmitPendingOrders();
			Log.d("test", " BroadcastReceiver");
		}
	};

	private void registerReceivers() {
		registerReceiver(mConnReceiver, new IntentFilter(
				ConnectivityManager.CONNECTIVITY_ACTION));
	}

	private void unRegisterListener() {

		unregisterReceiver(mConnReceiver);
	}

}