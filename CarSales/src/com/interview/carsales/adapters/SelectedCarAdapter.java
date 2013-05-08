package com.interview.carsales.adapters;

import java.util.List;

import com.interview.carsales.models.Car;
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
public class SelectedCarAdapter extends BaseAdapter {

	Context context;
	List<Car> cars;

	public SelectedCarAdapter(Context context, List<Car> cars) {
		this.context = context;
		this.cars = cars;
	}

	@SuppressLint("ResourceAsColor")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;

		if (v == null) {

			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = inflater.inflate(R.layout.carsales_row, null);

		}

		ImageView image = (ImageView) v.findViewById(R.id.imageViewColor);
		ProgressBar progress = (ProgressBar) v.findViewById(R.id.progressBar1);
		// TextView fromView = (TextView) v.findViewById(R.id.From);
		TextView manufactrure = (TextView) v
				.findViewById(R.id.textViewManufactruer);
		TextView model = (TextView) v.findViewById(R.id.textViewModel);
		TextView sales = (TextView) v.findViewById(R.id.textviewSales);

		manufactrure.setText(cars.get(position).getManufacture());
		model.setText(cars.get(position).getModel());

		image.setImageDrawable(getCarColor(cars.get(position).getColor()));

		double salesdone = CarSaleHelper.getSalesDone(cars.get(position)
				.getBudget(), cars.get(position).getSales());
		sales.setText(salesdone + " " + "%" + " " + "of "
				+ cars.get(position).getBudget());
		 CarSaleHelper.setProgressBar(progress, context, (int) salesdone);
//		progress.setProgressDrawable(context.getResources().getDrawable(
//				R.drawable.redprogressbar));
//		progress.setProgress((int) salesdone);
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
		return cars.size();
	}

	@Override
	public Object getItem(int pos) {
		// TODO Auto-generated method stub
		return cars.get(pos);
	}

	@Override
	public long getItemId(int pos) {
		// TODO Auto-generated method stub
		return pos;
	}
}
