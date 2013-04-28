package com.catglo.gardengolf18;


import java.util.Iterator;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.PointF;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;

public class Aerheart extends GolfActivity {
    public class AerheartView extends GolfView {
		
		private int propellerStage=0;
		
		private Bitmap[] propellerForward;
		private Bitmap[] propellerBackward;
		private Bitmap[] propellerSkipBeat;
		private Bitmap ameliaAndFlower;
		private AnimationAction propellerAnimation;
		static final int FLOWERS_INTERVAL = 50;
		static final int PROPELLER_INTERVAL = 50;
		int leftFlowerStage=0;
		int rightFlowerStage=0;

		private Matrix propellerMatrix;

		private int propellerStep;
		
		
		public AerheartView(Context context, int width, int height) {
			super(context, width, height);
			
			setBackgroundResource(R.drawable.aerhart_background);
			
			propellerForward = new Bitmap[8];
			propellerForward[0] = loadBitmap(R.drawable.aerhart_forward_1);
			propellerForward[1] = loadBitmap(R.drawable.aerhart_forward_2);
			propellerForward[2] = loadBitmap(R.drawable.aerhart_forward_3);
			propellerForward[3] = loadBitmap(R.drawable.aerhart_forward_4);
			propellerForward[0] = loadBitmap(R.drawable.aerhart_forward_5);
			propellerForward[4] = loadBitmap(R.drawable.aerhart_forward_5);
			propellerForward[5] = loadBitmap(R.drawable.aerhart_forward_6);
			propellerForward[6] = loadBitmap(R.drawable.aerhart_forward_7);
			propellerForward[7] = loadBitmap(R.drawable.aerhart_forward_8);
			
			propellerSkipBeat = new Bitmap[4];
			propellerSkipBeat[0] = loadBitmap(R.drawable.aerhart_skipbeat_1);
			propellerSkipBeat[1] = loadBitmap(R.drawable.aerhart_skipbeat_2);
			propellerSkipBeat[2] = loadBitmap(R.drawable.aerhart_skipbeat_3);
			propellerSkipBeat[3] = loadBitmap(R.drawable.aerhart_skipbeat_4);
			
			propellerBackward = new Bitmap[8];
			propellerBackward[0] = loadBitmap(R.drawable.aerhart_backward_1);
			propellerBackward[1] = loadBitmap(R.drawable.aerhart_backward_2);
			propellerBackward[2] = loadBitmap(R.drawable.aerhart_backward_3);
			propellerBackward[3] = loadBitmap(R.drawable.aerhart_backward_4);
			propellerBackward[4] = loadBitmap(R.drawable.aerhart_backward_5);
			propellerBackward[5] = loadBitmap(R.drawable.aerhart_backward_6);
			propellerBackward[6] = loadBitmap(R.drawable.aerhart_backward_7);
			propellerBackward[7] = loadBitmap(R.drawable.aerhart_backward_8);
			
			ameliaAndFlower = loadBitmap(R.drawable.aerhart_amelia_flower);
		
			propellerStep=0;
			final int ANGLE_STEP = (360/40);
			propellerAnimation = new AnimationAction(System.currentTimeMillis()+PROPELLER_INTERVAL,new Runnable(){public void run(){	
				++propellerStep;
				if (propellerStep%5==0){
					++propellerStage;
					if (propellerStage>=propellerForward.length){
						propellerStage=0;
					}
				}
				
				propellerMatrix.reset();
				propellerMatrix.postTranslate(-153, -151);
				propellerMatrix.postRotate(propellerStep * ANGLE_STEP);
				propellerMatrix.postTranslate(153+9, 151+13);
				lowBlade.applyMatrix(propellerMatrix);
				hiBlade.applyMatrix(propellerMatrix);
				checkIfBumperMovedOverBall(LOW_BLADE);
				checkIfBumperMovedOverBall(HI_BLADE);
				
				propellerAnimation.actionTime=System.currentTimeMillis()+PROPELLER_INTERVAL;
				postInvalidate();
			}});
			addAction(propellerAnimation);
			
			propellerMatrix = new Matrix();
			propellerMatrix.postTranslate(9, 13);
		}
		
