package com.connected.parking.controller;
 
import java.net.SocketTimeoutException;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.security.KeyStore;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import com.actionbarsherlock.widget.SearchView;
import com.connected.parking.R;  
import com.connected.parking.model.SessionManager;
import com.connected.parking.views.CustomProgressDialog;
import com.internet.android.http.AsyncHttpClient;
import com.internet.android.http.AsyncHttpResponseHandler;
import com.internet.android.http.RequestParams;

import android.app.Activity;
import android.app.ProgressDialog;
import android.app.SearchManager.OnDismissListener; 
import android.content.DialogInterface;
import android.content.Intent; 
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginController extends Activity{

	private EditText uname;
	private EditText passwd;
	private Button submit; 
	private String response;
	 
	private boolean error = false;	 
	HttpResponse responseGet; 
    SessionManager session;	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.login_controller2);
		
		/*SearchView searchView = (SearchView)findViewById(R.id.search_view);
		
		searchView.setIconifiedByDefault(true);
		//searchView.setOnQueryTextListener(this);
		searchView.setSubmitButtonEnabled(false);
		searchView.setQueryHint("INPUT");*/

		uname = (EditText) findViewById(R.id.etusername);
		passwd = (EditText) findViewById(R.id.etpassword);
		submit = (Button) findViewById(R.id.blogin); 
		 
		submit.setOnClickListener( new OnClickListener(){
			@Override
			public void onClick(View v){
				/*String username = uname.getText().toString();
				String password = passwd.getText().toString();
				
				if(username.equals("") == true && password.equals("") == true){
					Toast.makeText(LoginActivity.this, "please input user name and user password!", 1000).show();
				}else if(username.equals("") == true){
					Toast.makeText(LoginActivity.this, "please input user name!", 1000).show();
				}else if(password.equals("") == true){
					Toast.makeText(LoginActivity.this, "please input user password!", 1000).show();
				}else{
					new LoginTask().execute();
				}*/
			
 			 /*Intent intent = new Intent(LoginController.this,  ProfileController.class);
 			 intent.putExtra("username",username );
 			 intent.putExtra("profile_picture",img );
 			 intent.putExtra("id",id );
 			 
 			 Bundle bundle=new Bundle();
 			 bundle.putString("username", username);
 			 bundle.putString("profile_picture", img);
 			 bundle.putString("id", id);  
 			 intent.putExtras(bundle); */
				
				Intent intent = new Intent(LoginController.this,  ProfileController.class);
				Bundle bundle = new Bundle();
				bundle.putString("username", "username");
				bundle.putString("profile_picture", "url");
				//bundle.putString("id", id);
//				CustomProgressDialog dialog = CustomProgressDialog.createDialog(LoginController.this);
//				dialog.setMessage("loading...");
//				dialog.show();
				intent.putExtras(bundle);
				startActivity(intent);
				overridePendingTransition(R.anim.in_from_right, R.anim.out_from_left);
				//makeRequest(uname.getText().toString(), passwd.getText().toString());
			}
		});
	}
	
	private void makeRequest(String username, String pass){
		
		AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params1 = new RequestParams();
        params1.put("username", username);
        params1.put("password", pass);
        Log.i("eventstaker", username+"   " + pass);
        client.addHeader("Accept", "application/json;q=0.9");
        client.setTimeout(4000);
         
		 try {
//			 KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
//   		     trustStore.load(null, null);
//   		     MySSLSocketFactory  sf = new MySSLSocketFactory(trustStore);
//   		     sf.setHostnameVerifier(MySSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
//   		     client.setSSLSocketFactory(sf);   
   		}catch (Exception e) {   
   			
   		}         
        
        //client.setSSLSocketFactory(mySocketFactory);
        client.post("http://192.168.2.100/sessions", params1, new AsyncHttpResponseHandler() {
        	private ProgressDialog pDialog;
        	public void onStart() {
                // Initiated the request
    			pDialog = new ProgressDialog(LoginController.this);
    			pDialog.setCancelable(true);
    			pDialog.setProgressStyle(0);
    			pDialog.setMessage("Please Wait");
    			pDialog.show();        		
            }
        	
            public void onSuccess(String response) {
                //System.out.println(response);
   			    Toast.makeText(LoginController.this, response, 1000).show();
 
	        	JSONObject data,user;
				try {
					 data = new JSONObject(response);
					 String token = data.getString("access_token");
					 user = data.getJSONObject("user");
					 String username = user.getString("username"), 
							 id = user.getString("id"), 
							 img =user.getString("profile_picture") ;
	                 session.createLoginSession(username, id,token,img);
	                 
		 			 Intent intent = new Intent(LoginController.this,  ProfileController.class);
		 			 intent.putExtra("username",username );
		 			 intent.putExtra("profile_picture",img );
		 			 intent.putExtra("id",id );
		 			 
		 			 
					 startActivity(intent);
					 overridePendingTransition(R.anim.in_from_right, R.anim.out_from_left);
					 finish();
					 
				} catch (JSONException e) {
					e.printStackTrace();
					Toast.makeText(LoginController.this, "Invalid JSON", 1000).show();
				}   		
            }
            
            public void onFailure(Throwable e, String response) {
               
            	Log.i("eventstaker", "onFailure method is run... :( "+response);
                if(e instanceof java.net.ConnectException){
                	
                	Toast.makeText(LoginController.this, "no network  ", 1000).show();
                }else if(e instanceof HttpResponseException){                
                    HttpResponseException hre = (HttpResponseException) e;
                    int statusCode = hre.getStatusCode();
                    Toast.makeText(LoginController.this, "fail  "+response+"    status:"+statusCode, 1000).show();
                }
            }
            
            public void onFinish() {
    			if (null != pDialog && pDialog.isShowing()) {
    				pDialog.dismiss();
    			}            
    		}
            
            
        });
    }	 
	
	public void ToLearnMore(View view){
		
		Intent intent = new Intent(LoginController.this, LearnMoreController.class);
		startActivity(intent);
		overridePendingTransition(R.anim.zoom_in,R.anim.zoom_out);//R.anim.in_from_right, R.anim.out_from_left);
	}
	
	public void AppSetting(View view){
		Log.i("AppSetting", "appsetting");
		Intent intent1 = new Intent(LoginController.this, SettingController.class);
		startActivity(intent1);
		overridePendingTransition(R.anim.in_from_right, R.anim.out_from_left);
		 
	}
	 
	@Override
	public boolean onCreateOptionsMenu(Menu menu){
		
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_login, menu);
		return true;
	}
	
	 
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		// TODO Auto-generated method stub
		//return super.onMenuItemSelected(featureId, item);
		/*Intent intent = new Intent(LoginActivity.this, AppSetting.class);
		startActivity(intent);
		overridePendingTransition(R.anim.in_from_right, R.anim.out_from_left);*/
		return true;
	}
	
	
	@Override
	public boolean onMenuOpened(int featureId, Menu menu) {
		// TODO Auto-generated method stub
		//return super.onMenuOpened(featureId, menu);
		Intent intent = new Intent(LoginController.this, SettingController.class);
		startActivity(intent);
		overridePendingTransition(R.anim.in_from_right, R.anim.out_from_left);
		return false;
	}


	class LoginTask extends AsyncTask<String, Void, String> implements DialogInterface.OnDismissListener{
 
		private ProgressDialog pDialog;
		/*
		 * step 1
		 */
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			
			pDialog = new ProgressDialog(LoginController.this);
			pDialog.setCancelable(false);
			pDialog.setProgressStyle(0);
			pDialog.setMessage("Please Wait");
			pDialog.show();
		}
		
		/*
		 *  step 2
		 */
		@Override
		protected String doInBackground(String... arg0) {
			// TODO Auto-generated method stub
			String resp = trylogin();
			//Toast.makeText(LoginActivity.this, resp, 1000).show();
			return resp;
		}

		/*
		 * step 3
		 */
		@Override
		protected void onProgressUpdate(Void... values) {
			// TODO Auto-generated method stub
			super.onProgressUpdate(values);
		}
		

		/*
		{
		    "access_token": "fb2e77d.47a0479900504cb3ab4a1f626d174d2d",
		    "user": {
		        "id": "1574083",
		        "username": "snoopdogg",
		        "full_name": "Snoop Dogg",
		        "profile_picture": "http://distillery.s3.amazonaws.com/profiles/profile_1574083_75sq_1295469061.jpg"
		    }
		}						
		*/
		
		/*
		 * step 4
		 */
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub 
			super.onPostExecute(result);
			
			if (null != pDialog && pDialog.isShowing()) {
				pDialog.dismiss();
			}
			
			/*Toast.makeText(LoginController.this, response, 1000).show();
			
			if(response.compareTo("-1") == 0){
				
				Toast.makeText(LoginController.this, "Unknown Error Occured, exiting", 1000).show();
			}else if(response.compareTo("1") == 0){
			
				//Intent i = new Intent(LoginActivity.this,)
			}else{
				
				Toast.makeText(LoginController.this, "Invalid Username/Password", 1000).show();
				//Utils.showToast("Invalid Username/Password", getApplicationContext());
			}*/
			 
			StatusLine statusLine = responseGet.getStatusLine();
	        if(statusLine.getStatusCode() == HttpStatus.SC_OK){
	        	JSONObject data,user;
				try {
					 data = new JSONObject(result);
					 String token = data.getString("access_token");
					 user = data.getJSONObject("user");
					 String username = user.getString("username"), 
							 id = user.getString("id"), 
							 img =user.getString("profile_picture") ;
	                 session.createLoginSession(username, id, token, img);
	                 
		 			 Intent intent = new Intent(LoginController.this,  ProfileController.class);
		 			 intent.putExtra("username", username );
		 			 intent.putExtra("profile_picture", img );
		 			 intent.putExtra("id",id );
		 			  
					 startActivity(intent);
					 overridePendingTransition(R.anim.in_from_right, R.anim.out_from_left);
					 finish();
					 
				} catch (JSONException e) {
					e.printStackTrace();
					Toast.makeText(LoginController.this, "Invalid JSON", 1000).show();
				}
				
	         }else if(statusLine.getStatusCode() == HttpStatus.SC_NOT_FOUND){	          
				
	        	 Toast.makeText(LoginController.this, "Invalid Username/Password", 1000).show();	        	 
	         }else{
	        	 	        	 	        	 
	         }			
		}

		String trylogin(){
			
			String user_name = uname.getText().toString();
	        String password= passwd.getText().toString();    
	        
	        List<BasicNameValuePair> nvps = new ArrayList<BasicNameValuePair>();
	        /*nvps.add(new BasicNameValuePair("method", "signIn"));
	        nvps.add(new BasicNameValuePair("api-key", "NGNkZGRhYjkzY2Z"));
	        nvps.add(new BasicNameValuePair("timestamp",String.valueOf(ts)));*/
	        nvps.add(new BasicNameValuePair("username", user_name));//"pyee"));
	        nvps.add(new BasicNameValuePair("password", password));//"rocyeahyepen"));
	       /* nvps.add(new BasicNameValuePair("api-token",s));
	        nvps.add(new BasicNameValuePair("device-uuid",deviceId));*/
			
	        try{
	        	  
	        	HttpParams myParams = new BasicHttpParams();   
	        	HttpConnectionParams.setConnectionTimeout(myParams, 4000);
	            HttpConnectionParams.setSoTimeout(myParams, 4000);
	            HttpClient client = new DefaultHttpClient(myParams); 
	        	
		        user_name = URLEncoder.encode(user_name, "utf-8");
		        password = URLEncoder.encode(password, "utf-8");
		        
		        /*Resources r = getResources();
			    String getURL = r.getString(R.string.root_url) + "login_user.php" + 
			    		"?uname=" + user_name+ "&pwd=" + password;
		        String getURL = "http://192.168.0.2:8080/sessionsMobile";
			    HttpPost post = new HttpPost(getURL);
			    UrlEncodedFormEntity p_entity = new UrlEncodedFormEntity(nvps,HTTP.UTF_8);
			    post.setEntity(p_entity);
			    HttpResponse responseGet = client.execute(post);  
			   
			    ///////////////////
			    HttpEntity resEntityGet = responseGet.getEntity();  
			    if (resEntityGet != null) {  
			    	response = EntityUtils.toString(resEntityGet);
			    	System.out.println(response);
			    }*/
		        
		        String getURL = "http://192.168.1.129:8080/sessions";
			    HttpPost post = new HttpPost(getURL);
			    post.setHeader("Accept", "application/json;q=0.9");  
			    UrlEncodedFormEntity p_entity = new UrlEncodedFormEntity(nvps,HTTP.UTF_8);
			    post.setEntity(p_entity);
			    //HttpResponse 
			    responseGet = client.execute(post);  
			   
			    ///////////////////
			    HttpEntity resEntityGet = responseGet.getEntity();  
			    if (resEntityGet != null) {  
			    	response = EntityUtils.toString(resEntityGet);
			    	System.out.println(response);
			    }
			    
			    
		    } catch (ClientProtocolException e) {
	            Log.w("HTTP2:",e );
	            error = true;
	            cancel(true);
	        } catch (UnknownHostException  e) {
	        	// no network
	            Log.w("HTTP3:",e );
	            error = true;
	            cancel(true); 
	        }catch (SocketTimeoutException e) {
	            Log.w("HTTP2:",e );
	            error = true;
	            cancel(true);
	        }catch (Exception e) {
	            Log.w("HTTP4:",e );
	            error = true;
	            cancel(true);
	        }
	       /* catch(Exception e){
		    	e.printStackTrace();
	        	response = "-1";
	        }
			*/
			return response;
		}
  
		/*@Override
		public void onDismiss() {
			// TODO Auto-generated method stub
			this.cancel(true);
		}*/

		@Override
		public void onDismiss(DialogInterface dialog) {
			// TODO Auto-generated method stub
			this.cancel(true);
		}
		 
		/*private String getSha(String in) {
			
			String converted = null;
			 try{ 
				 MessageDigest md = MessageDigest.getInstance("SHA1");
		         System.out.println("Message digest object info: ");
		         System.out.println("   Algorithm = "+md.getAlgorithm());
		         System.out.println("   Provider = "+md.getProvider());
		         System.out.println("   toString = "+md.toString());
		         
		         md.update(in.getBytes()); 
		      	 byte[] output = md.digest();
		         System.out.println();
		         System.out.println("SHA1(\""+in+"\") =");
		         System.out.println("   "+bytesToHex(output));
		         converted = bytesToHex(output);
		         Log.i("Con",converted);
			 }catch (Exception e) {
	             System.out.println("Exception: "+e);
			 }
			 return converted;
		 }	*/	
		
	/*	 public String bytesToHex(byte[] b) {
		      
			 char hexDigit[] = {'0', '1', '2', '3', '4', '5', '6', '7',
		                         '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
		      StringBuffer buf = new StringBuffer();
		      for (int j=0; j<b.length; j++) {
		      
		    	  buf.append(hexDigit[(b[j] >> 4) & 0x0f]);
		          buf.append(hexDigit[b[j] & 0x0f]);
		      }
		      return buf.toString();
		 }
		 */
		 
		/* public void tryLogin(String u,String pass,String s){
			 
				InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
		  		//imm.hideSoftInputFromWindow(this.pass.getWindowToken(), 0);
		  		
				DefaultHttpClient client = new DefaultHttpClient();
			    HttpPost httppost = new HttpPost("192.168.0.2");//http://api.ticketline.co.uk//user");
		            
		        List<BasicNameValuePair> nvps = new ArrayList<BasicNameValuePair>();
		        nvps.add(new BasicNameValuePair("method", "signIn"));
		        nvps.add(new BasicNameValuePair("api-key", "NGNkZGRhYjkzY2Z"));
		        nvps.add(new BasicNameValuePair("email",u));
		        nvps.add(new BasicNameValuePair("password",pass));
		        nvps.add(new BasicNameValuePair("api-token",s));
		        nvps.add(new BasicNameValuePair("device-uuid",deviceId));
			        
				Log.e("Username",u);
				Log.e("Password",pass);
		        try {
		        	UrlEncodedFormEntity p_entity = new UrlEncodedFormEntity(nvps,HTTP.UTF_8);
		            httppost.setEntity(p_entity);
		            Log.v("t", nvps.toString());	              
		            HttpResponse response = client.execute(httppost);
		              			              
		            Log.v("t", response.getStatusLine().toString());			              
		            ByteArrayOutputStream ostream = new ByteArrayOutputStream();			              
		            response.getEntity().writeTo(ostream);			          
		            Log.e("HTTP CLIENT", ostream.toString());
		              
		            JSONObject jArray = new JSONObject( ostream.toString());
		            JSONArray  earthquakes = jArray.getJSONArray("categories");
			        	
		      	    JSONObject e = earthquakes.getJSONObject(0);
				    Log.i("Login handle",e.toString());
				    Log.i("id",e.getString("id"));
				    Log.i("email",e.getString("email_address"));	 		

		        	
		        	Intent PR = new Intent(getParent(),UserProfile.class);
					PR.putExtra("username",e.getString("email_address") );
					PR.putExtra("fname",e.getString("first_name") );
					PR.putExtra("lname",e.getString("last_name") );
					PR.putExtra("add1",e.getString("address1") );
					PR.putExtra("add2",e.getString("address2") );
					PR.putExtra("city",e.getString("city") );
					PR.putExtra("post",e.getString("postal_code") );
					PR.putExtra("country",e.getString("country") );
					PR.putExtra("phone",e.getString("phone") );
					PR.putExtra("json",us );
                    
	        	}catch (Exception e) {			        		
	        	  e.printStackTrace();
	              File newxmlfile = new File(Environment.getExternalStorageDirectory()+"/user.xml");
	      	      newxmlfile.delete();			        	
	        	} 
			 }*/ 
		 
	}

	
}
