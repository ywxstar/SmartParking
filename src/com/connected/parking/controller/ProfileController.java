package com.connected.parking.controller;
  
import java.util.ArrayList; 
import com.connected.parking.R;  
import com.connected.parking.views.CarStatusFragment;
import com.connected.parking.views.MainFragmentPagerAdapter;
import com.connected.parking.views.SearchFragment;
import com.connected.parking.views.UserProfileFragment;
import com.markupartist.android.widget.ActionBar.Action;
import com.markupartist.android.widget.ActionBar.IntentAction;
  
import android.app.FragmentTransaction; 
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle; 
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager; 
import android.support.v4.view.ViewPager.OnPageChangeListener; 
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;  
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;
 
import com.markupartist.android.widget.ActionBar;
import com.markupartist.android.widget.ActionBar.Action;
import com.markupartist.android.widget.ActionBar.IntentAction;
 
public class ProfileController extends FragmentActivity{ 
	
	//private ActionBar actionBar;
	private ViewPager viewPager;
	private ArrayList<Fragment> fragmentList;
	private SearchFragment search_fragment = null;
	private CarStatusFragment car_status_fragment = null;
	private UserProfileFragment uer_profile_fragment = null;
	
//	private Tab tabSearch;
//	private Tab tabStatus;
//	private Tab tabProfile; 
	
	private NotificationManager notificationManager = null;
	
