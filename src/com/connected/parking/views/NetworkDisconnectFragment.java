package com.connected.parking.views;

import com.connected.parking.R;
import com.connected.parking.utils.AppStatus;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;


@SuppressLint("ValidFragment")
public class NetworkDisconnectFragment extends Fragment{

	public Context context; 
	public NetworkDisconnectFragment(Context con){
		this.context = con;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		//return super.onCreateView(inflater, container, savedInstanceState);
		View view = inflater.inflate(R.layout.network_disconnect, container, false);
		Button setting_network = (Button)view.findViewById(R.id.setting_network);
		setting_network.setOnClickListener(new NetworkSettingListener());
		return view;
	}
	
	class NetworkSettingListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent = new Intent();
			intent.setAction(android.provider.Settings.ACTION_NETWORK_OPERATOR_SETTINGS);
			startActivityForResult(intent, 1);
		}
		
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		//super.onActivityResult(requestCode, resultCode, data);
		if(requestCode == 1){
			if(AppStatus.isOnline(context)){
				
			}else{
				return;
			}
		}
	}
	
	
	
}
