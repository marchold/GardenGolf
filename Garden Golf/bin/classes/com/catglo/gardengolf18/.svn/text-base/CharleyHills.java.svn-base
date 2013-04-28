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

public class CharleyHills extends GolfActivity {
    public class CharleyHillView extends GolfView {
	
	
		private static final long PUTTER_INTERVAL = 100;
		private Bitmap[] charley;
		private AnimationAction putterAnimation;
		private int putterStage;
		Bumper charleyBumper;
		static final int SIDE_VIEW_BUMPER = 1;
		private static final int LEFT_ZONE_LOW   = 1;
		private static final int LEFT_ZONE_HI    = 2;
		private static final int RIGHT_ZONE_LOW  = 3;
		private static final int RIGHT_ZONE_HI   = 4;
		private static final int TOP_ZONE_LOW    = 5;
		private static final int TOP_ZONE_HI     = 6;
		private static final int BOTTOM_ZONE_LOW = 7;
		private static final int BOTTOM_ZONE_HI  = 8;
		final float HILL_VELOCITY = 0.003f;
		private Velocity downhillIsUp;
		private Velocity downhillIsDown;
		private Velocity downhillIsLeft;
		private Velocity downhillIsRight;
		private VelocityAdjustor up;
		private VelocityAdjustor down;
		private VelocityAdjustor left;
		private VelocityAdjustor right;
		private Zone leftZoneLow;
		private Zone leftZoneHi;
		private Zone rightZoneLow;
		private Zone rightZoneHi;
		private Zone topZoneLow;
		private Zone topZoneHi;
		private Zone bottomZoneLow;
		private Zone bottomZoneHi;
		private Matrix charleyMatrix;
		Zone[] zones = new Zone[9];
		
