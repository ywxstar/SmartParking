package com.connected.parking.controller;
 
import com.connected.parking.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

public class SplashController1 extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		super.onCreate(savedInstanceState); 
		setContentView(R.layout.splash);
		
		new Handler().postDelayed(new Thread() {
        	@Override
        	public void run() {
				Intent activity = new Intent(SplashController1.this, SplashController.class);
				startActivity(activity);
				SplashController1.this.finish();
        	}
        }, 3000);
	}

	
}
