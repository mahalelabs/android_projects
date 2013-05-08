package com.interview.carsales.util;

import java.text.DecimalFormat;
import java.util.Date;

import com.interview.carsales.R;

import android.content.Context;
import android.widget.ProgressBar;

public class CarSaleHelper {

	public static double getSalesDone(String budget, String Sales) {
		DecimalFormat twoDForm = new DecimalFormat("#.##");

		double bud = Double.parseDouble(budget);
		double sale = Double.parseDouble(Sales);

		double result = (sale / bud) * 100;

		result = Double.valueOf(twoDForm.format(result));
		return result;

	}
public static String getStatus(long status){
	
	if(status==0){
		return "Pending";
		
	}
	
	if (status==1){
		return "Delivered";
	}
	
	return "Unknown";
}
	
	public static void setProgressBar(ProgressBar progressbar, Context context,int progress){
		if(progress > 0 && progress <30){
			
			progressbar.setProgressDrawable(context.getResources().getDrawable(R.drawable.redprogressbar));
			progressbar.setProgress(progress);
		}
		
		if (progress >= 30 && progress <70){

			progressbar.setProgressDrawable(context.getResources().getDrawable(R.drawable.yelloprogressbar));
			progressbar.setProgress(progress);
		}
		
		if (progress >= 70 && progress <=100) {
			progressbar.setProgressDrawable(context.getResources().getDrawable(R.drawable.greenprogressbar));
			progressbar.setProgress(progress);
		}
	}
	
	public static String getDateInString(){
		
		return new Date().toGMTString();
	}
}
