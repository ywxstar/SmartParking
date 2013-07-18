package com.connected.parking.asyntask;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import com.connected.parking.controller.ProfileController;

import android.R;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context; 
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

public class UploadPhotoTask extends AsyncTask<String, Void, String>{

	private ProgressDialog pDialog;
	//private Activity activity;
	private Context context;
	private File tempFile = null;
	private static final String UPLOAD_URL = "http://192.168.1.100:8080/"; 
	
	private NotificationManager notificationManager= null;
	//int index = 0;
	
	public UploadPhotoTask(Context con, File file, NotificationManager nm){
		this.tempFile = file;
		this.context = con; 
		this.notificationManager = nm;
	}
  
	//step 4
	@SuppressWarnings("deprecation")
	@Override
	protected void onPostExecute(String result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		
		if (null != pDialog && pDialog.isShowing()) {
			pDialog.dismiss();
		} 
		
		Toast.makeText(context, result, 1000).show();
		
		if(result.compareTo("-1") == 0){ 
			//Toast.makeText(context, "Unknown Error Occured, exiting", 1000).show();
			Notification notification = new Notification(R.drawable.ic_lock_idle_alarm,
					"Unknown Error Occured, exiting", 
					System.currentTimeMillis());
			notification.flags |= Notification.DEFAULT_SOUND;
			Intent notificationIntent =new Intent(context, ProfileController.class); // 点击该通知后要跳转的Activity   
		    PendingIntent contentItent = PendingIntent.getActivity(context, 0, notificationIntent, 0);   
		    notification.setLatestEventInfo(context, "contentTitle", "contentText", contentItent); 
			notificationManager.notify(1, notification);
			//notificationManager.cancel(1);
			
		}else if(result.compareTo("1") == 0){ 
			//Intent i = new Intent(LoginActivity.this,)
			//Toast.makeText(context, "upload success.", 1000).show();
			Notification notification = new Notification(R.drawable.ic_lock_idle_alarm,
					"upload success.", 
					System.currentTimeMillis());
			Intent notificationIntent =new Intent(context, ProfileController.class); // 点击该通知后要跳转的Activity   
		    PendingIntent contentItent = PendingIntent.getActivity(context, 0, notificationIntent, 0);   
		    notification.setLatestEventInfo(context, "contentTitle", "contentText", contentItent); 
			notificationManager.notify(2, notification);
			notificationManager.cancel(2);
		}else{ 
			//Toast.makeText(context, "Invalid", 1000).show(); 
			Notification notification = new Notification(R.drawable.ic_lock_idle_alarm,
					"Invalid", 
					System.currentTimeMillis());
			Intent notificationIntent =new Intent(context, ProfileController.class); // 点击该通知后要跳转的Activity   
		    PendingIntent contentItent = PendingIntent.getActivity(context, 0, notificationIntent, 0);   
		    notification.setLatestEventInfo(context, "contentTitle", "contentText", contentItent); 
			notificationManager.notify(3, notification);
			notificationManager.cancel(3);
		}
	}


	//step 1
	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		pDialog = new ProgressDialog(context);
		pDialog.setCancelable(false);
		pDialog.setProgressStyle(0);
		pDialog.setMessage("Please Wait");
		pDialog.show();
	}


	//step 3
	@Override
	protected void onProgressUpdate(Void... values) {
		// TODO Auto-generated method stub
		super.onProgressUpdate(values);
	}


    //step 2
	@Override
	protected String doInBackground(String... params) {
		// TODO Auto-generated method stub
		String result = upload();
		return result;
	}
	
	public String upload(){
		String result = null;
		
		HttpPost request = new HttpPost(UPLOAD_URL);   
        FileBody fileBody = new FileBody(tempFile);  
        StringBody comment = null;  
        try {  
            comment = new StringBody("1234", Charset.forName(HTTP.UTF_8));  
            //Toast.makeText(MainActivity.this, (CharSequence) comment, 1000).show();
            Log.i("abc", "comment.toString()");
        } catch (UnsupportedEncodingException e) {  
        	e.printStackTrace();
        }  
  
        MultipartEntity entity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE, null, Charset.forName(HTTP.UTF_8));  
        entity.addPart("images", fileBody);  
        entity.addPart("name", comment);   
        request.setEntity(entity);   
        
        HttpClient client = new DefaultHttpClient();  
        HttpResponse response = null;  
        try {  
            response = client.execute(request);  
            HttpEntity resEntityGet = response.getEntity();  
		    if (resEntityGet != null) {  
		    	result = EntityUtils.toString(resEntityGet);
		    	System.out.println(response);
		    }
        } catch (ClientProtocolException e) {  
        	result = "-1";
        	e.printStackTrace();
        } catch (IOException e) {
        	result = "-1";
           e.printStackTrace();
        }  
 
		return result;
	}
	 
} 