	//=================================================
	//userprofile
	//=================================================
/*	private ImageView user_imge = null;
	private String[] items = new String[] { "选择本地图片", "拍照" };
	// 头像名称 
	private static final String IMAGE_FILE_NAME = "faceImage.jpg";

	 //请求码 
	private static final int IMAGE_REQUEST_CODE = 0;
	private static final int CAMERA_REQUEST_CODE = 1;
	private static final int RESULT_REQUEST_CODE = 2;*/
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//requestWindowFeature(Window.FEATURE_ACTION_BAR);
		setContentView(R.layout.user_profile_controller); 
		notificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
		this.init();
		/*Notification notif = new Notification(R.drawable.logo, "OK", System.currentTimeMillis());
		Intent notificationIntent =new Intent(ProfileController.this, ProfileController.class); // 点击该通知后要跳转的Activity   
	    PendingIntent contentItent = PendingIntent.getActivity(this, 0, notificationIntent, 0);   
	    notif.setLatestEventInfo(this, "contentTitle", "contentText", contentItent); 
		notificationManager.notify(0, notif);*/ 
		
	}
	 
	private void init(){
		
		final ActionBar actionBar = (ActionBar) findViewById(R.id.profile_actionbar);
        actionBar.setHomeAction(new IntentAction(this, createIntent(this), R.drawable.logo));
        actionBar.setTitle("Home");

        final Action shareAction = new IntentAction(ProfileController.this, createShareIntent(), R.drawable.icon);
        actionBar.addAction(shareAction);
		
		initViewPager();
		//actionBar = this.getActionBar(); 
		//actionBar.setBottom(new Button("abc"));
//		actionBar.setDisplayHomeAsUpEnabled(true);
//		actionBar.setTitle("");
//		//actionBar.setIcon(R.drawable.logo);
//		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
//		actionBar.setDisplayShowTitleEnabled(false);
//		actionBar.setDisplayShowHomeEnabled(false);
//		
//		
//		tabSearch = actionBar.newTab().setText("Search").setTabListener(new searchFragmentTabListener());
//		actionBar.addTab(tabSearch);
//		tabStatus = actionBar.newTab().setText("Car Status").setTabListener(new statusFragmentTabListener());
//		actionBar.addTab(tabStatus); 
//		tabProfile = actionBar.newTab().setText("Profile").setTabListener(new profileFragmentTabListener());
//		actionBar.addTab(tabProfile);
//		
//		actionBar.setSelectedNavigationItem(0); 
	}
	
	//===========================================
	//actionbar
	//===========================================
	public static Intent createIntent(Context context) {
        Intent i = new Intent(context, LoginController.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        return i;
	}
	private Intent createShareIntent() {
        final Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, "Shared from the ActionBar widget.");
        return Intent.createChooser(intent, "Share");
    }
	  
	////////////////////////////////////////////////////////////////////////
	private void initViewPager(){
		
		viewPager = (ViewPager) findViewById(R.id.view_pager);
		fragmentList = new ArrayList<Fragment>();
		// 初始化Fragment，传入fragmentList
		search_fragment = new SearchFragment(ProfileController.this);
		car_status_fragment = new CarStatusFragment(ProfileController.this, this, notificationManager);
		uer_profile_fragment = new UserProfileFragment(ProfileController.this, this, notificationManager); 
		
		fragmentList.add(search_fragment);
		fragmentList.add(car_status_fragment);
		fragmentList.add(uer_profile_fragment);
		// 设置viewPager的适配器（自定义继承FragmentPagerAdapter的adapter）
		// 参数分别是FragmentManager和装载Fragment的容器
		viewPager.setAdapter(new MainFragmentPagerAdapter(this.getSupportFragmentManager(),
				fragmentList));
		viewPager.setCurrentItem(0);
		
		// 设置ViewPager的页面切换监听事件
		viewPager.setOnPageChangeListener(new mOnPageChangeListener());
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
	
	/*private class searchFragmentTabListener implements ActionBar.TabListener{
 
		public void onTabReselected(Tab tab, FragmentTransaction ft) {
			// TODO Auto-generated method stub 
		}

		@Override
		public void onTabSelected(Tab tab, FragmentTransaction ft) {
			// TODO Auto-generated method stub 
			viewPager.setCurrentItem(0);
		}

		@Override
		public void onTabUnselected(Tab tab, FragmentTransaction ft) {
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
			viewPager.setCurrentItem(1);
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
			viewPager.setCurrentItem(2);
		}

		@Override
		public void onTabUnselected(Tab tab, FragmentTransaction ft) {
			// TODO Auto-generated method stub 
		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		super.onCreateOptionsMenu(menu);
		 //添加菜单项
        MenuItem add=menu.add(0,0,0,"add");
        MenuItem del=menu.add(0,0,0,"del");
        MenuItem save=menu.add(0,0,0,"save");
        //绑定到ActionBar  
        save.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        add.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        del.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        
        return true;
	}*/
	
	 
	/*public void ChangeImage(View view){
		Toast.makeText(ProfileController.this, "Change Image", 1000).show();
		
		new AlertDialog.Builder(this)
		.setTitle("设置头像")
		.setItems(items, new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				switch (which) {
				case 0:
					Intent intentFromGallery = new Intent();
					intentFromGallery.setType("image/*"); // 设置文件类型
					intentFromGallery
							.setAction(Intent.ACTION_GET_CONTENT);
					startActivityForResult(intentFromGallery, IMAGE_REQUEST_CODE);
					break;
					
				case 1:
					Intent intentFromCapture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
					// 判断存储卡是否可以用，可用进行存储
					if (Tools.hasSdcard()) {

						intentFromCapture.putExtra(
								MediaStore.EXTRA_OUTPUT,
								Uri.fromFile(new File(Environment .getExternalStorageDirectory(), IMAGE_FILE_NAME)));
					}

					startActivityForResult(intentFromCapture, CAMERA_REQUEST_CODE);
					break;
				}
			}
		})
		.setNegativeButton("取消", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		}).show();
		
	}*/
 
	/*
	///////////////////////////////////////////////////////////////////////////////////////////////
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		//结果码不等于取消时候
		if (resultCode != RESULT_CANCELED) {

			switch (requestCode) {
			case IMAGE_REQUEST_CODE:
				startPhotoZoom(data.getData());
				break;
			case CAMERA_REQUEST_CODE:
				if (Tools.hasSdcard()) {
					File tempFile = new File(Environment.getExternalStorageDirectory() + IMAGE_FILE_NAME);
					startPhotoZoom(Uri.fromFile(tempFile));
				} else {
					
					Toast.makeText(ProfileController.this, "未找到存储卡，无法存储照片！", Toast.LENGTH_LONG).show();
				}

				break;
			case RESULT_REQUEST_CODE:
				if (data != null) {
					getImageToView(data);
				}
				break;
			}
		}
		super.onActivityResult(requestCode, resultCode, data); 
	}
	
	/**
	 * 裁剪图片方法实现
	 * 
	 * @param uri
	 *//*
	public void startPhotoZoom(Uri uri) {

		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		// 设置裁剪
		intent.putExtra("crop", "true");
		// aspectX aspectY 是宽高的比例
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		// outputX outputY 是裁剪图片宽高
		intent.putExtra("outputX", 320);
		intent.putExtra("outputY", 320);
		intent.putExtra("return-data", true);
		startActivityForResult(intent, 2);
	}

	*//**
	 * 保存裁剪之后的图片数据
	 * 
	 * @param picdata
	 *//*
	private void getImageToView(Intent data) {
		Bundle extras = data.getExtras();
		if (extras != null) {
			Bitmap photo = extras.getParcelable("data");
			Drawable drawable = new BitmapDrawable(photo);
			user_imge.setImageDrawable(drawable);
		}
	}*/
	
	
  
}
