package com.catglo.gardengolf18;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;

public class Bicycle extends GolfActivity {
  
	public class BicycleView extends GolfView {
		
		private Bitmap bycycle;
		private AnimationAction cloudAnimation;
		private int topCloudPosition=300;
		private int bottomCloudPosition=100;
		private int rainbowStage=0;
		private int rainbowStep=0;
		
		private Bitmap wheel;
		private float wheelDegrees=0;
		private Bitmap topCloud;
		private Bitmap bottomCloud;
		private Bitmap[] rainbow;
		
		private AnimationAction rainAnimation;
		static final int CLOUD_INTERVAL = 100;
		static final int RAIN_INTERVAL = 150;
		
		Vector push = new Vector(1,1);
		private Polygon circle;
		private Point circleCenter;
		protected boolean inCircle=false;
		private Zone lowCloudBumper;
		private Zone highCloudBumper;
		private Matrix lowCloudMatrix;
		private Matrix highCloudMatrix;
		private Matrix lowCloudMatrix2;
		private Matrix highCloudMatrix2;
		private Zone lowCloudBumper2;
		private Zone highCloudBumper2;
		private VelocityAdjustor lowCloudAdjustor;
		private VelocityAdjustor highCloudAdjustor;
		int lastLowPos=0;
		int lastHiPos=0;
		public BicycleView(Context context, int width, int height) {
			super(context, width, height);
			
		
			lowCloudAdjustor =  new VelocityAdjustor(){public Velocity adjust(Velocity velocity, int timeDelta) {synchronized(Bicycle.this){
		    	
		    	//Decelerate due to friction
		    	float total = velocity.total() + (DEFAULT_FRICTION*2)*timeDelta;
				if (total<0)
					total=0;
				velocity.setTotal(total);
		    	
				ballStart.x=ball.x-=0.5f;
				if (ball.x < 5){
					ballStart.x=ball.x=5;
				}
			//	lastLowPos = 
		    	return velocity;//.push(rampVelocity,timeDelta);
		    }}};
			
			highCloudAdjustor =  new VelocityAdjustor(){public Velocity adjust(Velocity velocity, int timeDelta) {synchronized(Bicycle.this){
		    	
		    	//Decelerate due to friction
		    	float total = velocity.total() + (DEFAULT_FRICTION*2)*timeDelta;
				if (total<0)
					total=0;
				velocity.setTotal(total);
		    	
				ballStart.x=ball.x+=0.5f;
				if (ball.x>315){
					ballStart.x=ball.x=315;
				}
		    	
		    	return velocity;//.push(rampVelocity,timeDelta);
		    }}};
			
			
			lowCloudBumper = new Zone(new Point[]{new Point(0,20),new Point(17,33),new Point(11,48),new Point(17,57),new Point(26,60),new Point(39,62),new Point(46,59),new Point(52,52),new Point(68,61),new Point(83,62),new Point(92,47),new Point(105,44),new Point(105,29),new Point(93,25),new Point(83,25),new Point(83,9),new Point(72,3),new Point(66,10),new Point(47,0),new Point(32,8),new Point(14,2),new Point(1,6)},LOW_CLOUD,lowCloudAdjustor,true);
			highCloudBumper = new Zone(new Point[]{new Point(2,17),new Point(12,21),new Point(11,39),new Point(31,44),new Point(60,46),new Point(78,32),new Point(67,15),new Point(57,2),new Point(33,3),new Point(3,2)},HIGH_CLOUD,highCloudAdjustor,true);
			lowCloudBumper2 = new Zone(new Point[]{new Point(0,20),new Point(17,33),new Point(11,48),new Point(17,57),new Point(26,60),new Point(39,62),new Point(46,59),new Point(52,52),new Point(68,61),new Point(83,62),new Point(92,47),new Point(105,44),new Point(105,29),new Point(93,25),new Point(83,25),new Point(83,9),new Point(72,3),new Point(66,10),new Point(47,0),new Point(32,8),new Point(14,2),new Point(1,6)},LOW_CLOUD,lowCloudAdjustor,true);
			highCloudBumper2 = new Zone(new Point[]{new Point(2,17),new Point(12,21),new Point(11,39),new Point(31,44),new Point(60,46),new Point(78,32),new Point(67,15),new Point(57,2),new Point(33,3),new Point(3,2)},HIGH_CLOUD,highCloudAdjustor,true);
			
			
			lowCloudMatrix = new Matrix();
			lowCloudMatrix.postTranslate(bottomCloudPosition, 400);
			lowCloudMatrix2 = new Matrix();
			lowCloudMatrix2.postTranslate(bottomCloudPosition+300, 400);
				
			highCloudMatrix = new Matrix();
			highCloudMatrix.postTranslate(topCloudPosition, 8);
			highCloudMatrix2 = new Matrix();
			highCloudMatrix2.postTranslate(topCloudPosition-300, 8);
			
			lowCloudBumper.applyMatrix(lowCloudMatrix);
			lowCloudBumper2.applyMatrix(lowCloudMatrix2);
			highCloudBumper.applyMatrix(highCloudMatrix);
			highCloudBumper2.applyMatrix(highCloudMatrix2);
			
			setBackgroundResource(R.drawable.bicycle_background);
			
			wheel = loadBitmap(R.drawable.bicycle_wheel);
			bycycle = loadBitmap(R.drawable.bicycle);
		    topCloud = loadBitmap(R.drawable.bibycle_top_cloud);
			bottomCloud = loadBitmap(R.drawable.bibycle_bottom_cloud);
			rainbow=new Bitmap[8];
			rainbow[0]=loadBitmap(R.drawable.bicycle_rainbow_1);
			rainbow[1]=loadBitmap(R.drawable.bicycle_rainbow_2_red);
			rainbow[2]=loadBitmap(R.drawable.bicycle_rainbow_3_orange);
			rainbow[3]=loadBitmap(R.drawable.bicycle_rainbow_4_yellow);
			rainbow[4]=loadBitmap(R.drawable.bicycle_rainbow_5_green);
			rainbow[5]=loadBitmap(R.drawable.bicycle_rainbow_6_blue);
			rainbow[6]=loadBitmap(R.drawable.bicycle_rainbow_7_indigo);
			rainbow[7]=loadBitmap(R.drawable.bicycle_rainbow_8_purple);
			
			circle = new Polygon(new Point[]{new Point(180,249),new Point(204,244),new Point(229,229),new Point(249,209),new Point(260,185),new Point(263,156),new Point(259,129),new Point(244,104),new Point(222,86),new Point(192,74),new Point(169,75),new Point(141,80),new Point(122,93),new Point(105,108),new Point(92,129),new Point(86,155),new Point(86,177),new Point(94,200),new Point(107,220),new Point(124,237),new Point(148,246),new Point(167,248)});
			
			cloudAnimation = new AnimationAction(System.currentTimeMillis()+CLOUD_INTERVAL,new Runnable(){public void run(){synchronized(BicycleView.this){
				topCloudPosition++;
				if (topCloudPosition>=320){
					topCloudPosition-=300;
				}
				
				bottomCloudPosition--;
				if (bottomCloudPosition<0-bottomCloud.getWidth()){
					bottomCloudPosition+=300;
				}
				lowCloudMatrix.reset();
				lowCloudMatrix.postTranslate(bottomCloudPosition, 400);
				lowCloudMatrix2.reset();
				lowCloudMatrix2.postTranslate(bottomCloudPosition+300, 400);
					
				highCloudMatrix.reset();
				highCloudMatrix.postTranslate(topCloudPosition, 8);
				highCloudMatrix2.reset();
				highCloudMatrix2.postTranslate(topCloudPosition-300, 8);

				lowCloudBumper.applyMatrix(lowCloudMatrix);
				lowCloudBumper2.applyMatrix(lowCloudMatrix2);
				highCloudBumper.applyMatrix(highCloudMatrix);
				highCloudBumper2.applyMatrix(highCloudMatrix2);
				
				
				wheelDegrees-=4;
				if (inCircle){
					
					Velocity v = new Velocity(hole,ball);
					Velocity v2 = new Velocity(hole,ball);
					v2.setAngle(v2.getAngle()-4); 
					
					Coordinate a = new Coordinate(hole.x+v.x,hole.y+v.y);
					Coordinate b = new Coordinate(hole.x+v2.x,hole.y+v2.y);
					float distance = a.distanceFrom(b);
					v.setAngle(v.getAngle()+90+180);
					v.setTotal(distance);
					ball.x+=v.x;
					ball.y+=v.y;//.slideUnder(v, delta, 1f);
					ballStart.x = ball.x;
					ballStart.y = ball.y;
					
					distance /= 40;
					
					v.setTotal(distance);//0.07f);//len*0.001f);//
					v.slideUnder(v, 1, 0.3f);
					
				}
				
				
				rainbowStep+=4;
				if (rainbowStep>44){
					rainbowStep=0;
					
					if (rainbowStage<rainbow.length-1)
						rainbowStage++;
					
				}
				
		 		cloudAnimation.actionTime=System.currentTimeMillis()+CLOUD_INTERVAL;
				postInvalidate();
			}}});
			addAction(cloudAnimation);
			
			rainAnimation = new AnimationAction(System.currentTimeMillis()+RAIN_INTERVAL,new Runnable(){public void run(){synchronized(BicycleView.this){
				
			
				rainAnimation.actionTime=System.currentTimeMillis()+RAIN_INTERVAL;
				postInvalidate();
			}}});
			//addAction(rainAnimation);
		}
		
	
	
