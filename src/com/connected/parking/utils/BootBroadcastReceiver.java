package com.connected.parking.utils;

import com.connected.parking.controller.LoginController;
import com.connected.parking.controller.SplashController; 
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class BootBroadcastReceiver extends BroadcastReceiver{

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		
		if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")){
			Intent mintent = new Intent(context, SplashController.class); 
			mintent.addFlags(intent.FLAG_ACTIVITY_NEW_TASK);
			context.startActivity(mintent);
			Log.i("auto", "auto boot");
		}else{
			Intent mintent = new Intent(context, LoginController.class); 
			mintent.addFlags(intent.FLAG_ACTIVITY_NEW_TASK);
			context.startActivity(mintent);
			Log.i("auto", "auto boot");
		}  
		Log.i("auto exit", "auto boot");
		} 
}
