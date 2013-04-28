package com.catglo.gardengolf18;

//TODO: fire should push ball
//TODO: fire should look like its blowing in the wind from ball motion
//TODO: ??the flambo should knock the ball

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Point;
import android.os.Bundle;

public class Tiger extends GolfActivity {
    public class TigerView extends GolfView {
		
		private Bitmap[] tiger;
		private Bitmap frame2;
		
		
		
		
		public TigerView(Context context, int width, int height) {
			super(context, width, height);
			setBackgroundResource(R.drawable.tiger_background);
			
		}
		
		protected void checkIfBumperMovedOverBall(int whichBumper) {
			// TODO Auto-generated method stub
			
		}
	
	
		
		protected void drawSurface(Canvas canvas){
			super.drawSurface(canvas);
	
		}	
		
		protected Coordinate ballStartPoint(){
			return new Coordinate(247,437);
		}
		protected Coordinate ballHolePoint(){
			return new Coordinate(77,65);
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
		
		boolean hasMoreBumpers(){
			if (currentBumper<1) return true;
			return false;
		}
	
		static final int TIGER_LEFT=1;
		static final int TIGER_RIGHT=2;
		static final int TIGER_BOTTOM=3;
		
		Bumper tigerLeft   = new Bumper(new Point[]{new Point(9,100),new Point(9,119),new Point(8,144),new Point(31,191),new Point(40,240),new Point(25,248),new Point(35,262),new Point(62,262),new Point(63,250),new Point(87,252),new Point(88,258),new Point(124,254),new Point(130,250),new Point(177,248),new Point(175,237),new Point(132,236),new Point(129,203),new Point(102,160),new Point(64,123),new Point(69,107),new Point(80,121),new Point(78,104),new Point(90,103),new Point(60,97),new Point(47,104),new Point(23,104)},TIGER_LEFT); 
		Bumper tigerRight  = new Bumper(new Point[]{new Point(101,26),new Point(113,30),new Point(125,77),new Point(144,77),new Point(152,116),new Point(183,152),new Point(172,175),new Point(198,203),new Point(193,228),new Point(203,230),new Point(208,212),new Point(220,202),new Point(205,185),new Point(237,165),new Point(249,193),new Point(271,218),new Point(256,184),new Point(251,164),new Point(217,128),new Point(194,89),new Point(193,57),new Point(178,26),new Point(181,18),new Point(136,12),new Point(132,25),new Point(113,5),new Point(102,5)},TIGER_RIGHT);
	    Bumper tigerBottom = new Bumper(new Point[]{new Point(70,352),new Point(90,351),new Point(141,322),new Point(165,300),new Point(200,298),new Point(220,312),new Point(257,292),new Point(279,321),new Point(275,335),new Point(262,329),new Point(233,365),new Point(231,387),new Point(207,392),new Point(193,390),new Point(189,400),new Point(200,402),new Point(194,411),new Point(170,411),new Point(159,371),new Point(139,342),new Point(125,339),new Point(100,356),new Point(78,361)},TIGER_BOTTOM);
		Bumper getBumper(int whichBumper){
			switch (whichBumper){
				case TIGER_LEFT: return tigerLeft;
				case TIGER_RIGHT: return tigerRight;
				case TIGER_BOTTOM: return tigerBottom;
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
	    	Tiger.this.finish();
	    }
		
	}
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        golfView = new TigerView (this.getApplicationContext(),width,height);
        layout.addView(golfView, 0);
        golfView.setScoreUpdator(this);
    }
}

