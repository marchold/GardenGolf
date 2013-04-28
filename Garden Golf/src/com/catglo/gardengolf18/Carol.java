package com.catglo.gardengolf18;

import java.util.LinkedList;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;

public class Carol extends GolfActivity {
	public class CarolView extends GolfView {
		private Bitmap frame1;
		private Bitmap frame2;
		private Bitmap frame3;
		private Bitmap frame4;
		private Bitmap frame5;
		private Bitmap frame6;
		public CarolView(Context context, int width, int height) {
			super(context, width, height);
			setBackgroundResource(R.drawable.carol__background);
			frame1 = loadBitmap(R.drawable.carol__frame_1);
			frame2 = loadBitmap(R.drawable.carol__frame_2);
			frame3 = loadBitmap(R.drawable.carol__frame3__8x11y);
			frame4 = loadBitmap(R.drawable.carol__frame4__95x25y);
			frame5 = loadBitmap(R.drawable.carol__frame5__89x8y);
			frame6 = loadBitmap(R.drawable.carol__frame6__55x33y);
			
			LinkedList<AnimationFrame> frames = new LinkedList<AnimationFrame>();
			frames.add(new AnimationFrame(0,0,frame1,1500,0));
			frames.add(new AnimationFrame(0,0,frame2,500,0));
			frames.add(new AnimationFrame(8,11,frame3,500,0));
			frames.add(new AnimationFrame(95,25,frame4,500,0));
			frames.add(new AnimationFrame(89,8,frame5,500,0));
			frames.add(new AnimationFrame(55,33,frame6,500,0));
			AnimationFrames animation = new AnimationFrames();
			animation.frames = frames;
			playAnimationFrames(animation);

		}
		
		protected Coordinate ballStartPoint(){
			return new Coordinate(170,430);
		}
		protected Coordinate ballHolePoint(){
			return new Coordinate(170,63);
		}
		protected Polygon getGreenPolygon(){
			Polygon green = new Polygon(new Point[] {
				new Point(2,412),
	            new Point(60,406),
	            new Point(50,257),
	            new Point(19,132),
	            new Point(26,103),
	            new Point(77,63),
	            new Point(106,33),
	            new Point(127,2),
	            new Point(223,8),
	            new Point(293,80),
	            new Point(297,112),
	            new Point(270,216),
	            new Point(262,381),
	            new Point(318,395),
	            new Point(318,478),
	            new Point(2,478)
			});
			return green;
		}
		
		boolean hasMoreBumpers(){
			if (currentBumper<2) return true;
			return false;
		}
	
		Bumper getNextBumper(){
			currentBumper++;
			switch (currentBumper){
			case 1: return new Bumper(new Point[]{
				new Point(155,121),	
				new Point(163,116),	
				new Point(164,110),	
				new Point(160,105),	
				new Point(163,101),	
				new Point(165,94),	
				new Point(174,96),	
				new Point(180,95),	
				new Point(183,97),	
				new Point(179,105),	
				new Point(182,114),	
				new Point(167,122)
				},1);
			case 2:return new Bumper(new Point[]{
					new Point(126,189),	
					new Point(95,178),	
					new Point(94,150),	
					new Point(117,151),	
					new Point(122,163),	
					new Point(119,180)					
				},2);
			}
			return null;
		}
		
		@Override
	    void downTheHole(){
	    	super.downTheHole();
	    	Intent data = new Intent(getApplicationContext(),GardenGolf.class);
	    	data.putExtra("Score", numberOfSwings);
	    	data.putExtra("green", greenNumber);
	    	startActivity(data);
	    	Carol.this.finish();
	    }
	}
	
	/** Called when the activity is first created. */ 
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    	golfView = new CarolView (this.getApplicationContext(),width,height);
        layout.addView(golfView, 0);
        golfView.setScoreUpdator(this);
        score.setPadding(0, 10, 0, 0);
        LayoutParams lp = new FrameLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
        lp.gravity=Gravity.TOP;
        score.setLayoutParams(lp);
    }  
}
