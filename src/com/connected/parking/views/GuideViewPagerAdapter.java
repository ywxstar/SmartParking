package com.connected.parking.views;

import java.util.List;

import com.connected.parking.R;
import com.connected.parking.controller.LoginController;
import com.connected.parking.controller.ProfileController;
import com.connected.parking.controller.RegisterController;
import com.connected.parking.utils.AppStatus;
 
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class GuideViewPagerAdapter extends PagerAdapter{

	// 定义列表页
	private List<View> views;
	private Activity activity;
	private static final String SHAREDPREFERENCES_NAME = "NotifSwipe";
	
	Typeface arialFont;
	
	public GuideViewPagerAdapter(List<View> views, Activity activity){
		this.views = views;
		this.activity = activity;
	}
	
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if(views != null){
			return views.size();
		}
		return 0;
	}
	 
	@Override
	public void destroyItem(View container, int position, Object object) {
		// TODO Auto-generated method stub
		((ViewPager) container).removeView(views.get(position));
	}


	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		// TODO Auto-generated method stub
		return arg0 == arg1;
	}


	@Override
	public Object instantiateItem(View container, int position) {
		// TODO Auto-generated method stub
		((ViewPager) container).addView(views.get(position), 0);
		if (position == views.size() - 1){
			/*ImageView startButton = (ImageView)container.findViewById(R.id.guide__imageButton);
			startButton.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					arg0.setBackgroundResource(R.drawable.butten_pressed);
					//setGuided();
					//goHome();
				}

			});*/
			//Intent intent = new Intent(activity, ProfileController.class);
			//activity.finish();
			//activity.startActivity(intent);
			Button login = (Button)container.findViewById(R.id.login);
			Button signup = (Button)container.findViewById(R.id.signup);
			arialFont = Typeface.createFromAsset(activity.getAssets(),"Arial.ttf");
			login.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if(!AppStatus.isOnline(activity.getApplicationContext())){
						showUserInvalidAlert();
					}else{
						Intent intent = new Intent(activity, LoginController.class);
						//activity.finish();
						activity.startActivity(intent);
					}
				}
			});
			signup.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if(!AppStatus.isOnline(activity.getApplicationContext())){
						showUserInvalidAlert();
					}else{
						Intent intent = new Intent(activity, RegisterController.class);
						//activity.finish();
						activity.startActivity(intent);
					}
				}
			});
		}
		
		/*if (position == views.size()-1){
			Intent intent = new Intent(activity, ProfileController.class);
			//activity.finish();
			activity.startActivity(intent);
		}*/
		return views.get(position);
	}
 
	/*
	 * 显示未联网警告
	 */
	private void showUserInvalidAlert() {
		// TODO Auto-generated method stub
		
		LayoutInflater inflater = activity.getLayoutInflater();
		View layout = inflater.inflate(R.layout.toast_layout,
		                               (ViewGroup) activity.findViewById(R.id.toast_layout_root));

		TextView text = (TextView) layout.findViewById(R.id.text);
		text.setText("Please check your network.");
		text.setTypeface(arialFont);
		text.setTextColor(Color.parseColor("#00B2FF"));

		Toast toast = new Toast(activity.getApplicationContext());
		toast.setGravity(Gravity.NO_GRAVITY, 0, 50);
		toast.setDuration(Toast.LENGTH_SHORT);
		toast.setView(layout);
		toast.show();
	}

}