		protected void drawBackground(Canvas canvas){
			super.drawBackground(canvas);
	
			canvas.drawBitmap(bycycle, 31,41, null);
			Matrix wheelMatrix = new Matrix();
			wheelMatrix.preTranslate(-(wheel.getWidth()/2),-(wheel.getHeight()/2));
			wheelMatrix.postRotate(wheelDegrees);
			wheelMatrix.postTranslate((wheel.getWidth()/2),(wheel.getHeight()/2));
			wheelMatrix.postTranslate(87, 76);
			canvas.drawBitmap(wheel, wheelMatrix, null);
			canvas.drawBitmap(rainbow[rainbowStage], 6,209, null);
			
			canvas.drawBitmap(topCloud, highCloudMatrix, null);
			canvas.drawBitmap(topCloud, highCloudMatrix2, null);
			canvas.drawBitmap(bottomCloud, lowCloudMatrix, null);
			canvas.drawBitmap(bottomCloud, lowCloudMatrix2, null);
			
			Paint p = new Paint();
			p.setColor(Color.BLUE);
			p.setStrokeWidth(5);
			canvas.drawLine(l1[0],l1[1],l1[2],l1[3], p);
			p.setColor(Color.RED);
			canvas.drawLine(l2[0],l2[1],l2[2],l2[3], p);
			
		}
		
