package com.connected.parking.views;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import com.connected.parking.R;
import com.connected.parking.asyntask.UploadPhotoTask;
import com.connected.parking.controller.ProfileController;
import com.connected.parking.utils.Tools;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context; 
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
 
@SuppressLint("ValidFragment")
public class CarStatusFragment extends Fragment{

	private Activity activity = null;
	private Context context = null;
	private View parentView;
	
	private Button upload_photos = null;
	private String[] items = new String[] { "选择本地图片", "拍照" };
	//上传图片路径
	private static final String IMAGE_FILE_NAME = "/smartparking/faceImage.jpg"; 
	 //请求码 
	private static final int IMAGE_REQUEST_CODE = 0;
	private static final int CAMERA_REQUEST_CODE = 1;
	private static final int RESULT_REQUEST_CODE = 2;
	
	public CarStatusFragment(Context con, Activity activity){
		this.context = con;
		this.activity = activity;
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
		View view = inflater.inflate(R.layout.user_car_status, container, false);  
		upload_photos = (Button) view.findViewById(R.id.upload_photo);
		upload_photos.setOnClickListener(new uploadClickListener());
		return view; 
	}
	
	class uploadClickListener implements OnClickListener{

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
						intentFromCapture.putExtra(MediaStore.EXTRA_OUTPUT,
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
		
	}
 
	///////////////////////////////////////////////////////////////////////////////////////////////
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub 
		//结果码不等于取消时候
		if ( data == null){//resultCode != RESULT_OK) {
			return ;
		}else{
		switch (requestCode) {
		case IMAGE_REQUEST_CODE:
			Uri originalUri = data.getData();
			String []proj = {MediaStore.Images.Media.DATA};
			Cursor cursor = activity.managedQuery(originalUri, proj, null, null, null);
			int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
			cursor.moveToFirst();
			String path = cursor.getString(column_index);
			File file = new File(path);
			startUpload(file);
			break;
		case CAMERA_REQUEST_CODE:
			if (Tools.hasSdcard()) {
				
				 Bundle extras = data.getExtras(); 
				 Bitmap myBitmap = (Bitmap) extras.getParcelable("data"); 
				 File myCaptureFile = new File(Environment.getExternalStorageDirectory() + IMAGE_FILE_NAME);
				 BufferedOutputStream bos;
				 try {
					bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
					//压缩位图到指定的OutputStream  
		            myBitmap.compress(Bitmap.CompressFormat.PNG, 0, bos);  
		            //刷新此缓冲区的输出流   
					bos.flush(); 
				    bos.close();    
		            //File tempFile = new File(Environment.getExternalStorageDirectory() + IMAGE_FILE_NAME);
					startUpload(myCaptureFile);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}  catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}  
	                  
			} else {
				Toast.makeText(context, "未找到存储卡，无法存储照片！", Toast.LENGTH_LONG).show();
			}

			break;
		case RESULT_REQUEST_CODE:
			if (data != null) {
				Uri uri = data.getData();
				String []pro = {MediaStore.Images.Media.DATA};
				Cursor cursor1 = activity.managedQuery(uri, pro, null, null, null);
				int column = cursor1.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
				cursor1.moveToFirst();
				String path1 = cursor1.getString(column);
				File file1 = new File(path1);
				startUpload(file1);
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
	 */
	public void startUpload(File file) {
		new UploadPhotoTask(context, file).execute();
	}
	
}
