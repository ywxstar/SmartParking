package com.connected.parking.views;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.acl.NotOwnerException;

import com.connected.parking.R;
import com.connected.parking.asyntask.UploadPhotoTask;
import com.connected.parking.controller.ProfileController;
import com.connected.parking.utils.Tools;

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
import android.widget.ImageView;
import android.widget.Toast;

@SuppressLint("ValidFragment")
public class UserProfileFragment extends Fragment{
 
	private Activity activity = null;
	private Context context = null;
	private NotificationManager notifiManager;
	private View parentView;
	ImageView user_image;
	
	private String[] items = new String[] { "ѡ�񱾵�ͼƬ", "����" };
	//�ϴ�ͼƬ·��
	private static final String IMAGE_FILE_NAME = "faceImage.jpg"; 
	 //������ 
	private static final int IMAGE_REQUEST_CODE = 0;
	private static final int CAMERA_REQUEST_CODE = 1;
	private static final int RESULT_REQUEST_CODE = 2;
	
	public UserProfileFragment(Context con , Activity ac,  NotificationManager nm){
		this.context = con ;
		this.activity = ac;
		this.notifiManager = nm;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub 
		parentView = init(inflater, container);
		return parentView;
	}

	//==================================
	//��ʼ����� 
	//====================================
	private View init(LayoutInflater inflater, ViewGroup container) {
		
		View view = inflater.inflate(R.layout.user_profile, container, false); 
	    user_image = (ImageView)view.findViewById(R.id.user_image);
	    Bitmap tempBitmap = getBitmapResource();
	    if(tempBitmap != null){
	    	user_image.setImageBitmap(tempBitmap);
	    }
		user_image.setOnClickListener(new uploadListener());
		return view; 
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
		.setTitle("�ϴ�ͼƬ")
		.setItems(items, new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				switch (which) {
				case 0:
					Intent intentFromGallery = new Intent();
					intentFromGallery.setType("image/*"); // �����ļ�����
					intentFromGallery .setAction(Intent.ACTION_GET_CONTENT);
					startActivityForResult(intentFromGallery, IMAGE_REQUEST_CODE);
					break; 
				case 1:
					Intent intentFromCapture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
					// �жϴ洢���Ƿ�����ã����ý��д洢
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
		.setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {

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
		//����벻����ȡ��ʱ��
		/*if ( data == null){//resultCode != RESULT_OK) {
			Log.i("StartUpload", "start...");
			if (CAMERA_REQUEST_CODE == requestCode && Tools.hasSdcard()) { 
				 File myCaptureFile = new File(Environment.getExternalStorageDirectory()+ 
						 "/smartparking/"+ IMAGE_FILE_NAME); 
				 startPhotoZoom(Uri.fromFile(myCaptureFile));
			 } else {
				Toast.makeText(context, "δ�ҵ��洢�����޷��洢��Ƭ��", Toast.LENGTH_LONG).show();
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
					Toast.makeText(context, "δ�ҵ��洢�����޷��洢��Ƭ��", Toast.LENGTH_LONG).show();
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
	 * �ü�ͼƬ����ʵ��
	 * 
	 * @param uri
	 */
	public void startPhotoZoom(Uri uri) {

		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		// ���òü�
		intent.putExtra("crop", "true");
		// aspectX aspectY �ǿ�ߵı���
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		// outputX outputY �ǲü�ͼƬ���
		intent.putExtra("outputX", 320);
		intent.putExtra("outputY", 320);
		intent.putExtra("return-data", true);
		startActivityForResult(intent, 2);
	}

	/**
	 * ����ü�֮���ͼƬ����
	 * 
	 * @param picdata
	 */
	private void getImageToView(Intent data) {
		Bundle extras = data.getExtras();
		if (extras != null) {
			Bitmap photo = extras.getParcelable("data");
			Drawable drawable = new BitmapDrawable(photo);
			user_image.setImageDrawable(drawable);
			startUpload(photo);
		}
	}
	/**
	 * �ü�ͼƬ����ʵ��
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
