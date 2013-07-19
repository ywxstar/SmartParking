package com.connected.parking.controller;

import java.util.ArrayList;
import java.util.List;

import com.connected.parking.R; 
import com.connected.parking.utils.AppStatus; 
import com.connected.parking.views.GuideViewPagerAdapter; 

import android.os.Bundle;
import android.os.Environment;
import android.app.Activity; 
import android.graphics.Color;
import android.support.v4.view.ViewPager;  
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;  
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class SplashController extends Activity {
  
	private ViewPager view_pager = null;
	private GuideViewPagerAdapter guideAdapter = null;
	private List<View> views;
	
	private ImageView [] foot_bar = null;
	private int currentPos = 0; 
	 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.splash_controller);
		
		boolean online = AppStatus.isOnline(this); 
		Toast.makeText(SplashController.this, String.valueOf(online), 1000).show(); 
		String hasCon = AppStatus.getConnectionType(this);
		Toast.makeText(SplashController.this, hasCon, 1000).show();
		initGuidePager();
		initFootBar();  
    }
	
	private void initGuidePager(){
		
		LayoutInflater inflater = LayoutInflater.from(this);
		views = new ArrayList<View>();
		views.add(inflater.inflate(R.layout.guide_one, null));
		views.add(inflater.inflate(R.layout.guide_two, null));
		views.add(inflater.inflate(R.layout.guide_three, null));
		views.add(inflater.inflate(R.layout.guide_four, null));

		// 初始化Adpater
		guideAdapter = new GuideViewPagerAdapter(views, this); 
		view_pager = (ViewPager)findViewById(R.id.splash_view_pager);
		view_pager.setAdapter(guideAdapter);
		view_pager.setOnPageChangeListener(new OnPageChangeListener() { 
			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub
				if(arg0 < 0 || arg0 > views.size() - 1 || arg0 == currentPos){
					return ;
				}
				foot_bar[currentPos].setSelected(false);
				foot_bar[arg0].setSelected(true);
				currentPos = arg0; 
			} 
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub 
			} 
			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub 
			}
		});
	}
	
	private void initFootBar(){
		LinearLayout ll = (LinearLayout) findViewById(R.id.guidell);
		foot_bar = new ImageView[views.size()];
		// 循环活动小点图片
		for (int i = 0; i < views.size(); i++) {
			foot_bar[i] = (ImageView) ll.getChildAt(i);
		}
		currentPos = 0;
		foot_bar[currentPos].setSelected(true);
	}
	 
  
}
