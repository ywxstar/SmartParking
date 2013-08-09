package com.connected.parking.utils;

import com.connected.parking.R;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

public class DefinedToast {

	Activity activity;
	Context context;
	public DefinedToast(Activity ac, Context con){
		this.activity = ac;
		this.context = con;
	}
	
	public void showAlert(String message) {
		LayoutInflater inflater = this.activity.getLayoutInflater();
		View layout = inflater.inflate(R.layout.toast_layout,
		                               (ViewGroup)activity.findViewById(R.id.toast_layout_root));

		TextView text = (TextView) layout.findViewById(R.id.text);
		text.setText(message);
		text.setTextColor(Color.parseColor("#00B2FF"));

		Toast toast = new Toast(context);
		toast.setGravity(Gravity.NO_GRAVITY, 0, 50);
		toast.setDuration(Toast.LENGTH_SHORT);
		toast.setView(layout);
		toast.show();
	}	
}
