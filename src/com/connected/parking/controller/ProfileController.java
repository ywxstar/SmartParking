package com.connected.parking.controller;
   
import java.util.ArrayList;
  
import android.app.NotificationManager;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment; 
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener; 
import android.view.Gravity; 
import android.view.LayoutInflater; 
import android.view.ViewGroup; 
import android.view.ViewGroup.LayoutParams; 
import android.widget.Button; 
import android.widget.PopupWindow; 

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.connected.parking.R;    
import com.connected.parking.views.CarStatusFragment; 
import com.connected.parking.views.MainFragmentPagerAdapter;
import com.connected.parking.views.SearchFragment;
import com.connected.parking.views.UserProfileFragment; 
import com.slidingmenu.lib.SlidingMenu;
  

public class ProfileController extends BaseActivity {

	//private ActionBar actionBar;
	private ViewPager vp;
	private ArrayList<Fragment> fragmentList;
	private SearchFragment search_fragment = null;
	private CarStatusFragment car_status_fragment = null;
	private UserProfileFragment uer_profile_fragment = null; 
	private NotificationManager notificationManager = null;
	
	private Tab tabSearch;
 	private Tab tabStatus;
 	private Tab tabProfile; 
 	
 	private PopupWindow popupWindow;
 	private Button profile_setting_btn = null;
 	private Button log_out = null;
	
	public ProfileController() {
		super(R.string.viewpager);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		 
		//setContentView(R.layout.user_profile_controller);//vp); 
		notificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
		
		vp = /*(ViewPager)findViewById(R.id.view_pager);*/new ViewPager(this); 
		vp.setId("VP".hashCode());
		fragmentList = new ArrayList<Fragment>();
		// 初始化Fragment，传入fragmentList
		Bundle bundle = new Bundle();
		bundle = getIntent().getExtras();
		search_fragment = new SearchFragment(ProfileController.this);
		car_status_fragment = new CarStatusFragment(ProfileController.this, this, notificationManager);
		uer_profile_fragment = new UserProfileFragment(ProfileController.this, this, notificationManager, bundle); 
		
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
		
	/*	actionBar = getSupportActionBar();  
		//actionBar.setDisplayHomeAsUpEnabled(true);
		//actionBar.setIcon(R.drawable.logo);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		//actionBar.setHomeButtonEnabled(true); 
//		actionBar.setDisplayShowTitleEnabled(false);
//		actionBar.setDisplayShowHomeEnabled(false);
		 
		tabSearch = actionBar.newTab().setIcon(R.drawable.abs__ic_search).setText("Search").setTabListener(new searchFragmentTabListener());
		actionBar.addTab(tabSearch);
		tabStatus = actionBar.newTab().setIcon(R.drawable.pin).setText("Car Status").setTabListener(new statusFragmentTabListener());
		actionBar.addTab(tabStatus); 
		tabProfile = actionBar.newTab().setIcon(R.drawable.profile).setText("Profile").setTabListener(new profileFragmentTabListener());
		actionBar.addTab(tabProfile); 
		actionBar.setSelectedNavigationItem(0); 
		ColorDrawable color = new ColorDrawable(Color.RED);
		color.setAlpha(255);
		actionBar.setBackgroundDrawable(color);*/
		
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
			//actionBar.setSelectedNavigationItem(arg0);
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
	
	///////////////////////////////////////////////////////////
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			//toggle();
			return true;
		case R.id.github:
			//Util.goToGitHub(this);
			OpenPopWindow();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getSupportMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void OpenPopWindow(){
		
		LayoutInflater mLayoutInflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
		ViewGroup menuView = (ViewGroup) mLayoutInflater.inflate(
				R.layout.popup, null, true);
		profile_setting_btn = (Button)menuView.findViewById(R.id.profile_settings);
		log_out = (Button)menuView.findViewById(R.id.log_out);
	
		popupWindow = new PopupWindow(menuView, LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT, true);
		popupWindow.setBackgroundDrawable(new BitmapDrawable());
		popupWindow.setAnimationStyle(R.style.PopupAnimation);
		
		int[] location = new int[2];  
        vp.getLocationOnScreen(location);
		popupWindow.showAtLocation(vp, /*Gravity.CENTER
				| Gravity.CENTER*/Gravity.NO_GRAVITY, location[0] + vp.getWidth(), location[1] 
						- 50*popupWindow.getHeight());
		popupWindow.update();
	}
	  
}