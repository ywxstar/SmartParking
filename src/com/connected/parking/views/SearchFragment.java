package com.connected.parking.views;
  
import com.connected.parking.R;
import com.connected.parking.utils.AppStatus;
import com.connected.parking.views.NetworkDisconnectFragment.NetworkSettingListener;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.opengl.Visibility;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup; 
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
  
@SuppressLint("ValidFragment")
public class SearchFragment extends Fragment{

	private Context context = null; 
	private View parentView;
	EditText edit;
	Button setting_network;
	TextView warning_text;
	ImageButton search_view;
	ListView searchListView;
	
	public SearchFragment(){ 
	}
	
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
	 
		setting_network = (Button)view.findViewById(R.id.setting_network); 
		warning_text = (TextView)view.findViewById(R.id.tv_warning);
		
		edit = (EditText)view.findViewById(R.id.search_edit); 
		search_view = (ImageButton)view.findViewById(R.id.search);
		searchListView = (ListView)view.findViewById(R.id.search_result);
		searchListView.setAdapter(new BaseAdapter() {
			
			@Override
			public View getView(int arg0, View arg1, ViewGroup arg2) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public long getItemId(int arg0) {
				// TODO Auto-generated method stub
				return 0;
			}
			
			@Override
			public Object getItem(int arg0) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public int getCount() {
				// TODO Auto-generated method stub
				return 0;
			}
		});
		if(AppStatus.isOnline(context)){ 
			setting_network.setVisibility(View.GONE); 
			warning_text.setVisibility(View.GONE);
			search_view.setVisibility(View.VISIBLE);
		}else{
			setting_network.setVisibility(View.VISIBLE); 
			warning_text.setVisibility(View.VISIBLE); 
			search_view.setVisibility(View.GONE);
		}
		setting_network.setOnClickListener(new NetworkSettingListener());
		return view; 
	}
	
	class NetworkSettingListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent = new Intent();
			intent.setAction(android.provider.Settings.ACTION_WIRELESS_SETTINGS);
			startActivityForResult(intent, 1); 
		}
		
	}
	 
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode == 1){
			if(AppStatus.isOnline(context)){
				setting_network.setVisibility(View.GONE); 
				warning_text.setVisibility(View.GONE);
				search_view.setVisibility(View.VISIBLE);
			}else{
				setting_network.setVisibility(View.VISIBLE); 
				warning_text.setVisibility(View.VISIBLE);
				search_view.setVisibility(View.GONE);
			}
		}
	}
	
	
}
