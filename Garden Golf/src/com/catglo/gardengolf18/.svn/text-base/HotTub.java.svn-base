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
import android.util.Log;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;

public class HotTub extends GolfActivity {
   public class HotTubView extends GolfView {
		
		private int bubblesStage=0;
		
		private Bitmap[] bubbles;
		private Bitmap[] leftFlower;
		private Bitmap[] rightFlower;
		private AnimationAction bubblesAnimation;
		static final int FLOWERS_INTERVAL = 50;
		static final int BUBBLES_INTERVAL = 150;

		private static final long FLICK_INTERVAL = 100;
		private static final long TOSS_INTERVAL = 50;
		
		int leftFlowerStage=0;
		int rightFlowerStage=0;

		private Velocity rampVelocity;
		private AnimationAction leftFlickAnimation;

		private AnimationAction rightFlickAnimation;

		private Polygon leftFlingArea;

		private Polygon rightFlingArea;

		private AnimationAction leftTossAnimation;

		private int leftTossStage;

		private int rightTossStage;

		private AnimationAction rightTossAnimation;
		
		
		public HotTubView(Context context, int width, int height) {
			super(context, width, height);
				
			setBackgroundResource(R.drawable.hottub_background);
			
			bubbles = new Bitmap[5];
			bubbles[0] = loadBitmap(R.drawable.hottub_bubbles1);
			bubbles[1] = loadBitmap(R.drawable.hottub_bubbles2);
			bubbles[2] = loadBitmap(R.drawable.hottub_bubbles3);
			bubbles[3] = loadBitmap(R.drawable.hottub_bubbles4);
			bubbles[4] = loadBitmap(R.drawable.hottub_bubbles5);
				
			leftFlower = new Bitmap[3];
			leftFlower[0] = loadBitmap(R.drawable.hottub_left_flower_1);
			leftFlower[1] = loadBitmap(R.drawable.hottub_left_flower_2);
			leftFlower[2] = loadBitmap(R.drawable.hottub_left_flower_3);
			
			rightFlower = new Bitmap[3];
			rightFlower[0] = loadBitmap(R.drawable.hottub_right_flower_1);
			rightFlower[1] = loadBitmap(R.drawable.hottub_right_flower_2);
			rightFlower[2] = loadBitmap(R.drawable.hottub_right_flower_3);
			
			
			bubblesAnimation = new AnimationAction(System.currentTimeMillis()+BUBBLES_INTERVAL,new Runnable(){public void run(){	
				++bubblesStage;
				if (bubblesStage>=bubbles.length){
					bubblesStage=0;
				}
				bubblesAnimation.actionTime=System.currentTimeMillis()+BUBBLES_INTERVAL;
				postInvalidate();
			}});
			addAction(bubblesAnimation);
			
			leftFlickAnimation = new AnimationAction(System.currentTimeMillis()+FLICK_INTERVAL,new Runnable(){public void run(){	
				++leftFlowerStage;
				if (leftFlowerStage>=leftFlower.length){
					leftFlowerStage=0;
					removeAction(leftFlickAnimation);
				}
				leftFlickAnimation.actionTime=System.currentTimeMillis()+FLICK_INTERVAL;
				postInvalidate();
			}});
			
			leftTossStage=0;
			leftTossAnimation = new AnimationAction(System.currentTimeMillis()+TOSS_INTERVAL,new Runnable(){public void run(){	
				++leftTossStage;
				switch (leftTossStage){
				case 1:
					leftFlowerStage=1;
					ballStart.x = ball.x = 68;
					ballStart.y = ball.y = 257;
					break;
				case 2:
					ballStart.x = ball.x = 51;
					ballStart.y = ball.y = 261;
					break;
				case 3:
					leftFlowerStage=2;
					ballStart.x = ball.x = 49;
					ballStart.y = ball.y = 185;
					break;
				case 4:
					ballStart.x = ball.x = 63;
					ballStart.y = ball.y = 135;
					break;
				case 5:
					leftFlowerStage=0;
					ballStart.x = ball.x = 82;
					ballStart.y = ball.y = 84;
					break;
				case 6:
					ballStart.x = ball.x = 122;
					ballStart.y = ball.y = 63;
					removeAction(leftTossAnimation);
					leftTossStage=0;
					break;
				}
				leftTossAnimation.actionTime=System.currentTimeMillis()+TOSS_INTERVAL;
				postInvalidate();
			}});
			
			rightTossStage=0;
			rightTossAnimation = new AnimationAction(System.currentTimeMillis()+TOSS_INTERVAL,new Runnable(){public void run(){	
				++rightTossStage;
				switch (rightTossStage){
				case 1:
					rightFlowerStage=1;
					ballStart.x = ball.x = 247;
					ballStart.y = ball.y = 257;
					break;
				case 2:
					ballStart.x = ball.x = 270;
					ballStart.y = ball.y = 261;
					break;
				case 3:
					rightFlowerStage=2;
					ballStart.x = ball.x = 272;
					ballStart.y = ball.y = 185;
					break;
				case 4:
					ballStart.x = ball.x = 260;
					ballStart.y = ball.y = 135;
					break;
				case 5:
					rightFlowerStage=0;
					ballStart.x = ball.x = 234;
					ballStart.y = ball.y = 84;
					break;
				case 6:
					ballStart.x = ball.x = 195;
					ballStart.y = ball.y = 53;
					removeAction(rightTossAnimation);
					rightTossStage=0;
					break;
				}
				rightTossAnimation.actionTime=System.currentTimeMillis()+TOSS_INTERVAL;
				postInvalidate();
			}});
			
			
			rightFlickAnimation = new AnimationAction(System.currentTimeMillis()+FLICK_INTERVAL,new Runnable(){public void run(){	
				++rightFlowerStage;
				if (rightFlowerStage>=rightFlower.length){
					rightFlowerStage=0;
					removeAction(rightFlickAnimation);
				}
				rightFlickAnimation.actionTime=System.currentTimeMillis()+FLICK_INTERVAL;
				postInvalidate();
			}});
			//addAction(rightFlickAnimation);
			
			
			rampVelocity = new Velocity(new Point(153,145),new Point(153,202));
			rampVelocity.setTotal(0.005f);
			
			leftFlingArea = new Polygon(new Point[] {new Point(54,310),new Point(68,328),new Point(90,332),new Point(90,285),new Point(61,280)});
			rightFlingArea = new Polygon(new Point[] {new Point(233,330),new Point(252,328),new Point(269,308),new Point(255,269),new Point(219,298)});
		}
		
