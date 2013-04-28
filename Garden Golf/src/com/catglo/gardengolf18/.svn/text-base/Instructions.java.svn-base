package com.catglo.gardengolf18;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.FrameLayout.LayoutParams;

public class Instructions extends GolfActivity {
	public class InstructionsView extends GolfView {
		TextView instructionBubble;
		
		private Bitmap[] slipers;
		private Bitmap[] pail;
		private AnimationAction slipersAnimation;
		private AnimationAction pailAnimation;
		private int slipperStage=0;
		private int pailStage=0;
		private Bitmap[] smoke;
		private AnimationAction smokeAnimation;
		protected int smokeStage=-1;
		static final int SLIPERS_INTERVAL = 200;
		static final int PAIL_INTERVAL = 500;
		private static final long SMOKE_INTERVAL = 70;
		
		
		public InstructionsView(Context context, int width, int height) {
			super(context, width, height);
			setBackgroundResource(R.drawable.instructions_background);		
		}
		
	
		synchronized protected void drawBackground(Canvas canvas){
		}
		
		protected Coordinate ballStartPoint(){
			return new Coordinate(290,240);
		}
		protected Coordinate ballHolePoint(){
			return new Coordinate(300,21);
		}
		protected Polygon getGreenPolygon(){
			Polygon green = new Polygon(new Point[] {
				new Point(2,478),
	            new Point(2,2),
	            new Point(318,2),
	            new Point(318,478)
			});
			return green;
		}
		
		
		
		@Override
		protected void onObjectBounce(int id, float force) {
			
		}
		
		synchronized Bumper getBumper(int whichBumper){
			
			return null;
		}
		
		@Override
	    void downTheHole(){
	    	super.downTheHole();
	    	Intent data = new Intent(getApplicationContext(),GardenGolf.class);
	    	data.putExtra("Score", numberOfSwings);
	    	data.putExtra("green", greenNumber);
	    	startActivity(data);
	    	Instructions.this.finish();
	    }
	}
	
	int instruction=0;
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        golfView = new InstructionsView (this.getApplicationContext(),width,height);
        layout.addView(golfView, 0);
        golfView.setScoreUpdator(this);
        score.setPadding(10, 10, 10, 10);
        LayoutParams lp = new FrameLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
        lp.gravity=Gravity.TOP|Gravity.LEFT;
        score.setLayoutParams(lp);
        
        ((InstructionsView)golfView).instructionBubble = new TextView(this);
        final TextView tv = ((InstructionsView)golfView).instructionBubble;
        lp = new FrameLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
        lp.gravity=Gravity.BOTTOM|Gravity.RIGHT;
        lp.setMargins(0, 0, 110, 70);
        
        tv.setLayoutParams(lp);
        tv.setBackgroundResource(R.drawable.bubble_white_bot_right);
        tv.setText(getString(R.string.welcome));
        tv.setTextColor(Color.BLACK);
        tv.setMaxWidth(180);
        tv.setTextSize(18);
        tv.setOnClickListener(new OnClickListener(){public void onClick(View v) {
			++instruction;
			switch (instruction){
			case 1:
				tv.setText(getString(R.string.controlermodes));
			break;
			case 2: 
				tv.setText(getString(R.string.controlfling));
			break;
			case 3: 
				tv.setText(getString(R.string.controltap));
			break;
			case 4: 
				tv.setText(getString(R.string.nowyoutry));
			break;
			case 5:
				tv.setVisibility(View.GONE);
			}
		}});
      
        layout.addView(tv,2);
        
        
	 }
   
}

