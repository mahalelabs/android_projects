package com.interview.carsales.adapters;

import java.util.List;

import com.interview.carsales.models.Car;
import com.interview.carsales.models.Order;
import com.interview.carsales.util.CarSaleConstants;
import com.interview.carsales.util.CarSaleHelper;

import com.interview.carsales.R;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.AvoidXfermode.Mode;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

@SuppressLint("ResourceAsColor")
public class OrdersAdapter extends BaseAdapter {

	Context context;
	List<Order> orders;

	public OrdersAdapter(Context context, List<Order> orders) {
		this.context = context;
		this.orders = orders;
	}

	@SuppressLint("ResourceAsColor")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;

		if (v == null) {

			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = inflater.inflate(R.layout.order_row, null);

		}

		ImageView image = (ImageView) v.findViewById(R.id.imageViewColor);
	
		// TextView fromView = (TextView) v.findViewById(R.id.From);
		TextView manufactrure = (TextView) v
				.findViewById(R.id.textViewManufactruer);
		TextView model = (TextView) v.findViewById(R.id.textViewModel);
		TextView date = (TextView) v.findViewById(R.id.textviewDate);
		TextView status=(TextView)v.findViewById(R.id.textView1Status);

		manufactrure.setText(orders.get(position).getManufacture());
		model.setText(orders.get(position).getModel());

		image.setImageDrawable(getCarColor(orders.get(position).getColor()));
		
		status.setText( CarSaleHelper.getStatus(orders.get(position).getStatus()));

		date.setText(orders.get(position).getDeliveryDate());
		return v;

	}

	private Drawable getCarColor(String color) {

		if (color.equals("black")) {

			return context.getResources().getDrawable(R.drawable.circle_balck);
		}
		if (color.equals("blue")) {

			return context.getResources().getDrawable(R.drawable.circle_blue);
		}
		if (color.equals("silver")) {

			return context.getResources().getDrawable(R.drawable.circle_grey);
		} else {
			return context.getResources().getDrawable(R.drawable.circle_grey);
		}
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return orders.size();
	}

	@Override
	public Object getItem(int pos) {
		// TODO Auto-generated method stub
		return orders.get(pos);
	}

	@Override
	public long getItemId(int pos) {
		// TODO Auto-generated method stub
		return pos;
	}
}
