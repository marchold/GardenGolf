package com.catglo.gardengolf18;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class Intro extends Activity {
	private Typeface font;
	private SharedPreferences sharedPreferences;
	private Editor prefEditor;
	private TextView title;
	private TextView footer;
	private RelativeLayout introLayout;
	private TextView body;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		 super.onCreate(savedInstanceState);
	           
//		 AdManager.setTestDevices( new String[] { AdManager.TEST_EMULATOR,	// Android emulator 
//				 "FF08642974E848A8A816B6D3B6AEB63F", // My T-Mobile G1 Test Phone 
//				 } );
		 //10-03 10:24:10.977: INFO/AdMobSDK(891): To get test ads on this device use 
	//	 AdManager.setTestDevices( new String[] { "FF08642974E848A8A816B6D3B6AEB63F" } );
		 
		
		 
         font = Typeface.createFromAsset(getAssets(), "fonts/rstoulou.ttf");  
         
     	 sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
		 prefEditor = sharedPreferences.edit();
		 if (zzz_Version.isFree){
			 GreenList.unlockedGames = sharedPreferences.getInt("unlockedGames", 0);
		 } else {
			 GreenList.unlockedGames = sharedPreferences.getInt("unlockedGames", 3);
		 }
		 String userName = sharedPreferences.getString("userName", "");
		 if (userName.compareTo("")==0){
			 Resources res = getResources();
			 CharSequence[] strings = res.getTextArray(R.array.sillynames);
			 Random r = new Random();
			 userName = strings[r.nextInt(strings.length-1)]+" "+r.nextInt(999);
			 prefEditor.putString("userName", userName);
			 prefEditor.putString("VersionInstalled","1");
			 prefEditor.commit();
		 }
		 
		 
		 
		 setContentView(R.layout.intro);
	     title = (TextView)findViewById(R.id.Title);
		 footer = (TextView)findViewById(R.id.whatsonroof); 
		 body = (TextView)findViewById(R.id.body);
		 title.setTypeface(font);
		 footer.setTypeface(font);
		 introLayout = (RelativeLayout)findViewById(R.id.introLayout);
		 
		 GreenList.updateHighScores();
    }
	
	boolean inGame=false;
	
	@Override
	public boolean onTouchEvent(MotionEvent event){
		if (event.getAction() == MotionEvent.ACTION_DOWN && inGame==false){
			inGame=true;
			if (GreenList.unlockedGames==0) { //Handles when we have the intro screen
				body.setVisibility(View.INVISIBLE);
				footer.setVisibility(View.INVISIBLE);
				title.setVisibility(View.INVISIBLE);
				introLayout.setBackgroundResource(R.drawable.carol__frame_1);
				Intent intent = new Intent(getApplicationContext(),Instructions.class);
				intent.putExtra("number", 0);
				startActivityForResult(intent, 0);
				finish();
				return true;
			}else {
				startActivityForResult(new Intent(getApplicationContext(),GreenList.class),1);
				finish();
				return true;
			}
		}
		
		return false;
	}
}
