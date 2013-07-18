package com.connected.parking.views;

import java.util.List;

import com.connected.parking.controller.ProfileController;
 
import android.app.Activity;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class GuideViewPagerAdapter extends PagerAdapter{

	// 定义列表页
	private List<View> views;
	private Activity activity;
	private static final String SHAREDPREFERENCES_NAME = "NotifSwipe";
	
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
			Intent intent = new Intent(activity, ProfileController.class);
			activity.finish();
			activity.startActivity(intent);
		}
		return views.get(position);
	}
 
	

}