		protected void checkIfBumperMovedOverBall(int whichBumper) {
			// TODO Auto-generated method stub
			
		}
	
		protected void drawBackground(Canvas canvas){
			super.drawBackground(canvas);
			canvas.drawBitmap(bubbles[bubblesStage], 15,48, null);
			
			canvas.drawBitmap(leftFlower[leftFlowerStage], 21,172, null);
			canvas.drawBitmap(rightFlower[rightFlowerStage], 233,172, null);
			
		}
		
		protected void drawSurface(Canvas canvas){
			super.drawSurface(canvas);
	
		}	
		
		protected Coordinate ballStartPoint(){
			return new Coordinate(165,440);
		}
		final Coordinate holePoint = new Coordinate(158,50);
		protected Coordinate ballHolePoint(){
			return holePoint; 
		}
		final Polygon green = new Polygon(new Point[] {
			new Point(2,478),
            new Point(2,2),
            new Point(318,2),
            new Point(318,478)
		});
		protected Polygon getGreenPolygon(){
			
			return green;
		}
		
		@Override
		protected void onObjectBounce(int id, float force) {
			switch (id){
			case LEFT_FLOWER_FLING:
				if (leftFlingArea.contains(ball)){
					addAction(leftTossAnimation);
				} else {
					addAction(leftFlickAnimation);
				}
				break;
			case RIGHT_FLOWER_FLING:
				if (rightFlingArea.contains(ball)){
					addAction(rightTossAnimation);
				}else{
					addAction(rightFlickAnimation);
				}
				break;	
			}
		}
		
		static final int BACKBOARD=1;
		static final int LEFT_FLOWER = 2;
		static final int RIGHT_FLOWER = 3;
		static final int LEFT_FLOWER_FLING = 4;
		static final int RIGHT_FLOWER_FLING = 5;
		
