package com.connected.parking.controller;
  
import com.connected.parking.R;     

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient; 

public class LearnMoreController extends Activity{

	//PullToRefreshListView mPullRefreshListView;
	//ListView list_view;
	//PullToRefreshWebView mPullRefreshWebView;
	WebView mWebView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.learn_more);
		
/*
        final ActionBar actionBar = (ActionBar) findViewById(R.id.actionbar);
        actionBar.setHomeAction(new IntentAction(this, createIntent(this), R.drawable.logo));
        actionBar.setTitle("Home");

        final Action shareAction = new IntentAction(LearnMoreController.this, createShareIntent(), R.drawable.icon);
        actionBar.addAction(shareAction);*/
		
		/*mPullRefreshWebView = (PullToRefreshWebView) findViewById(R.id.pull_refresh_webview);
		mWebView = mPullRefreshWebView.getRefreshableView();

		mWebView.getSettings().setJavaScriptEnabled(true);
		mWebView.setWebViewClient(new SampleWebViewClient());
		mWebView.loadUrl("http://www.baidu.com");*/
	}
	
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
	
	private static class SampleWebViewClient extends WebViewClient {
		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			view.loadUrl(url);
			return true;
		}
	}
	
	
}
