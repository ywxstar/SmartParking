package com.connected.parking.model;

import java.util.HashMap;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SessionManager {
	
	private static User user = null;
	private static Context context;
	
	// Shared Preferences
    SharedPreferences pref;
     
    // Editor for Shared preferences
    Editor editor;
     
    // Context
    Context _context;
     
    // Shared pref mode
    int PRIVATE_MODE = 0;
     
    // Sharedpref file name
    private static final String PREF_NAME = "ConnectedLiverpool";
     
    // All Shared Preferences Keys
    private static final String IS_LOGIN = "IsLoggedIn";
     
    public static final String KEY_NAME = "name";   
    public static final String KEY_IMG = "img";     
    public static final String KEY_ID = "id";
    public static final String KEY_TOKEN = "token";
	
	public static User getUser() {
		if(user == null) 
			return null;
		
		return user;
	}

	public static void setUser(User user) {
		SessionManager.user = user;
	}

	public static Context getContext() {
		return context;
	}

	public static void setContext(Context context) {
		SessionManager.context = context;
	}
	
	
	 // Constructor
    public SessionManager(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }
     
    /**
     * Create login session
     * */
    public void createLoginSession(String name, String id, String token,String img){
        // Storing login value as TRUE
    	
        editor.putBoolean(IS_LOGIN, true);
        editor.putString(KEY_NAME, name);         
        editor.putString(KEY_ID, id);
        editor.putString(KEY_IMG, img);        
        editor.putString(KEY_TOKEN, token);
         
        // commit changes
        editor.commit();
    }  
      
    /**
     * Get stored session data
     * */
    public HashMap<String, String> getUserDetails(){
        
    	HashMap<String, String> user = new HashMap<String, String>();
        user.put(KEY_NAME, pref.getString(KEY_NAME, null));       
        user.put(KEY_ID, pref.getString(KEY_ID, null));
        user.put(KEY_IMG, pref.getString(KEY_IMG, null));    
        user.put(KEY_TOKEN, pref.getString(KEY_TOKEN, null));
        return user;
    }
    
    /**
     * Check login method wil check user login status
     * If false it will redirect user to login page
     * Else won't do anything
     * */
    public void checkLogin(Class c ){
        // Check login status
        if(!this.isLoggedIn()){
            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(_context, c);
            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); 
            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); 
            // Staring Login Activity
            _context.startActivity(i);
        }
         
    }
    
    /**
     * Clear session details
     * */
    public void logoutUser(Class c){
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit(); 
        // After logout redirect user to Loing Activity
        Intent i = new Intent(_context, c);
        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
         
        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
         
        // Staring Login Activity
        _context.startActivity(i);
    }
     
    /**
     * Quick check for login
     * **/
    // Get Login State
    public boolean isLoggedIn(){
        return pref.getBoolean(IS_LOGIN, false);
    } 


}

    
     
   