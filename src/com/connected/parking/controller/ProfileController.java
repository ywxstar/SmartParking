package com.connected.parking.controller;
   
import java.util.ArrayList;
  
import android.app.NotificationManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.connected.parking.R;    
import com.connected.parking.views.CarStatusFragment;
import com.connected.parking.views.ColorFragment; 
import com.connected.parking.views.MainFragmentPagerAdapter;
import com.connected.parking.views.SearchFragment;
import com.connected.parking.views.UserProfileFragment; 
import com.slidingmenu.lib.SlidingMenu;
  

public class ProfileController extends BaseActivity {

	private ActionBar actionBar;
	private ViewPager vp;
	private ArrayList<Fragment> fragmentList;
	private SearchFragment search_fragment = null;
	private CarStatusFragment car_status_fragment = null;
	private UserProfileFragment uer_profile_fragment = null; 
	private NotificationManager notificationManager = null;
	
	private Tab tabSearch;
 	private Tab tabStatus;
 	private Tab tabProfile; 
	
	public ProfileController() {
		super(R.string.viewpager);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		 
		notificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
		
		vp = new ViewPager(this); 
		vp.setId("VP".hashCode());
		fragmentList = new ArrayList<Fragment>();
		// 初始化Fragment，传入fragmentList
		search_fragment = new SearchFragment(ProfileController.this);
		car_status_fragment = new CarStatusFragment(ProfileController.this, this, notificationManager);
		uer_profile_fragment = new UserProfileFragment(ProfileController.this, this, notificationManager); 
		
		fragmentList.add(search_fragment);
		fragmentList.add(car_status_fragment);
		fragmentList.add(uer_profile_fragment);  
		vp.setAdapter(new MainFragmentPagerAdapter(getSupportFragmentManager(), fragmentList));
		setContentView(vp); 
		vp.setOnPageChangeListener(new OnPageChangeListener() {
	 
			public void onPageScrollStateChanged(int arg0) { }

	 
			public void onPageScrolled(int arg0, float arg1, int arg2) { }
 
			public void onPageSelected(int position) {
				switch (position) {
				case 3:
					getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
					break;
				default:
					getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
					break;
				}
			}

		});
		 
		vp.setCurrentItem(0);
		getSlidingMenu().setMode(SlidingMenu.RIGHT);
		getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN); 
		vp.setOnPageChangeListener(new mOnPageChangeListener());
		
		actionBar = getSupportActionBar();  
		//actionBar.setDisplayHomeAsUpEnabled(true);
		//actionBar.setIcon(R.drawable.logo);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
//		actionBar.setDisplayShowTitleEnabled(false);
//		actionBar.setDisplayShowHomeEnabled(false);
		 
		tabSearch = actionBar.newTab().setText("Search").setTabListener(new searchFragmentTabListener());
		actionBar.addTab(tabSearch);
		tabStatus = actionBar.newTab().setText("Car Status").setTabListener(new statusFragmentTabListener());
		actionBar.addTab(tabStatus); 
		tabProfile = actionBar.newTab().setText("Profile").setTabListener(new profileFragmentTabListener());
		actionBar.addTab(tabProfile); 
		actionBar.setSelectedNavigationItem(0); 
		ColorDrawable color = new ColorDrawable(Color.RED);
		color.setAlpha(255);
		actionBar.setBackgroundDrawable(color);
		
		//getSlidingMenu().setSecondaryMenu(R.layout.setting_controller);
		//getSlidingMenu().setSecondaryShadowDrawable(R.drawable.shadowright);
		/*getSupportFragmentManager()
		.beginTransaction()
		.replace(R.id.menu_frame_two, new SampleListFragment())
		.commit();*/
		
		
	}
	
	private class mOnPageChangeListener implements OnPageChangeListener{

		@Override
		public void onPageScrollStateChanged(int arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onPageSelected(int arg0) {
			// TODO Auto-generated method stub 
			actionBar.setSelectedNavigationItem(arg0);
		} 
	}
	
	private class searchFragmentTabListener implements ActionBar.TabListener{
	 
		@Override
		public void onTabSelected(Tab tab, FragmentTransaction ft) {
			// TODO Auto-generated method stub 
			vp.setCurrentItem(0);
		}

		@Override
		public void onTabUnselected(Tab tab, FragmentTransaction ft) {
			// TODO Auto-generated method stub 
		} 

		@Override
		public void onTabReselected(Tab tab, FragmentTransaction ft) {
			// TODO Auto-generated method stub
			
		}
		
	}
	 
	private class statusFragmentTabListener implements ActionBar.TabListener{
 
		public void onTabReselected(Tab tab, FragmentTransaction ft) {
			// TODO Auto-generated method stub 
		}

		@Override
		public void onTabSelected(Tab tab, FragmentTransaction ft) {
			// TODO Auto-generated method stub 
			vp.setCurrentItem(1);
		}

		@Override
		public void onTabUnselected(Tab tab, FragmentTransaction ft) {
			// TODO Auto-generated method stub 
		}
		
	}
	 
	private class profileFragmentTabListener implements ActionBar.TabListener {

		@Override
		public void onTabReselected(Tab tab, FragmentTransaction ft) {
			// TODO Auto-generated method stub 
		}

		@Override
		public void onTabSelected(Tab tab, FragmentTransaction ft) {
			// TODO Auto-generated method stub 
			vp.setCurrentItem(2);
		}

		@Override
		public void onTabUnselected(Tab tab, FragmentTransaction ft) {
			// TODO Auto-generated method stub 
		}
		
	}
	  
}