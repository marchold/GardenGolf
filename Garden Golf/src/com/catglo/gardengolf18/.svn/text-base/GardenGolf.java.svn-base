package com.catglo.gardengolf18;




import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class GardenGolf extends Activity {
	private Typeface font;
	private SharedPreferences sharedPreferences;
	private Editor prefEditor;
	private TextView title;
	private TextView footer;
	private RelativeLayout introLayout;
	private TextView body;
	boolean startup=true;
	private DefaultHttpClient client;
	private TextView highScoreText;
	private TextView highScore;
	boolean dialogShowing=false;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		 super.onCreate(savedInstanceState);
	           
         font = Typeface.createFromAsset(getAssets(), "fonts/rstoulou.ttf");  
         
     	 sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
		 prefEditor = sharedPreferences.edit();
		 final String userName = sharedPreferences.getString("userName", "");
		 
		 setContentView(R.layout.score);
		 TextView tapToContinue = (TextView)findViewById(R.id.TapToContinue);
		 tapToContinue.setTypeface(font);
		 score = (TextView)findViewById(R.id.Score);
		 par = (TextView)findViewById(R.id.Par);
		 
		 Intent intent = getIntent();
		 final int s = intent.getIntExtra("Score", 0);
		 final int g = intent.getIntExtra("green", 0);
		 score.setText(""+s);
		 par.setText(""+GreenList.greenList[g].par);
		 TextView unlockedTitle = (TextView)findViewById(R.id.unlockedTitle);
		 TextView unlockedGame = (TextView)findViewById(R.id.unlockedText);	
		 
		 if (s <= GreenList.greenList[g].minimum && g >= GreenList.unlockedGames){
			GreenList.unlockedGames++;
			ImageView unlockedIcon = (ImageView)findViewById(R.id.unlockedIcon);
			if (GreenList.unlockedGames>=GreenList.greenList.length){
				unlockedIcon.setImageResource(R.drawable.trophy);
				unlockedGame.setText(R.string.youWin);
				//unlockedTitle.setText("");
				//unlockedTitle.setTypeface(font);
			} else {
				prefEditor.putInt("unlockedGames", GreenList.unlockedGames);
				unlockedIcon.setImageResource(GreenList.greenList[GreenList.unlockedGames].resource);
				unlockedTitle.setText(GreenList.greenList[GreenList.unlockedGames].name);
				unlockedTitle.setTypeface(font);
			}
		} else { 
			unlockedGame.setText("");
		}
		if (s < GreenList.greenList[g].score & g > 0){
			GreenList.greenList[g-1].score = s;
			prefEditor.putInt("score"+(g-1), s);
		}
		prefEditor.commit();
		
		Thread thread = new Thread(new Runnable(){public void run(){
			client = new DefaultHttpClient();
			String un = userName.replace(' ', '+');
			String requestString = new String("http://www.catglo.com/gardengolf_put_score.php?gamename="+GreenList.greenList[g].dbname+"&games="+s+"&uname="+un);
			final HttpGet request = new HttpGet(requestString);
			try {
				client.execute(request);
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}});
		thread.start();
		
		if (GreenList.greenList[g].best >= s || GreenList.greenList[g].best<0 || dialogShowing){
			HighScoreDialog customizeDialog = new HighScoreDialog(this);
			customizeDialog.gameName.setText(GreenList.greenList[g].name);
			customizeDialog.gameName.setTypeface(font);
			customizeDialog.score.setText(""+s);
			customizeDialog.userName.setText(userName);
			dialogShowing=true;
			customizeDialog.show();
			GreenList.greenList[g].best=s;
		}
		dialogShowing=false; 
		
		highScoreText = (TextView)findViewById(R.id.BestScoreText);
		highScore = (TextView)findViewById(R.id.bestScore);
		if (GreenList.greenList[g].best > 0){
			highScore.setText(""+GreenList.greenList[g].best);
		} else {
			highScoreText.setVisibility(View.GONE);
			highScore.setVisibility(View.GONE);
		}
	}
	
	boolean inGame=false;
	private TextView score;
	private TextView par;
	
	@Override
	public boolean onTouchEvent(MotionEvent event){
		if (event.getAction() == MotionEvent.ACTION_DOWN){
			startActivity(new Intent(getApplicationContext(),GreenList.class));	
			finish();
			return true;
		}
		return false;
	}
	
	@Override
	public void onPause(){
		super.onPause();
	}
    
	@Override
	public void onResume(){
		super.onResume();
		 
	}
	
	@Override
  	public boolean onKeyDown(int keyCode, KeyEvent event) {
  	    if (keyCode == KeyEvent.KEYCODE_BACK) {
  	        finish();
  	    	//moveTaskToBack(true);
  	        //Intent data = new Intent(getApplicationContext(),GardenGolf.class);
	    	//startActivity(data);
  	        return true;
  	    }
  	    return super.onKeyDown(keyCode, event);
  	} 
	

}