		protected void drawSurface(Canvas canvas){
			super.drawSurface(canvas);
	
		}	
		
		protected Coordinate ballStartPoint(){
			return new Coordinate(40,440);
		}
		Coordinate hole = new Coordinate(175,161);
		protected Coordinate ballHolePoint(){
			return hole;
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
		
		
		static final int BICYCLE=1;
		static final int LOW_CLOUD=1;
		static final int HIGH_CLOUD=2;
		static final int LOW_CLOUD2=3;
		static final int HIGH_CLOUD2=4;
		
		Bumper getBumper(int whichBumper){
			switch (whichBumper){
			case BICYCLE: return new Bumper(new Point[]{new Point(64,192),new Point(52,192),new Point(42,198),new Point(33,210),new Point(34,229),new Point(42,239),new Point(61,243),new Point(76,237),new Point(85,219),new Point(82,204),new Point(75,197),new Point(69,193),new Point(72,162),new Point(78,134),new Point(89,115),new Point(104,97),new Point(123,83),new Point(141,74),new Point(166,67),new Point(165,75),new Point(172,73),new Point(176,62),new Point(173,52),new Point(182,50),new Point(186,59),new Point(186,44),new Point(172,44),new Point(167,40),new Point(166,54),new Point(119,64),new Point(112,74),new Point(108,82),new Point(93,98),new Point(78,119),new Point(70,144),new Point(65,180)},BICYCLE);
			
			}
			return null;
		}
		
		Zone getZone(int whichZone){
			switch (whichZone){
			case LOW_CLOUD: return lowCloudBumper;
			case HIGH_CLOUD: return highCloudBumper;
			case LOW_CLOUD2: return lowCloudBumper2;
			case HIGH_CLOUD2: return highCloudBumper2;
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
	    	Bicycle.this.finish();
	    }
		float[] l1 = new float[4];
		float[] l2 = new float[4];
		
		float lastWheelDegrees=0;
		@Override
		synchronized protected void onBallMovementProcessed(int delta) {
			Vector vec = new Vector(hole,ball);
			if (vec.total() < 90)
			{
				inCircle=true;
				lastWheelDegrees=wheelDegrees;
			} else{
				inCircle=false;
			}
		}
	}
	
	  @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        golfView = new BicycleView (this.getApplicationContext(),width,height);
        layout.addView(golfView, 0);
        golfView.setScoreUpdator(this);
        score.setPadding(10, 10, 10, 10);
        LayoutParams lp = new FrameLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
        lp.gravity=Gravity.BOTTOM|Gravity.RIGHT;
        score.setLayoutParams(lp);
        fontColor = 000000;
    }
}

