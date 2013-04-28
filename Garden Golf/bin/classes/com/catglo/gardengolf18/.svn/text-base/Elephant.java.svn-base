package com.catglo.gardengolf18;

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

public class Elephant extends GolfActivity {
    public class ElephantView extends GolfView {
		
		private Bitmap[] elephant;
		private Bitmap[] flower;
		private AnimationAction flowerAnimation;
		private AnimationAction elephantAnimation;
		private int flowerStage=0;
		private int elephantStage=0;
		private Bitmap shot;
		private Random r;
		private Velocity rampVelocity;
		private Zone easilRampZone;
		static final int FLOWER_INTERVAL = 600;
		static final int FLOWER_WAIT = 3000;
		
		static final int ELEPHANT_INTERVAL = 350;
		
		
		public ElephantView(Context context, int width, int height) {
			super(context, width, height);
			setBackgroundResource(R.drawable.elephant_background);
			//removeAction(tossing);
			drawBall=false;
			
			elephant = new Bitmap[13];
			elephant[0]  = loadBitmap(R.drawable.elephant_revsn_1);
			elephant[1]  = loadBitmap(R.drawable.elephant_revsn_2);
			elephant[2]  = loadBitmap(R.drawable.elephant_revsn_3);
			elephant[3]  = loadBitmap(R.drawable.elephant_revsn_4);
			elephant[4]  = loadBitmap(R.drawable.elephant_revsn_5);
			elephant[5]  = loadBitmap(R.drawable.elephant_revsn_6);
			elephant[6]  = loadBitmap(R.drawable.elephant_revsn_7);
			elephant[7]  = loadBitmap(R.drawable.elephant_revsn_8);
			elephant[8]  = loadBitmap(R.drawable.elephant_revsn_9);
			elephant[9]  = loadBitmap(R.drawable.elephant_revsn_10);
			elephant[10] = loadBitmap(R.drawable.elephant_revsn_11);
			elephant[11] = loadBitmap(R.drawable.elephant_revsn_12);
			elephant[12] = loadBitmap(R.drawable.elephant_revsn_13);
			
			flower = new Bitmap[4];
			flower[0] = loadBitmap(R.drawable.elephant_flower_swaying_1);
			flower[1] = loadBitmap(R.drawable.elephant_flower_swaying_2);
			flower[2] = loadBitmap(R.drawable.elephant_flower_swaying_3);
			flower[3] = loadBitmap(R.drawable.elephant_flower_swaying_4);
			
			shot = loadBitmap(R.drawable.elephant_shot);
			
			r=new Random();
			
			flowerAnimation = new AnimationAction(System.currentTimeMillis()+FLOWER_INTERVAL,new Runnable(){public void run(){synchronized(ElephantView.this){
				flowerStage++;
				flowerAnimation.actionTime=System.currentTimeMillis()+FLOWER_INTERVAL;
				if (flowerStage==flower.length){
					flowerAnimation.actionTime=System.currentTimeMillis()+r.nextInt(FLOWER_WAIT);
					flowerStage=0;
				}
				postInvalidate();
			}}});
			//
			
			elephantAnimation = new AnimationAction(System.currentTimeMillis()+ELEPHANT_INTERVAL,new Runnable(){public void run(){synchronized(ElephantView.this){
				elephantStage++;
				if (elephantStage==elephant.length){
					removeAction(elephantAnimation);
					//addAction(tossing);
					drawBall=true;
					//addAction(flowerAnimation);
				}
				postInvalidate();
				elephantAnimation.actionTime=System.currentTimeMillis()+ELEPHANT_INTERVAL;	
			}}});
			addAction(elephantAnimation);
			
			rampVelocity = new Velocity(new Point(307,37),new Point(205,202));
			rampVelocity.setTotal(0.001f);
			
			easilRampZone = new Zone(new Point[]{		
				new Point(266,12),
				new Point(155,176),
				new Point(199,213),
				new Point(309,40)  
				/*	new Point(50,100),
					new Point(200,100),
					new Point(200,200),
					new Point(50,200)*/
			    },EASIL_RAMP, new VelocityAdjustor(){public Velocity adjust(Velocity velocity, int timeDelta) {synchronized(ElephantView.this){
			    	//Decelerate due to friction
			    	float total = velocity.total() + DEFAULT_FRICTION*timeDelta;
					if (total<0)
						total=0;
					velocity.setTotal(total);
			    	
			    	return velocity.accelerate(rampVelocity,timeDelta);
			}}},true);
			
		}
	
	
		synchronized protected void drawBackground(Canvas canvas){
			super.drawBackground(canvas);
			if (elephantStage < elephant.length){
				canvas.drawBitmap(elephant[elephantStage], 6,6, null);
			} else {
				canvas.drawBitmap(shot, 159,8, null);
		//		canvas.drawBitmap(flower[flowerStage], 36,50, null);
		//		canvas.drawBitmap(flower[flowerStage], 16,363, null);
		//		canvas.drawBitmap(flower[flowerStage], 126,299, null);
		//		canvas.drawBitmap(flower[flowerStage], 218,218, null);
			}
		}
		
		protected Coordinate ballStartPoint(){
			return new Coordinate(40,440);
		}
		protected Coordinate ballHolePoint(){
			return new Coordinate(255,65);
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

	
		static final int RIGHT_SIDE_OF_EASIL=1;
		static final int LEFT_SIDE_OF_EASIL=2;
		static final int INSIDE_OF_EASIL=3;

		
		Bumper getBumper(int whichBumper){
			switch (whichBumper){
			case RIGHT_SIDE_OF_EASIL: 
				return new Bumper(new Point[]{	
					new Point(202,200),
					new Point(312,37),
					new Point(319,133),
					new Point(287,91),
				},RIGHT_SIDE_OF_EASIL);
			case LEFT_SIDE_OF_EASIL:
				return new Bumper(new Point[]{
					new Point(157,164),
					new Point(264,5),
					new Point(270,7),
					new Point(163,167),
				},LEFT_SIDE_OF_EASIL);
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
	    	Elephant.this.finish();
	    }
		
		static final int EASIL_RAMP = 1;
		
		@Override
		Zone getZone(int whichZone){
			switch (whichZone){
			case EASIL_RAMP: 
				return easilRampZone;
			}
			return null;
		}

	} 
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        golfView = new ElephantView (this.getApplicationContext(),width,height);
        layout.addView(golfView, 0);
        golfView.setScoreUpdator(this);
        score.setPadding(10, 10, 10, 10);
        LayoutParams lp = new FrameLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
        lp.gravity=Gravity.BOTTOM|Gravity.RIGHT;
        score.setLayoutParams(lp);
    }
    
    
}

