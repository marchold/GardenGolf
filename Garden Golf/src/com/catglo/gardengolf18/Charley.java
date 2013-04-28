package com.catglo.gardengolf18;

//TODO: fire should push ball
//TODO: fire should look like its blowing in the wind from ball motion
//TODO: ??the flambo should knock the ball

import java.util.Random;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;

public class Charley extends GolfActivity {
    public class CharleyView extends GolfView {
	
	
		private static final long PUTTER_INTERVAL = 100;
		private Bitmap[] charley;
		private Bitmap standing;
		private AnimationAction putterAnimation;
		private int putterStage;
		
		public CharleyView(Context context, int width, int height) {
			super(context, width, height);
			
			setBackgroundResource(R.drawable.charly_background);
			
			charley = new Bitmap[6];
			charley[0] = loadBitmap(R.drawable.charly_frame1);
			charley[2] = loadBitmap(R.drawable.charly_frame2);
			charley[3] = loadBitmap(R.drawable.charly_frame3);
			charley[4] = loadBitmap(R.drawable.charly_frame4);
			charley[5] = loadBitmap(R.drawable.charly_frame5);
			standing = loadBitmap(R.drawable.charly_frame6);	
		
			
			putterAnimation = new AnimationAction(System.currentTimeMillis()+PUTTER_INTERVAL,new Runnable(){
	
			public void run(){
				putterStage++;
				if (putterStage>=charley.length){
					putterStage=0;
				}
				putterAnimation.actionTime=System.currentTimeMillis()+PUTTER_INTERVAL;	
			}});
			
		}
	
		protected void checkIfBumperMovedOverBall(int whichBumper) {
			// TODO Auto-generated method stub	
		}
		
		protected void drawBackground(Canvas canvas){
			super.drawSurface(canvas);
			
			//canvas.drawBitmap(charley[putterStage], 100, 20, null);
	
		}	
		
		protected Coordinate ballStartPoint(){
			return new Coordinate(280,30);
		}
		protected Coordinate ballHolePoint(){
			return new Coordinate(52,288);
		}
		protected Polygon getGreenPolygon(){
			Polygon green = new Polygon(new Point[] {
				new Point(1,66),new Point(162,66),new Point(162,3),new Point(317,3),new Point(318,299),new Point(181,299),new Point(181,478),new Point(2,478),new Point(2,77)	
			});
			return green;
		}
		
		boolean hasMoreBumpers(){
			if (currentBumper<1) return true;
			return false;
		}
	
		static final int SIDE_VIEW=1;
		static final int FRONT_VIEW=2;
		Bumper[] bumpers = {null,
		    new Bumper(new Point[]{new Point(99,475),new Point(157,459),new Point(150,435),new Point(156,420),new Point(150,388),new Point(158,340),new Point(147,287),new Point(156,287),new Point(145,199),new Point(133,176),new Point(131,159),new Point(133,147),new Point(126,144),new Point(132,122),new Point(119,108),new Point(96,107),new Point(90,120),new Point(92,133),new Point(83,143),new Point(91,150),new Point(98,169),new Point(108,182),new Point(105,196),new Point(101,223),new Point(100,241),new Point(82,249),new Point(71,245),new Point(71,253),new Point(40,264),new Point(47,268),new Point(72,265),new Point(76,273),new Point(100,265),new Point(96,290),new Point(101,296),new Point(103,329),new Point(106,388),new Point(115,413),new Point(114,437),new Point(86,445),new Point(89,454),new Point(97,457),new Point(86,466)},SIDE_VIEW),
			new Bumper(new Point[]{new Point(180,218),new Point(190,225),new Point(217,220),new Point(236,224),new Point(251,224),new Point(237,207),new Point(242,144),new Point(235,120),new Point(240,80),new Point(257,132),new Point(261,221),new Point(267,125),new Point(247,54),new Point(233,37),new Point(232,22),new Point(237,12),new Point(227,4),new Point(213,4),new Point(206,20),new Point(212,43),new Point(200,48),new Point(174,76),new Point(185,110),new Point(193,115),new Point(195,180),new Point(201,212)},FRONT_VIEW)
		};
		Bumper getBumper(int whichBumper){
			if (whichBumper>=bumpers.length)
				return null;
			return bumpers[whichBumper];
		}
		
		Bumper getNextBumper(){
			currentBumper++;
			return getBumper(currentBumper);	
		}
		
		@Override
	    void downTheHole(){
	    	super.downTheHole();
	    	Intent data = new Intent(getApplicationContext(),GardenGolf.class);
	    	data.putExtra("Score", numberOfSwings);
	    	data.putExtra("green", greenNumber);
	    	startActivity(data);
	    	Charley.this.finish();
	    }
	}
	
	 @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    	golfView = new CharleyView (this.getApplicationContext(),width,height);
        layout.addView(golfView, 0);
        golfView.setScoreUpdator(this);
        score.setPadding(10, 10, 10, 50);
        LayoutParams lp = new FrameLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
        lp.gravity=Gravity.BOTTOM|Gravity.RIGHT;
        score.setLayoutParams(lp);
    }
}

	
