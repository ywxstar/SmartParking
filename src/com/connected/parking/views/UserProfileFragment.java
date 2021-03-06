package com.connected.parking.views;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.security.acl.NotOwnerException;
import java.util.Arrays;
import java.util.LinkedList;

import com.connected.parking.R;
import com.connected.parking.asyntask.UploadPhotoTask;
import com.connected.parking.controller.ProfileController;
import com.connected.parking.utils.Tools;
import com.slidingmenu.lib.app.SlidingFragmentActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

@SuppressLint("ValidFragment")
public class UserProfileFragment extends Fragment{
 
	private Activity activity = null;
	private Context context = null;
	private NotificationManager notifiManager;
	private View parentView;
	//ImageView user_image;
	//private View view;
	
	private String[] items = new String[] { "选择本地图片", "拍照" };
	//上传图片路径
	private static final String IMAGE_FILE_NAME = "faceImage.jpg"; 
	 //请求码 
	private static final int IMAGE_REQUEST_CODE = 0;
	private static final int CAMERA_REQUEST_CODE = 1;
	private static final int RESULT_REQUEST_CODE = 2;
	
	public UserProfileFragment(Context con , Activity ac,  NotificationManager nm, 
			Bundle bundle){
		this.context = con ;
		this.activity = ac;
		this.notifiManager = nm;
		Log.i("async", bundle.getString("username") + "--------" + bundle.getString("profile_picture"));
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
		
		View view = inflater.inflate(R.layout.user_profile, container, false); 
		ImageView user_image = (ImageView)view.findViewById(R.id.user_image);
	    ImageView user_setting = (ImageView)view.findViewById(R.id.user_setting);
	    ListView listview = (ListView)view.findViewById(R.id.notifi_content);
	    String[] str = { "notify 1", "notify 2", "notify 3", "notify 4"};
	    LinkedList<String> mListItems = new LinkedList<String>();
		mListItems.addAll(Arrays.asList(str));;
	    listview.setAdapter(new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, mListItems));
	    try {  
            Field f = AbsListView.class.getDeclaredField("mFastScroller");  
            f.setAccessible(true);  
            Object o = f.get(listview);  
            f = f.getType().getDeclaredField("mThumbDrawable");  
            f.setAccessible(true);  
            Drawable drawable = (Drawable) f.get(o);  
            drawable = getResources().getDrawable(R.drawable.pic1);  
            f.set(o, drawable);  
        } catch (Exception e) {  
            throw new RuntimeException(e);  
        }  	
	    
		
	    user_setting.setOnClickListener(new UserSettingClickListener());
	    Bitmap tempBitmap = getBitmapResource();
	    if(tempBitmap != null){
	    	user_image.setImageBitmap(tempBitmap);
	    }
		user_image.setOnClickListener(new uploadListener());
		return view; 
	}
	
	class UserSettingClickListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			((SlidingFragmentActivity) activity).toggle();
		}
		
	}
	
	private Bitmap getBitmapResource(){
		Bitmap bitmap = null;
		String file_path = Environment.getExternalStorageDirectory() + "/smartparking/temp.png";
		bitmap = BitmapFactory.decodeFile(file_path);
		return bitmap;
	}
	
	class uploadListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			uploadPhoto();
		} 
	}
	
	public void uploadPhoto(){
		
		new AlertDialog.Builder(context)
		.setTitle("上传图片")
		.setItems(items, new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				switch (which) {
				case 0:
					Intent intentFromGallery = new Intent();
					intentFromGallery.setType("image/*"); // 设置文件类型
					intentFromGallery .setAction(Intent.ACTION_GET_CONTENT);
					startActivityForResult(intentFromGallery, IMAGE_REQUEST_CODE);
					break; 
				case 1:
					Intent intentFromCapture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
					// 判断存储卡是否可以用，可用进行存储
					if (Tools.hasSdcard()) {
						String path = Environment .getExternalStorageDirectory()
								+ "/smartparking";
						File dir = new File(path);
						if(!dir.exists()){ 
							dir.mkdir(); 
						}
						File file = new File(path,  IMAGE_FILE_NAME);
						intentFromCapture.putExtra(MediaStore.EXTRA_OUTPUT,
								Uri.fromFile(file));
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
		
	}
    
	///////////////////////////////////////////////////////////////////////////////////
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		//结果码不等于取消时候
		/*if ( data == null){//resultCode != RESULT_OK) {
			Log.i("StartUpload", "start...");
			if (CAMERA_REQUEST_CODE == requestCode && Tools.hasSdcard()) { 
				 File myCaptureFile = new File(Environment.getExternalStorageDirectory()+ 
						 "/smartparking/"+ IMAGE_FILE_NAME); 
				 startPhotoZoom(Uri.fromFile(myCaptureFile));
			 } else {
				Toast.makeText(context, "未找到存储卡，无法存储照片！", Toast.LENGTH_LONG).show();
			} 
			return ;
		}else{*/
			switch (requestCode) {
			case IMAGE_REQUEST_CODE:  
				Log.i("INFO", "IMAGE_REQUEST_CODE");
				if (Tools.hasSdcard()) {
					if(data!= null){ 
						startPhotoZoom(data.getData());
					}
				} else {
					Toast.makeText(context, "未找到存储卡，无法存储照片！", Toast.LENGTH_LONG).show();
				}
				break;
			case CAMERA_REQUEST_CODE:
				Log.i("INFO", "CAMERA_REQUEST_CODE");
				File myCaptureFile = new File(Environment.getExternalStorageDirectory()+ 
						 "/smartparking/"+ IMAGE_FILE_NAME); 
				startPhotoZoom(Uri.fromFile(myCaptureFile));
				break;
			case RESULT_REQUEST_CODE: 
				Log.i("INFO", "RESULT_REQUEST_CODE");
				if(data != null){
					getImageToView(data); 
				}
				break;
		
			}
		//}
		super.onActivityResult(requestCode, resultCode, data);
	}

	/**
	 * 裁剪图片方法实现
	 * 
	 * @param uri
	 */
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

	/**
	 * 保存裁剪之后的图片数据
	 * 
	 * @param picdata
	 */
	private void getImageToView(Intent data) {
		Bundle extras = data.getExtras();
		if (extras != null) {
			Bitmap photo = extras.getParcelable("data");
			Drawable drawable = new BitmapDrawable(photo);
			((ImageView)parentView.findViewById(R.id.user_image)).setImageDrawable(drawable);
			startUpload(photo);
		}
	}
	/**
	 * 裁剪图片方法实现
	 * 
	 * @param uri
	 */
	public void startUpload(Bitmap bp) {
		File file = new File(Environment.getExternalStorageDirectory() + "/smartparking", "temp.png");
		if(!file.exists()){
			try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		FileOutputStream fOut = null;
        try {
             fOut = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
                 e.printStackTrace();
        }
        bp.compress(Bitmap.CompressFormat.PNG, 100, fOut);
       
        try {
        	fOut.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        try {
            fOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
		new UploadPhotoTask(context, file, this.notifiManager).execute();
	}
	
}
