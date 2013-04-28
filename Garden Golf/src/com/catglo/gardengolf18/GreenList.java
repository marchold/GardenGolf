package com.catglo.gardengolf18;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.impl.client.DefaultHttpClient;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class GreenList extends ListActivity {

 
	private class MyAdapter extends ArrayAdapter<ListItem> {
		ListItem[] items;
        public MyAdapter(Context context, int textViewResourceId, ListItem[] items) {
        	super(context, textViewResourceId, items);
            this.items = items;   
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            View v = convertView;
            if (v == null) {
                LayoutInflater vi = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = vi.inflate(R.layout.row, null);
            }
            if (position<items.length ){
                ListItem o = items[position];
          
                //TextView tt = (TextView) v.findViewById(R.id.toptext);
                ImageView icon = (ImageView) v.findViewById(R.id.icon);
                TextView title = (TextView) v.findViewById(R.id.title);
                TextView score = (TextView) v.findViewById(R.id.score);
                
                if (title != null) {
                	title.setText(o.name);  
                  	title.setTypeface(font);
                  	title.setTextSize(26);
                }
                if (icon != null){
                	if (position<=unlockedGames){
                		icon.setImageResource(o.resource);
                		if (position==0){
                			score.setVisibility(View.INVISIBLE);
                		} else {
	                		if (o.score==Integer.MAX_VALUE){
	                			score.setText("");
	                		}else {
	                			score.setText(""+o.score);
	                		}
                		}		
                	}else{
                		icon.setImageResource(R.drawable.icon_lock);
                		score.setText("");
                	}
                }
        
            }
            return v;
        }
 	}
 
 	private MyAdapter m_adapter;
 	
 	static void updateHighScores(){
 		Thread thread = new Thread(new Runnable(){public void run(){
		    //name">fish<
			final Pattern parseGame = Pattern.compile("name\\\"\\>(\\w+)\\<");
			//best">5</
			final Pattern parseBest = Pattern.compile("best\\\"\\>(\\w+)\\<");
			final Pattern parseAvg = Pattern.compile("average\\\"\\>([\\w\\.]+)\\<");
			final Pattern parseWorst = Pattern.compile("worst\\\"\\>(\\w+)\\<");
			
			HttpDataFetcher get = new HttpDataFetcher(new DefaultHttpClient(),"http://www.catglo.com/gardengolf_get_scores.php",new DoForEachLine(){public void parseAndStore(String line) {
				final Matcher gameMatcher  = parseGame.matcher(line);
				final Matcher bestMatcher  = parseBest.matcher(line);
				final Matcher avgMatcher   = parseAvg.matcher(line);
				final Matcher worstMatcher = parseWorst.matcher(line);
				String gameName=null;
				int best=-1;
				float avg=-1;
				int worst=-1;
				if (gameMatcher.find()){
					gameName=gameMatcher.group(1);
				}
				if (gameName!=null){
					if (bestMatcher.find()){
						best = new Integer(bestMatcher.group(1));
					}
					if (avgMatcher.find()){
						avg = new Float(avgMatcher.group(1));
					}
					if (worstMatcher.find()){
						worst = new Integer(worstMatcher.group(1));
					}
					GreenList.setScore(gameName, best, worst, avg);
				}
			}});
			get.fetchAndParse(); 
		 }});
		 thread.start();
 	}
 	
 	
 	
 	
 	static void setScore(String greenName, int best, int worst, float avg){
 		for (int i = 0; i < greenList.length; i++){
 			if (greenName.compareTo(greenList[i].dbname)==0){
 				greenList[i].average=avg;
 				greenList[i].best=best;
 				greenList[i].worst = worst;
 			}
 		}
 	}

	private Typeface font;
	SharedPreferences					sharedPreferences;
	private SharedPreferences.Editor	prefEditor;
	static int unlockedGames=0;

	private TextView textBox;
	private RelativeLayout highScoreArea;
	private TextView bestScore;
	private TextView avgScore;
	private TextView upToBestScore;
	private TextView upToAvgScore;
	private TextView purchaseText;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		 super.onCreate(savedInstanceState);
	     setContentView(R.layout.green_list);
	    
	     sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
		 prefEditor = sharedPreferences.edit();
		
		 String uc = sharedPreferences.getString("unlockCode","1"); 
		 if (uc.compareTo("5star")==0){
			 zzz_Version.isFree=false;
			 zzz_Version.freeLevels=19;
		 }
		 
		 if (sharedPreferences.getBoolean("unlocked_by_review",false)){
			 zzz_Version.isFree=false;
			 zzz_Version.freeLevels=19;
		 }
		 
		 if (greenList[0].intent==null){
			 greenList[0].intent  = new Intent(getApplicationContext(),Instructions.class);
			 greenList[1].intent  = new Intent(getApplicationContext(),Carol.class);
			 greenList[2].intent  = new Intent(getApplicationContext(),FishPond.class);
			 greenList[3].intent  = new Intent(getApplicationContext(),LewisArmstrong.class);
			 greenList[4].intent  = new Intent(getApplicationContext(),WizardOfOz.class);
			 greenList[5].intent  = new Intent(getApplicationContext(),Steamer.class);
			 greenList[6].intent  = new Intent(getApplicationContext(),Elephant.class);
			 greenList[7].intent  = new Intent(getApplicationContext(),FireEater.class);
			 greenList[8].intent  = new Intent(getApplicationContext(),GinStill.class);
			 greenList[9].intent  = new Intent(getApplicationContext(),GingerAndFred.class);
			 greenList[10].intent  = new Intent(getApplicationContext(),HotTub.class);
			 greenList[11].intent = new Intent(getApplicationContext(),Aerheart.class);
			 greenList[12].intent = new Intent(getApplicationContext(),Bicycle.class);
			 greenList[13].intent = new Intent(getApplicationContext(),BillyHoliday.class);
			 greenList[14].intent = new Intent(getApplicationContext(),Tiger.class);
			 greenList[15].intent = new Intent(getApplicationContext(),ModelT.class);
			 greenList[16].intent = new Intent(getApplicationContext(),Charley.class);
			 greenList[17].intent = new Intent(getApplicationContext(),Jester.class);
			 greenList[18].intent = new Intent(getApplicationContext(),Shirly.class);
			 greenList[19].intent = new Intent(getApplicationContext(),CharleyHills.class);
		 }
		 
	     if (zzz_Version.isFree){
	    	 unlockedGames = sharedPreferences.getInt("unlockedGames", 0);
	 		
		     if (greenList.length!=zzz_Version.freeLevels){
		    	 ListItem[] greenListFree = new ListItem[zzz_Version.freeLevels];
		    	 for (int i = 0; i < zzz_Version.freeLevels; i++){
		    		 greenListFree[i] = greenList[i];
		    	 } 
		    	 greenList = greenListFree;
		     }
	    	 if (unlockedGames>2){
	    		 purchaseText = (TextView)findViewById(R.id.purchaseText);
	    		 purchaseText.setVisibility(View.VISIBLE);
	    	 }
	     } else {
	    	 unlockedGames = sharedPreferences.getInt("unlockedGames", 3);
	     }
	     unlockedGames = greenList.length-1;
	     
	     for (int i =0; i < greenList.length;i++){
			 greenList[i].intent.putExtra("number", i);
		 }
		
	     prefEditor.putInt("unlockedGames", unlockedGames);
	     prefEditor.commit();
	     
	     
	     m_adapter = new MyAdapter(this,R.layout.row,greenList);
	     setListAdapter(m_adapter);
        
         
         getListView().setAnimationCacheEnabled(false);
     //    getListView()savedInstanceState.
          
         font = Typeface.createFromAsset(getAssets(), "fonts/rstoulou.ttf");  
         
     	 
		 for (int i =0; i < greenList.length-1;i++){
			 greenList[i+1].score = sharedPreferences.getInt("score"+i, Integer.MAX_VALUE);
		 }
		 
		 highScoreArea = (RelativeLayout)findViewById(R.id.highScoreArea);
		 highScoreArea.setVisibility(View.GONE);
		 
		 bestScore = (TextView)findViewById(R.id.this_best_score);
		 avgScore = (TextView)findViewById(R.id.this_avg_score);
		 upToBestScore = (TextView)findViewById(R.id.upToBestScore);
		 upToAvgScore = (TextView)findViewById(R.id.upToAvgScore);
		 
	/*	 getListView().setOnItemSelectedListener(new OnItemSelectedListener(){
			 public void onItemSelected(AdapterView<?> arg0, View arg1, int item, long id) {
				 highScoreArea.setVisibility(View.VISIBLE);
				 bestScore.setText(""+greenList[item].best);
				 avgScore.setText(""+greenList[item].average);
				 int totalBest=0;
				 float totalAvg=0;
				 for (int i = 0; i <= item;i++){
					 totalBest += greenList[i].best;
					 totalAvg+= greenList[i].average;
				 }
				 upToBestScore.setText(""+totalBest);
				 upToAvgScore.setText(""+totalAvg);
				 
			 }

 			 public void onNothingSelected(AdapterView<?> arg0) {
 				highScoreArea.setVisibility(View.GONE);
			 }
		 });	*/	 
    }

	@Override
	public void onPause(){
		super.onPause();
	}
    
	@Override
	public void onResume(){
		super.onResume();
		m_adapter.notifyDataSetChanged();
	}
		 
	
	static ListItem[] greenList = new ListItem[]{
		new ListItem(R.string.Instructions,R.drawable.icon,4,40,"instructions"),
		new ListItem(R.string.Carol,R.drawable.list_image_carolombard,4,4,"carol"),
		new ListItem(R.string.goldfish,R.drawable.list_image_pond,4,4,"fish"),
		new ListItem(R.string.lewis,R.drawable.list_image_louisarmstrong,6,6,"lewis"),
		new ListItem(R.string.wizardofoz,R.drawable.icon_slippers,3,4,"wizard"),
		new ListItem(R.string.sailboat,R.drawable.list_image_sailboat,4,10,"boat"),
		new ListItem(R.string.elephant,R.drawable.icon_elephant,4,10,"elephant"),
		new ListItem(R.string.fireeater,R.drawable.list_image_fireeater,7,13,"fire"),
		new ListItem(R.string.ginstill,R.drawable.list_image_ginstill,8,12,"ginstill"),
		new ListItem(R.string.gingerandfred,R.drawable.list_image_gingerfred,7,10,"ginger"),
		new ListItem(R.string.hottub,R.drawable.list_image_hottub_flower,4,10,"hottub"),
		new ListItem(R.string.Amelia,R.drawable.list_image_amelia,6,10,"plane"),
		new ListItem(R.string.bicycle,R.drawable.list_image_bicycle,4,10,"bicycle"),
		new ListItem(R.string.billie,R.drawable.list_image_billieholiday,3,10,"musicbox"),
		new ListItem(R.string.tiger,R.drawable.icon_tiger,6,10,"tiger"),
		new ListItem(R.string.modelt,R.drawable.icon_modelt,6,10,"modelt"),
		new ListItem(R.string.charlie,R.drawable.icon_chaplen,8,10,"silentfilm"),
		new ListItem(R.string.jester,R.drawable.icon_jester,20,20,"jestermaze"),
		new ListItem(R.string.shirley,R.drawable.list_image_shirley_temple,3,10,"fall"),	
		new ListItem(R.string.charleyHills,R.drawable.list_image_charliechaplain,3,10,"pyramid"),	
	};
 
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id){
		if (position>unlockedGames)
			return;
		super.onListItemClick(l, v, position, id);
		startActivity(greenList[position].intent);
		updateHighScores();
		finish();
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
	
	static final int SETTINGS = 1;
	static final int BUY_NOW = 2;
	
	/* Creates the menu items */
	public boolean onCreateOptionsMenu(final Menu menu) {
		menu.add(0, SETTINGS, 0, R.string.settings).setIcon(android.R.drawable.ic_menu_preferences);
		
		if (zzz_Version.isFree){
			menu.add(1, BUY_NOW, 0, R.string.buyFull).setIcon(android.R.drawable.ic_menu_more);		
		}
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
		}
		return false;
	}
}
