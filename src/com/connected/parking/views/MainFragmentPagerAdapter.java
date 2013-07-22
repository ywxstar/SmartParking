package com.connected.parking.views;

import java.util.ArrayList;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

public class MainFragmentPagerAdapter extends FragmentPagerAdapter{

	private ArrayList<Fragment> fragmentList;
	
	/*public MainFragmentPagerAdapter(FragmentManager fm) {
		super(fm);
		// TODO Auto-generated constructor stub
	}*/
	
	public MainFragmentPagerAdapter(FragmentManager fragmentManager, ArrayList<Fragment> fragments) {
		super(fragmentManager);
		
		// TODO Auto-generated constructor stub
		this.fragmentList = fragments;
	}
 
	@Override
	public Fragment getItem(int arg0) {
		// TODO Auto-generated method stub
		return fragmentList.get(arg0);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return fragmentList.size();
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return super.getItemId(position);
	}
	 
}