		final Bumper[] bumpers = {null,
			new Bumper(new Point[]{
				new Point(43,224),
				new Point(26,105),
				new Point(48,89),
				new Point(70,76),
				new Point(94,74),
				new Point(94,17),
				new Point(141,9),
				new Point(226,20),
				new Point(224,72),
				new Point(276,93),
				new Point(285,108),
				new Point(275,211),
				new Point(267,218),
				new Point(277,121),
				new Point(269,98),
				new Point(216,76),
				new Point(219,26),
				new Point(157,17),
				new Point(103,26),
				new Point(102,79),
				new Point(62,87),
				new Point(31,111)		
			},BACKBOARD),//Left Flower
			new Bumper(new Point[] {
				new Point(51,310),
				new Point(72,227),
				new Point(42,210),
				new Point(26,235),
				new Point(39,251),
				new Point(37,278),
				new Point(20,278)
			},LEFT_FLOWER),
			//Right Flower
			new Bumper(new Point[] {
				new Point(268,309),
				new Point(258,249),
				new Point(246,237),
				new Point(251,221),
				new Point(276,204),
				new Point(294,224),
				new Point(284,249),
				new Point(279,265),
				new Point(281,282),
				new Point(303,275)
			},RIGHT_FLOWER),
			new Bumper(new Point[] {
				new Point(50,310),
				new Point(71,332),
				new Point(88,330),
				new Point(65,318)	
			},LEFT_FLOWER_FLING),
			//Right Flower
			new Bumper(new Point[] {
				new Point(232,330),
				new Point(249,330),
				new Point(269,307),
				new Point(243,326)
			},RIGHT_FLOWER_FLING),
		};
		Bumper getBumper(int whichBumper){
			
			if (whichBumper < bumpers.length){
				return bumpers[whichBumper];
			} else {
				return null;
			}
		}
		
		static final int HOTTUB_BASIN = 1;
		static final int BUBBLES = 2;
		Random random = new Random();
		final Zone[] zones = {null,
			//Hottub Basin
			new Zone(new Point[] {
					new Point(25,102),
					new Point(39,230),
					new Point(184,244),
					new Point(242,232),
					new Point(274,209),
					new Point(285,101),
					new Point(270,133),
					new Point(223,141),
					new Point(149,144),
					new Point(83,137),
					new Point(44,122)
			},HOTTUB_BASIN,new VelocityAdjustor(){public Velocity adjust(Velocity velocity, int timeDelta) {
				//Decelerate due to friction
		    	float total = velocity.total() + DEFAULT_FRICTION*timeDelta;
				if (total<0)
					total=0;
				
				if (total > 0.5f){
					total=0.5f;
				}
				velocity.setTotal(total);
				//Log.i("GOLF","velocity = "+velocity.total());
				
		    	return velocity.accelerate(rampVelocity,timeDelta);
			}}, true),
			//Bubbles
			new Zone(new Point[] {
				new Point(30,121),
				new Point(73,145),
				new Point(128,152),
				new Point(190,150),
				new Point(244,144),
				new Point(279,127),
				new Point(288,111),
				new Point(282,87),
				new Point(252,73),
				new Point(215,81),
				new Point(104,84),
				new Point(92,70),
				new Point(57,74),
				new Point(31,96)
			    },BUBBLES,new VelocityAdjustor(){public Velocity adjust(Velocity velocity, int timeDelta) {
			    	float total = velocity.total() + (DEFAULT_FRICTION*2)*timeDelta;
					if (total<0)
						total=0;
					velocity.setTotal(total);
					ball.x += (random.nextInt(10)-5);
					ball.y += (random.nextInt(12)-5);
			    	return velocity;
			}}, true),
		};
		@Override
		Zone getZone(int whichZone){
			
			if (whichZone<zones.length){
				return zones[whichZone];
			}else{
				return null;
			}
		}
		
		@Override
	    void downTheHole(){
	    	super.downTheHole();
	    	Intent data = new Intent(getApplicationContext(),GardenGolf.class);
	    	data.putExtra("Score", numberOfSwings);
	    	data.putExtra("green", greenNumber);
	    	startActivity(data);
	    	HotTub.this.finish();
	    }
		
	}
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        golfView = new HotTubView (this.getApplicationContext(),width,height);
        layout.addView(golfView, 0);
        golfView.setScoreUpdator(this);
        score.setPadding(10, 10, 10, 10);
        LayoutParams lp = new FrameLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
        lp.gravity=Gravity.BOTTOM|Gravity.RIGHT;
        score.setLayoutParams(lp);
    }
    
}