		protected void checkIfBumperMovedOverBall(int whichBumper) {
			Bumper b = getBumper(whichBumper);
			final float pow = 0.1f;
			if (b.contains(ball)){	
				
				//Move the ball away from the center. This simulates the outward pull
				//from centrifugal force
				Coordinate hole = ballHolePoint();
				Vector v = new Vector(hole,ball);
				float distance = v.total();
				Log.i("GOLF","BALL theta "+v.getTheta());
	    		
	    		//This moves the ball out of the way of the propeller
	    		//a diamond pattern simulates the circular motion
	    		final int MID_POINT = 163;
	    		Log.i("GOLF","BALL "+ball.x+","+ball.y);
	    		distance++;

	    		Vector vec = new Vector(hole,ball);
	    		vec.setAngle(vec.getAngle()+90f);
	    		vec.setTotal(1);
	    		PointF ballF = ball;//PointF((float)ball.x,(float)ball.y);
	    		Log.i("GOLF","BALL vec = "+vec.x+" , "+vec.y);
	    		int cnt=0;
	    		while (b.contains(ballF)){
	    			ballF.x += vec.x;
	    			ballF.y += vec.y;  	
	    			Log.i("GOLF","BALL new value = "+ballF.x+" , "+ballF.y);
	    			if (cnt++ > 100)
	    				throw new IllegalStateException();
				}	
	    	
				ballStart.x = ball.x = (int) ballF.x;
				ballStart.y = ball.y = (int) ballF.y;
				Log.i("GOLF","BALL +++ "+ball.x+","+ball.y);
							
				//Give the ball a push
				vec.setTotal(0.1f);
				ballVelocityAdd(vec.x,vec.y);	
				
				onBumperMovedBall(whichBumper);
			}
		}
	
	
		protected void drawBackground(Canvas canvas){
			super.drawBackground(canvas);
			canvas.drawBitmap(propellerForward[propellerStage], propellerMatrix, null);
			canvas.drawBitmap(ameliaAndFlower, 130,145, null);	
		}
		
		protected void drawSurface(Canvas canvas){
			super.drawSurface(canvas);
		}	
		
		protected Coordinate ballStartPoint(){
			return new Coordinate(40,440);
		}
		protected Coordinate ballHolePoint(){
			return new Coordinate(162,163);
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
		
		static final int AMELIA=1;
		static final int TICK1=2;
		static final int TICK2=3;
		static final int TICK3=4;
		static final int TICK4=5;
		static final int LOW_BLADE=6;
		static final int HI_BLADE=7;
		
		Bumper lowBlade = new Bumper(new Point[]{
			new Point(146,183),
			new Point(142,308),
			new Point(163,281),
			new Point(168,249),
			new Point(160,181)		
		},LOW_BLADE);
		
	    Bumper hiBlade = new Bumper(new Point[]{
	    	new Point(154,124),
	    	new Point(161,0),
	    	new Point(137,12),
	    	new Point(131,35),
	    	new Point(142,128)
		},HI_BLADE);
		
		protected void onObjectBounce(int id, float force) {
			
		}
		final Bumper[] bumpers = {null,
			new Bumper(new Point[]{
				new Point(153,369),
				new Point(164,409),
				new Point(248,411),
				new Point(252,439),
				new Point(259,414),
				new Point(274,414),
				new Point(287,388),
				new Point(295,366),
				new Point(277,294),
				new Point(273,225),
				new Point(253,214),
				new Point(232,210),
				new Point(236,175),
				new Point(228,160),
				new Point(214,144),
				new Point(190,147),
				new Point(175,159),
				new Point(178,175),
				new Point(169,184),
				new Point(186,210),
				new Point(184,220),
				new Point(167,226),
				new Point(162,289),
				new Point(126,409),
				new Point(151,426)
			},AMELIA),
			new Bumper(new Point[]{
				new Point(25,325),
				new Point(32,341),
				new Point(42,348),
				new Point(30,347),
				new Point(23,335)
			},TICK1),
			new Bumper(new Point[]{
				new Point(120,428),
				new Point(105,439),
				new Point(77,443),
				new Point(99,439),
				new Point(118,425)		
			},TICK2),
			new Bumper(new Point[]{
				new Point(268,28),
				new Point(282,32),
				new Point(291,42),
				new Point(294,52),
				new Point(295,60),
				new Point(297,47),
				new Point(290,36),
				new Point(275,27)	
			},TICK3),
			new Bumper(new Point[]{
				new Point(308,47),
				new Point(307,32),
				new Point(295,23),
				new Point(306,40)	
			},TICK4),
			lowBlade,
			hiBlade
		};
		Bumper getBumper(int whichBumper){
			if (whichBumper >= bumpers.length)
				return null;
			return bumpers[whichBumper];
		}
		
		
		@Override
	    void downTheHole(){
	    	super.downTheHole();
	    	Intent data = new Intent(getApplicationContext(),GardenGolf.class);
	    	data.putExtra("Score", numberOfSwings);
	    	data.putExtra("green", greenNumber);
	    	startActivity(data);
	    	Aerheart.this.finish();
	    }
	}
	
	 @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        golfView = new AerheartView (this.getApplicationContext(),width,height);
        layout.addView(golfView, 0);
        golfView.setScoreUpdator(this);
        score.setPadding(10, 10, 10, 10);
        LayoutParams lp = new FrameLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
        lp.gravity=Gravity.TOP|Gravity.LEFT;
        score.setLayoutParams(lp);
    }
}

