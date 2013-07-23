package com.connected.parking.views;
 
import com.connected.parking.R;
  
import android.os.Bundle; 
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
   
public class PreferenceSettingFragment extends /*Preference*/Fragment{

	private View view;
	
	public PreferenceSettingFragment(){
		
	}

	/*@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.preferences);
	}
	*/
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		//return super.onCreateView(inflater, container, savedInstanceState);
		view = initView(inflater, container);//
		return view;
	}
	
	//==================================
	//初始化组件 
	//====================================
	private View initView(LayoutInflater inflater, ViewGroup container) {
		View view = inflater.inflate(R.layout.setting_controller, container, false);
		return view;
	}
 
	
}
