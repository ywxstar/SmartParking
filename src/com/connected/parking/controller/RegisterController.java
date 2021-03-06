package com.connected.parking.controller;
  

import java.io.ByteArrayOutputStream;
import java.security.MessageDigest;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.connected.parking.R;
import com.internet.android.http.AsyncHttpClient;
import com.internet.android.http.AsyncHttpResponseHandler;
import com.internet.android.http.RequestParams;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class RegisterController extends Activity{

	EditText txtEmail = null;
	EditText txtPassword = null;
	EditText txtPasswordc = null;
	Button reg_btn = null;
	ImageView return_btn = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.register_controller);
		
		//
		txtPassword = (EditText) findViewById(R.id.txtPassword);
		txtPasswordc = (EditText) findViewById(R.id.txtPasswordc);
		txtEmail = (EditText) findViewById(R.id.txtEmail);
		reg_btn = (Button) findViewById(R.id.bregister);
		return_btn = (ImageView) findViewById(R.id.return_pre);
	}
	
	public void GetBack(View getback){
		//UserSignupActivity.this.setResult(RESULT_OK);
		//UserSignupActivity.this.finish();
		//openDialog();
	
	}
		
/*	public void openDialog() {
		  
		View menuView = View.inflate(this, R.layout.gridview_menu, null);
		
		// ´´½¨AlertDialog
		final AlertDialog menuDialog = new AlertDialog.Builder(this).create();
		menuDialog.setView(menuView);
		menuGrid = (GridView) menuView.findViewById(R.id.gridview);
		menuGrid.setAdapter(getMenuAdapter(menu_name_array, menu_image_array));
		menuGrid.setOnItemClickListener(new OnItemClickListener() {
		 
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				if (arg2 == 11) {
					menuDialog.cancel();
				}
			}
		});
		menuDialog.show();
	}

	private ListAdapter getMenuAdapter(String[] menuNameArray,
			int[] menuImageArray) {
		ArrayList<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();
		for (int i = 0; i < menuNameArray.length; i++) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("itemImage", menuImageArray[i]);
			map.put("itemText", menuNameArray[i]);
			data.add(map);
		}
		SimpleAdapter simperAdapter = new SimpleAdapter(this, data,
				R.layout.item_menu, new String[] { "itemImage", "itemText" },
				new int[] { R.id.item_image, R.id.item_text });
		return simperAdapter;
	
	}*/
	
	 public void CreateUser(View button) {
		  
//		   Long ts = convertDateToMilliSeconds(new Date(System.currentTimeMillis()));    		   
//		   getSha(String.valueOf(ts)+"YWFmOGMzNWJlNjk"); 
		   String email = txtEmail.getText().toString();
		   String password = txtPassword.getText().toString();
  
		 /*  if(!txtEmail.getText().toString().contains("@")){
			   showAlert("'email' is required");
		   }
		   
		   if(txtEmail.getText().toString().length() < 6) {
			   showAlert("email is invalid");
		   }

		  if(txtPassword.length() < 8) {
			  showAlert("Password too short");
			  return;
		  }
		   
		  if(txtPasswordc.getText().length() < 1) {
			  showAlert("'Confirm Password' is required");
			  return;
			  
		  }else if(!txtPassword.getText().toString().equals(txtPasswordc.getText().toString())) {
			  
			  showAlert("Passwords do not match");
			  return;
		  }*/
		
		  /*DefaultHttpClient client = new DefaultHttpClient();
	       HttpPost httppost = new HttpPost("http://api.ticketline.co.uk//user");
	       List<BasicNameValuePair> nvps = new ArrayList<BasicNameValuePair>();
	       nvps.add(new BasicNameValuePair("method", "create"));
	       nvps.add(new BasicNameValuePair("username",email ));
	       nvps.add(new BasicNameValuePair("password",password ));
	       
	       nvps.add(new BasicNameValuePair("api-token",converted ));
	       nvps.add(new BasicNameValuePair("first-name",fname));
	       nvps.add(new BasicNameValuePair("last-name",lname ));
           nvps.add(new BasicNameValuePair("device-uuid",deviceId));
	       nvps.add(new BasicNameValuePair("address1",add1 ));
	       nvps.add(new BasicNameValuePair("address2",add2 ));
	       nvps.add(new BasicNameValuePair("city",city ));
	       nvps.add(new BasicNameValuePair("postal-code",post ));
	       nvps.add(new BasicNameValuePair("country-id","66009" ));
	       nvps.add(new BasicNameValuePair("phone",phone ));
	       nvps.add(new BasicNameValuePair("timestamp",String.valueOf(ts) ));*/
	      /* 
	       nvps.add(new BasicNameValuePair("api-key", "NGNkZGRhYjkzY2Z"));
	       
	       try {
	    	   UrlEncodedFormEntity p_entity = new UrlEncodedFormEntity(nvps,HTTP.UTF_8);
               httppost.setEntity(p_entity);
               HttpResponse response = client.execute(httppost);
               Log.v("t", response.getStatusLine().toString());
              
               ByteArrayOutputStream ostream = new ByteArrayOutputStream(); 
               response.getEntity().writeTo(ostream);
 
               Log.e("HTTP CLIENT", ostream.toString());
              
               int responseCode = response.getStatusLine().getStatusCode();
               String suc =  ostream.toString();
               String s="{\"categories\":["+ostream.toString()+"]}";
	           Log.i("response",s);	              
               switch(responseCode) {
                   case 200:
                	    
						showAlert(suc);
						 Intent AR = new Intent(getApplicationContext(),TicketlineActivity.class);
						 AR.putExtra("TAB", "1");
						           startActivity(AR);
						           finish();
						 	                  
             	   break;
                   case 400:
                	   JSONObject jArray = new JSONObject(s);
    		           JSONArray  earthquakes = jArray.getJSONArray("categories");
    		        	
    			       for(int i=0;i<earthquakes.length();i++){			
    			    	 
    			    	   String e = earthquakes.getString(i); 
    					   if(e.contains("successfully")){
    						   
    					   } else {
    							//showAlert("signup failed");
    					   }
    					}	                	  
    			       break;
           		}
        	} catch (Exception e) { 
        		e.printStackTrace();     
	        } */
	       
	        Log.i("makeRequest", /*username+"   "*/ email); 
	        AsyncHttpClient http_client = new AsyncHttpClient();
	        RequestParams params1 = new RequestParams();
	        //params1.put("username", username);
	        params1.put("email", email);
	        params1.put("password", password);
	        params1.put("api-key", "NGNkZGRhYjkzY2Z");
		                  
	        Log.i("eventstaker", /*email+"   "+*/ password);
	        http_client.addHeader("Accept", "application/json;q=0.9");
	        http_client.setTimeout(4000);
	        //client.setSSLSocketFactory(mySocketFactory);
	        http_client.post("http://192.168.2.100:8080/signup", params1, new AsyncHttpResponseHandler() {
	        	
	        	private ProgressDialog pDialog; 
	            public void onStart() {
	                // Initiated the request
	    			pDialog = new ProgressDialog(RegisterController.this);
	    			pDialog.setCancelable(true);
	    			pDialog.setProgressStyle(0);
	    			pDialog.setMessage("Please Wait");
	    			pDialog.show();        		
	            }
	        	 
	            public void onSuccess(String response) {
	                //System.out.println(response);
	   			    Toast.makeText(RegisterController.this, response, 1000).show(); 
		        	JSONObject data,user;
					try {
						 data = new JSONObject(response);
						 String token = data.getString("access_token");
						 user = data.getJSONObject("user");
						 String username = user.getString("username"), 
								 id = user.getString("id"), 
								 img =user.getString("profile_picture") ;
		                 
			 			 Intent intent = new Intent(RegisterController.this,  ProfileController.class);
			 			 intent.putExtra("username",username );
			 			 intent.putExtra("profile_picture",img );
			 			 intent.putExtra("id",id );
			 			 
			 			 
						 startActivity(intent);
						 overridePendingTransition(R.anim.in_from_right, R.anim.out_from_left);
						 finish();
						 
					} catch (JSONException e) {
						e.printStackTrace();
						Toast.makeText(RegisterController.this, "Invalid JSON", 1000).show();
					}   			    
	   			    
	   			    
	            }
	             
	            public void onFailure(Throwable e, String response) {
	                Log.i("eventstaker", "onFailure method is run... :( "+response);
	                if(e instanceof java.net.ConnectException){
	                	Toast.makeText(RegisterController.this, "no network  ", 1000).show();
	                	
	                }else if(e instanceof HttpResponseException){                
	                    HttpResponseException hre = (HttpResponseException) e;
	                    int statusCode = hre.getStatusCode();
	                    Toast.makeText(RegisterController.this, "fail  "+response+"    status:"+statusCode, 1000).show();
	                }
	            }
	             
	            public void onFinish() {
	    			if (null != pDialog && pDialog.isShowing()) {
	    				pDialog.dismiss();
	    			}            
	    		}
	            
	            
	        });
	 }
	 
	/*public static long convertDateToMilliSeconds(Date date) {
		     
		 return date.getTime();
	}
	 
	 private void getSha(String in) {
		
		 String converted = null;
		 try {
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
	 }
	 
	 public static String bytesToHex(byte[] b) {
	     
		 char hexDigit[] = {'0', '1', '2', '3', '4', '5', '6', '7',
	                         '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
	      StringBuffer buf = new StringBuffer();
	      for (int j=0; j<b.length; j++) {
	         buf.append(hexDigit[(b[j] >> 4) & 0x0f]);
	         buf.append(hexDigit[b[j] & 0x0f]);
	      }
	      return buf.toString();
	 }	*/
	
	
	 private void showAlert(String message) {
			
		 	String alertText = "";
		 
		 	if(message.contains("'email' is required")) {
		 		alertText = "Email address is a required field, please give a suitable email address";
		 	} 
		 	
		 	if(message.contains("email is invalid")) {
		 		alertText = "Sorry, your email is invalid, please try again";
		 	}else if (message.contains("already exists")) {
		 		
		 		alertText = "Your Email is already in use, please login or use another email";
		 	}else if (message.contains("Passwords do not match")) {
		 		
		 		alertText = "Your password and password confirmation do not match, please try again";
		 	}else if (message.contains("Password too short")) {
		 	
		 		alertText = "Your password is too short, you must use at least 8 letters, numbers or special characters";
		 	}else if (message.contains("'first-name' is required")) {
		 	
		 		alertText = "Sorry, your First Name is invalid, please try again";
		 	}else if (message.contains("'last-name' is required")) {
		 	
		 		alertText = "Sorry, your Last Name is invalid, please try again";
		 	}else if (message.contains("'address1' is required")) {
		 	
		 		alertText = "Sorry, the first line of your address is invalid, please try again";
		 	}else if (message.contains("'Country' is required")) {
		 		
		 		alertText = "Sorry, the country field is invalid, please try again";
		 	}else if (message.contains("'postal-code' is required")) {
		 	
		 		alertText = "Sorry, the post-code field is invalid, please try again";
		 	}else if (message.contains("Postcode too short")) {
		 	
		 		alertText = "Sorry, the post-code you input is too short, please try again";
		 	}else if(message.contains("successfully")) {
		 		
		 		alertText = "Signup successful";
		 	}else if(message.contains("signup failed")) {
		 		
		 		alertText = "Sorry, we couldn't create an account at the moment, please try again later";
		 	}else if(message.contains("'Confirm Password' is required")) {
		 	
		 		alertText = "Sorry, your confirm password is invalid, please try again";
		 	}
		 	
		 	
			/*LayoutInflater inflater = getLayoutInflater();
			View layout = inflater.inflate(R.layout.toast_layout,
			                               (ViewGroup) findViewById(R.id.toast_layout_root));

			TextView text = (TextView) layout.findViewById(R.id.text);
			text.setText(alertText);
			text.setTextColor(Color.parseColor("#00B2FF"));

			Toast toast = new Toast(getApplicationContext());
			toast.setGravity(Gravity.NO_GRAVITY, 0, 50);
			toast.setDuration(Toast.LENGTH_SHORT);
			toast.setView(layout);
			toast.show();*/
		}	
	 
	 /*@Override
		public boolean onCreateOptionsMenu(Menu menu){
			// Inflate the menu; this adds items to the action bar if it is present.
			//getMenuInflater().inflate(R.menu.activity_user_signup, menu);
			return true;
		}*/

	
}
