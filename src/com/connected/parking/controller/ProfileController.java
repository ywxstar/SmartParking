package com.connected.parking.controller;
   
import java.util.ArrayList;
  
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.NotificationManager;
import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.support.v4.app.Fragment; 
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener; 
import android.support.v4.widget.CursorAdapter;
import android.view.Gravity; 
import android.view.KeyEvent;
import android.view.LayoutInflater; 
import android.view.View;
import android.view.ViewGroup; 
import android.view.ViewGroup.LayoutParams; 
import android.widget.Button;  
import android.widget.PopupWindow; 
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.widget.SearchView;
import com.actionbarsherlock.widget.SearchView.OnQueryTextListener;
import com.actionbarsherlock.widget.SearchView.OnSuggestionListener;
import com.connected.parking.R;    
import com.connected.parking.utils.DefinedToast;
import com.connected.parking.views.CarStatusFragment; 
import com.connected.parking.views.MainFragmentPagerAdapter;
import com.connected.parking.views.SearchFragment;
import com.connected.parking.views.UserProfileFragment; 
import com.slidingmenu.lib.SlidingMenu;
  

public class ProfileController extends BaseActivity {

	public static Activity ac = null;
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
 	
 	private PopupWindow popupWindow;
 	private Button profile_setting_btn = null;
 	private Button log_out = null;
 	
 	/**
	 * 设置搜索适配
	 */
	private SuggestionsAdapter suggestion;
	
	public ProfileController() {
		super(R.string.viewpager);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		 
		this.ac = this;
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
		
		actionBar = getSupportActionBar();  
		//actionBar.setDisplayHomeAsUpEnabled(true);
		//actionBar.setIcon(R.drawable.logo);
		//actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		//actionBar.setHomeButtonEnabled(true); 
//		actionBar.setDisplayShowTitleEnabled(false);
//		actionBar.setDisplayShowHomeEnabled(false);
		 
		/*tabSearch = actionBar.newTab().setIcon(R.drawable.abs__ic_search).setText("Search").setTabListener(new searchFragmentTabListener());
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
	
	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if(keyCode == KeyEvent.KEYCODE_BACK){
			this.finish();
			overridePendingTransition(R.anim.show_from_left, R.anim.show_from_right);
		}
		return super.onKeyUp(keyCode, event);
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
		final MenuItem item = menu.findItem(R.id.actionbar_search);
		SearchView searchView = (SearchView) item.getActionView();
		searchView.setQueryHint("input, please!");
		//searchView.set
		searchView.setOnQueryTextListener(new OnQueryTextListener() {
			
			@Override
			public boolean onQueryTextSubmit(String query) {
				// TODO Auto-generated method stub
				item.collapseActionView();
				return false;
			}
			@Override
			public boolean onQueryTextChange(String newText) {
				// TODO Auto-generated method stub
				//DefinedToast defToast = new DefinedToast(ProfileController.ac, ProfileController.this);
				//defToast.showAlert(newText);
				return false;
			}
		});
		
		suggestion = getSuggestions();
		searchView.setSuggestionsAdapter(suggestion); 
		searchView.setOnSuggestionListener(new OnSuggestionListener() {
			
			@Override
			public boolean onSuggestionSelect(int position) {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public boolean onSuggestionClick(int position) {
				// TODO Auto-generated method stub
				Cursor c = (Cursor) suggestion.getItem(position);
				String query = c.getString(c.getColumnIndex(SearchManager.SUGGEST_COLUMN_TEXT_1));
				Toast.makeText(ProfileController.this, "Suggestion clicked: " + query, Toast.LENGTH_LONG).show();
			    return true;
				//return false;
			}
		});
		
		/**
		 * 创建一个搜索view
		 */
		//SearchView search = new SearchView(getSupportActionBar().getThemedContext());
		/**
		 * 添加搜索提示
		 */
		//searchView.setQueryHint("Search for contries...");
		/**
		 * 设置textview的监听
		 */
		//searchView.setOnQueryTextListener(this);
		/**
		 * 设置选择搜索结果的监听
		 */
		//searchView.setOnSuggestionListener(this);
		/**
		 * 设置搜索适配
		 */
		//suggestion = getSuggestions();
		//searchView.setSuggestionsAdapter(suggestion);
		
		//return true;
//		MenuItem search=menu.findItem(R.id.actionbar_search);
//        search.collapseActionView();
//        SearchView searchview=(SearchView) search.getActionView();
//        searchview.setIconifiedByDefault(false);
//        SearchManager mSearchManager=(SearchManager)getSystemService(Context.SEARCH_SERVICE);
//        SearchableInfo info=mSearchManager.getSearchableInfo(getComponentName());
//        searchview.setSearchableInfo(info); //需要在Xml文件加下建立searchable.xml,搜索框配置文件
		//return super.onCreateOptionsMenu(menu);
        return true;
	}
	
	
	/**
     * 生成一个Search适配器，其中的SearchManager.SUGGEST_COLUMN_TEXT_1 和下面的bindView是对应的
     * 
     * @return
     */
    private SuggestionsAdapter getSuggestions() {
        String[] columns = { BaseColumns._ID,
                SearchManager.SUGGEST_COLUMN_TEXT_1 };
        MatrixCursor c = new MatrixCursor(columns);
        c.addRow(new String[] { "1", "New_0" });
        c.addRow(new String[] { "2", "New_1" });
        c.addRow(new String[] { "3", "New_2" });
        c.addRow(new String[] { "4", "New_3" });
        return new SuggestionsAdapter(this, c);
    }

    /**
     * 为Search添加搜索结果的类，创建布局和添加搜索结果
     * 
     * @author Administrator
     * 
     */  
	private class SuggestionsAdapter extends CursorAdapter {

        public SuggestionsAdapter(Context context, Cursor c) {
            super(context, c, 0);
            // TODO Auto-generated constructor stub
        }
 
        /**
         * 为每一个item，添加搜索结果
         */
        @Override
        public void bindView(View arg0, Context arg1, Cursor arg2) {
            // TODO Auto-generated method stub
            TextView view = (TextView) arg0;
            int index = arg2.getColumnIndex(SearchManager.SUGGEST_COLUMN_TEXT_1);
            view.setText(arg2.getString(index));
        }

        /**
         * 返回搜索结果的布局，这个是list布局
         */
        @Override
        public View newView(Context arg0, Cursor arg1, ViewGroup arg2) {
            // TODO Auto-generated method stub
            LayoutInflater flater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View v = flater.inflate(android.R.layout.simple_list_item_1, arg2,
                    false);
            return v;
        }

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