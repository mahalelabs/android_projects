package com.interview.carsales;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.interview.carsales.adapters.SelectedCarAdapter;
import com.interview.carsales.db.OrderDataSource;
import com.interview.carsales.models.Car;
import com.interview.carsales.models.Order;
import com.interview.carsales.services.OrderingService;
import com.interview.carsales.util.CarSaleConstants;
import com.interview.carsales.util.CarSaleHelper;

import android.R.string;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends Activity {
	List<Car> cars;
	Spinner spnManufacturer, spnColor;
	List<String> manufactures;
	List<String> colors;
	ListView lvCarsales;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		spnManufacturer = (Spinner) findViewById(R.id.spinner1);
		spnColor = (Spinner) findViewById(R.id.spinner2);
		lvCarsales = (ListView) findViewById(R.id.listView1);

		// this is directly called as json data is in file , otherwise its good
		// to have a Async Task (thread) doing
		// this specifice task of pulling data .
		
		getJsonData();
		bindDataToSpinner();
		// Binding service
		

		OnItemSelectedListener customSelectedListener = new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View v, int pos,
					long arg3) {

				if (parent.getId() == R.id.spinner1) {
					spnColor.setSelection(0);
				}

				if (parent.getId() == R.id.spinner2) {

					List<Car> carData = createDataForSelectedFields();

					lvCarsales.setAdapter(new SelectedCarAdapter(
							getApplicationContext(), carData));

				}

			}

			private List<Car> createDataForSelectedFields() {

				String colorSelected = spnColor.getSelectedItem().toString();
				String manufactrureSelected = spnManufacturer.getSelectedItem()
						.toString();

				List<Car> selectedCars = null;

				if (manufactrureSelected.equals("All")
						&& colorSelected.equals("All")) {
					selectedCars = new ArrayList<Car>();
					selectedCars = cars;

				}

				else {

					selectedCars = new ArrayList<Car>();

					for (Car car : cars) {

						if ((car.getManufacture().equals(manufactrureSelected) || manufactrureSelected
								.equals("All"))
								&& (car.getColor().equals(colorSelected) || colorSelected
										.equals("All"))) {

							selectedCars.add(car);

						}

					}

				}

				return selectedCars;
				/*
				 * Toast.makeText(getApplicationContext(), "Selected " +
				 * ""+selectedCars.size(), Toast.LENGTH_SHORT).show();
				 */

			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {

			}
		};

		spnColor.setOnItemSelectedListener(customSelectedListener);
		spnManufacturer.setOnItemSelectedListener(customSelectedListener);
		lvCarsales.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {

				Intent intent = getIntent();
				intent.putExtra(CarSaleConstants.Manufacturer,
						cars.get(position).getManufacture());
				intent.putExtra(CarSaleConstants.Model, cars.get(position)
						.getModel());
				intent.putExtra(CarSaleConstants.Color, cars.get(position)
						.getColor());
				intent.putExtra(CarSaleConstants.OrderDate,
						CarSaleHelper.getDateInString());
              setResult(RESULT_OK, intent);
				
				finish();

			}

		});

	}

	
	

	

	private void bindDataToSpinner() {

		manufactures = new ArrayList<String>();
		manufactures.add("All");
		colors = new ArrayList<String>();
		colors.add("All");

		for (int i = 0; i < cars.size(); i++) {

			manufactures.add(cars.get(i).getManufacture());
			colors.add(cars.get(i).getColor());

		}

		@SuppressWarnings("unchecked")
		List<String> manufacturesTemp = new ArrayList<String>(
				(Collection<? extends String>) new LinkedHashSet(manufactures));
		List<String> colorsTemp = new ArrayList<String>(
				(Collection<? extends String>) new LinkedHashSet(colors));
		manufactures = manufacturesTemp;
		colors = colorsTemp;
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, manufacturesTemp);
		dataAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spnManufacturer.setAdapter(dataAdapter);
		ArrayAdapter<String> dataAdapterColor = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, colorsTemp);
		dataAdapterColor
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spnColor.setAdapter(dataAdapterColor);

	}

	public void getJsonData() {

		cars = new ArrayList<Car>();
		InputStream inputStream = getResources().openRawResource(R.raw.data);
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

		int ctr;
		try {
			ctr = inputStream.read();
			while (ctr != -1) {
				byteArrayOutputStream.write(ctr);
				ctr = inputStream.read();
			}
			inputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			JSONObject jObject = new JSONObject(
					byteArrayOutputStream.toString());
			JSONObject jObjectResult = jObject.getJSONObject(getResources()
					.getString(R.string.cars));
			JSONArray jArray = jObjectResult.getJSONArray(getResources()
					.getString(R.string.car));

			for (int i = 0; i < jArray.length(); i++) {

				Car tempCar = new Car(jArray.getJSONObject(i)
						.get("manufacture").toString(), jArray.getJSONObject(i)
						.get("model").toString(), jArray.getJSONObject(i)
						.get("color").toString(), jArray.getJSONObject(i)
						.get("budget").toString(), jArray.getJSONObject(i)
						.get("sales").toString());

				cars.add(tempCar);
				/*
				 * Toast.makeText(getApplicationContext(), "" + cars.size(), 1)
				 * .show();
				 */

			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
