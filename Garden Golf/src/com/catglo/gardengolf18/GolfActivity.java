package com.catglo.gardengolf18;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Typeface;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

public class GolfActivity extends Activity implements ScoreUpdator,SensorEventListener {
	 SensorManager sensorManager;
	 static final int sensor = SensorManager.SENSOR_ORIENTATION;
	  
	 int width;
	 int height;
	 FrameLayout layout;
	 protected TextView score;
	 private Typeface font;
	 final Handler	messageHandler	= new Handler();
	 int greenNumber=0;
	
	 int fontColor = 0x00FFFFFF;
	 private Editor prefEditor;
	 
	 @Override
	 public void onCreate(Bundle savedInstanceState) {
		 super.onCreate(savedInstanceState);
		 width = getWindow().getWindowManager().getDefaultDisplay().getWidth();
         height = getWindow().getWindowManager().getDefaultDisplay().getHeight();

         setContentView(R.layout.golf_layout);
         layout = (FrameLayout)findViewById(R.id.golf_layout);
         score = (TextView)findViewById(R.id.textScore);
         font = Typeface.createFromAsset(getAssets(), "fonts/rstoulou.ttf");  
         score.setTypeface(font);
         newScore(0,0xFF);
         
         greenNumber=getIntent().getIntExtra("number", -1);
         
         sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

         SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
         prefEditor = sharedPreferences.edit();
         
         
	 }
	 GolfView golfView;
	 
	 protected void onDestroy(){
		super.onDestroy();
		golfView.destroy();
	 }
	  
	 @Override
	 public void onPause(){
		 super.onPause();
		 golfView.pause();
		 sensorManager.unregisterListener(this);//  .unregisterListener(this);
	 }
    
	 @Override
	 public void onResume(){
		 super.onResume();
		 golfView.resume();
		 sensorManager.registerListener((SensorEventListener)this,sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION) ,  SensorManager.SENSOR_DELAY_GAME);	 
	 }

	
  	 public void newScore(final int s, final int visibility) {
		messageHandler.post(new Runnable(){public void run(){
			score.setText("Score: "+s+"\nPar: "+GreenList.greenList[greenNumber].par);
			int v = (visibility<<24) | fontColor;
			score.setTextColor(v);
		}});
	}
  
  	@Override
  	public boolean onKeyDown(int keyCode, KeyEvent event) {
  	    if (keyCode == KeyEvent.KEYCODE_BACK) {
  	        //moveTaskToBack(true);
  	        Intent data = new Intent(getApplicationContext(),GreenList.class);
	    	startActivity(data);
	    	finish();
  	        return true;
  	    }
  	    return super.onKeyDown(keyCode, event);
  	} 
  	
  	static final int SETTINGS=1;
  	static final int BUY_NOW=2;
  	static final int AIM=3;
  	static final int FLING=4;
  	
  	/* Creates the menu items */
	public boolean onCreateOptionsMenu(final Menu menu) {
		/*menu.add(0, SETTINGS, 0, R.string.settings).setIcon(android.R.drawable.ic_menu_preferences);
		
		if (zzz_Version.isFree){
			menu.add(1, BUY_NOW, 0, R.string.buyFull).setIcon(android.R.drawable.ic_menu_more);		
		}*/
		
		menu.add(0, AIM, 0, R.string.aimControl).setIcon(android.R.drawable.ic_menu_preferences);;
		menu.add(0, FLING, 0, R.string.flingControl).setIcon(android.R.drawable.ic_menu_preferences);;
		
		return true;
	}

	/* Handles item selections */
	public boolean onOptionsItemSelected(final MenuItem item) {
		switch (item.getItemId()) {
		case SETTINGS: 
			final Intent myIntent = new Intent(getApplicationContext(), GolfSettings.class);
			startActivityForResult(myIntent, 0);
			return true;
		case BUY_NOW:
			final Intent marketIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://search?q=com.catglo.gardengolf18")); 
			startActivity(marketIntent);
			return true;
		case AIM:
			GolfView.ControlerMode=GolfView.CONTROLER_AIM;
			prefEditor.putString("gameMode", "aim");
			prefEditor.commit();
			return true;
		case FLING:
			GolfView.ControlerMode=GolfView.CONTROLER_FLING;
			prefEditor.putString("gameMode", "fling");
			prefEditor.commit();
			return true;	
		}
		return false;
	}

	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub
		
	}

	float initialAngle=Float.MAX_VALUE;
	public void onSensorChanged(SensorEvent event) {
	 
	  //if (event.sensor==SensorManager.SENSOR_ORIENTATION){
		  //Log.i("GOLF","sensor "+event.values[0]+", "+event.values[1]+" , "+event.values[2]);
		  if (golfView.ControlerMode==GolfView.CONTROLER_MOTION){
			  if (initialAngle==Float.MAX_VALUE){
				  
				  initialAngle = new Vector(golfView.ball,golfView.ballHolePoint()).getAngle();
				  initialAngle = (360-event.values[0])-initialAngle;
			  }
			  
			  golfView.currentAim = new Vector(1,1);
			  golfView.currentAim.setTotal(45);
			  golfView.currentAim.setAngle(360-event.values[0]-initialAngle);
			  golfView.postInvalidate();
		  }
	  //}
	}
	
}
