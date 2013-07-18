package com.connected.parking.views;
  
import com.connected.parking.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup; 
 
@SuppressLint("ValidFragment")
public class SearchFragment extends Fragment{

	private Context context = null; 
	private View parentView;
	 
	public SearchFragment(Context con ){
		this.context = con ;
	}
  
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		parentView = init(inflater, container);
		return parentView;
	}

	//==================================
	//初始化组件 
	//====================================
	private View init(LayoutInflater inflater, ViewGroup container) {
		
		View view = inflater.inflate(R.layout.user_search, container, false); 
		return view; 
	}
	
	
}