		public CharleyHillView(Context context, int width, int height) {
			super(context, width, height);
			
			
			setBackgroundResource(R.drawable.charlyhill_background);
			
			charley = new Bitmap[7];
			charley[0] = loadBitmap(R.drawable.charly_frame1);
			charley[2] = loadBitmap(R.drawable.charly_frame2);
			charley[3] = loadBitmap(R.drawable.charly_frame3);
			charley[4] = loadBitmap(R.drawable.charly_frame4);
			charley[5] = loadBitmap(R.drawable.charly_frame5);
			charley[6] = loadBitmap(R.drawable.charly_frame6);	
		
			charleyBumper = new Bumper(new Point[]{new Point(70,362),new Point(93,367),new Point(109,357),new Point(136,353),new Point(130,328),new Point(138,311),new Point(130,278),new Point(138,238),new Point(132,180),new Point(136,182),new Point(129,89),new Point(116,69),new Point(109,38),new Point(118,21),new Point(97,2),new Point(75,6),new Point(70,27),new Point(63,34),new Point(81,69),new Point(89,75),new Point(79,123),new Point(82,130),new Point(65,141),new Point(54,135),new Point(48,146),new Point(20,158),new Point(53,161),new Point(55,167),new Point(86,151),new Point(73,181),new Point(87,188),new Point(79,201),new Point(88,291),new Point(98,307),new Point(95,335),new Point(66,337),new Point(67,349)},SIDE_VIEW_BUMPER);
			charleyMatrix = new Matrix();
			charleyMatrix.postTranslate(175, 109);
			charleyBumper.applyMatrix(charleyMatrix);
			
			downhillIsUp = new Velocity(new Point(1,1),new Point(1,0));
			downhillIsUp.setTotal(HILL_VELOCITY);
			up = new VelocityAdjustor(){public Velocity adjust(Velocity velocity, int timeDelta) {    	
		    	//Decelerate due to friction
		    	float total = velocity.total() + DEFAULT_FRICTION*timeDelta;
				if (total<0)
					total=0;
				velocity.setTotal(total);
		    	return velocity.accelerate(downhillIsUp,timeDelta);
			}};
		
			downhillIsDown = new Velocity(new Point(1,1),new Point(1,2));
			downhillIsDown.setTotal(HILL_VELOCITY);
			down = new VelocityAdjustor(){public Velocity adjust(Velocity velocity, int timeDelta) {    	
		    	//Decelerate due to friction
		    	float total = velocity.total() + DEFAULT_FRICTION*timeDelta;
				if (total<0)
					total=0;
				velocity.setTotal(total);
		    	return velocity.accelerate(downhillIsDown,timeDelta);
			}};
			
			downhillIsLeft = new Velocity(new Point(1,1),new Point(0,1));
			downhillIsLeft.setTotal(HILL_VELOCITY);
			left = new VelocityAdjustor(){public Velocity adjust(Velocity velocity, int timeDelta) {    	
		    	//Decelerate due to friction
		    	float total = velocity.total() + DEFAULT_FRICTION*timeDelta;
				if (total<0)
					total=0;
				velocity.setTotal(total);
		    	return velocity.accelerate(downhillIsLeft,timeDelta);
			}};
			
			downhillIsRight = new Velocity(new Point(1,1),new Point(2,1));
			downhillIsRight.setTotal(HILL_VELOCITY);
			right = new VelocityAdjustor(){public Velocity adjust(Velocity velocity, int timeDelta) {    	
		    	//Decelerate due to friction
		    	float total = velocity.total() + DEFAULT_FRICTION*timeDelta;
				if (total<0)
					total=0;
				velocity.setTotal(total);
		    	return velocity.accelerate(downhillIsRight,timeDelta);
			}};
			
		    zones[1] = leftZoneLow = new Zone(new Point[]{new Point(8,410),new Point(25,392),new Point(25,22),new Point(8,9)},LEFT_ZONE_LOW,left);
		    zones[2] = leftZoneHi  = new Zone(new Point[]{new Point(28,368),new Point(40,351),new Point(40,261),new Point(27,247),new Point(27,357)},LEFT_ZONE_HI,left);
		    zones[3] = rightZoneLow = new Zone(new Point[]{new Point(147,393),new Point(147,24),new Point(165,9),new Point(166,407)},RIGHT_ZONE_LOW,right);
		    zones[4] = rightZoneHi  = new Zone(new Point[]{new Point(139,369),new Point(129,354),new Point(129,263),new Point(140,251),new Point(140,361)},RIGHT_ZONE_HI,right);
		    zones[5] = topZoneLow = new Zone(new Point[]{new Point(19,23),new Point(153,23),new Point(165,9),new Point(6,9)},TOP_ZONE_LOW,up);
		    zones[6] = topZoneHi = new Zone(new Point[]{new Point(28,252),new Point(41,264),new Point(128,264),new Point(138,249),new Point(34,249)},TOP_ZONE_HI,up);
		    zones[7] = bottomZoneLow = new Zone(new Point[]{new Point(10,408),new Point(26,393),new Point(150,393),new Point(167,409),new Point(20,409)},BOTTOM_ZONE_LOW,down);
		    zones[8] = bottomZoneHi = new Zone(new Point[]{new Point(27,368),new Point(39,352),new Point(129,352),new Point(140,370),new Point(37,370)},BOTTOM_ZONE_HI,down);
					
			putterAnimation = new AnimationAction(System.currentTimeMillis()+PUTTER_INTERVAL,new Runnable(){public void run(){synchronized(CharleyHillView.this){
				putterStage++;
				if (putterStage>=charley.length){
					putterStage=0;
				}
				putterAnimation.actionTime=System.currentTimeMillis()+PUTTER_INTERVAL;	
			}}});
			
		}
	
		protected void checkIfBumperMovedOverBall(int whichBumper) {
			// TODO Auto-generated method stub	
		}
		
		protected void drawBackground(Canvas canvas){
			super.drawSurface(canvas);
			canvas.drawBitmap(charley[putterStage], charleyMatrix, null);
		}	
		
		protected Coordinate ballStartPoint(){
			return new Coordinate(220,410);
		}
		protected Coordinate ballHolePoint(){
			return new Coordinate(82,312);
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
		
		
		Bumper getBumper(int whichBumper){
			if (whichBumper>1)
				return null;
			return charleyBumper;
		}
		
		@Override
		Zone getZone(int whichZone){
			if (whichZone>=zones.length)
				return null;
			return zones[whichZone];
		}
		

		@Override
	    void downTheHole(){
	    	super.downTheHole();
	    	Intent data = new Intent(getApplicationContext(),GardenGolf.class);
	    	data.putExtra("Score", numberOfSwings);
	    	data.putExtra("green", greenNumber);
	    	startActivity(data);
	    	CharleyHills.this.finish();
	    }
	}
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    	golfView = new CharleyHillView (this.getApplicationContext(),width,height);
        layout.addView(golfView, 0);
        golfView.setScoreUpdator(this);
        score.setPadding(10, 10, 10, 10);
        LayoutParams lp = new FrameLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
        lp.gravity=Gravity.TOP|Gravity.RIGHT;
        score.setLayoutParams(lp);
    }
}

	
