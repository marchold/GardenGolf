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
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;

public class GingerAndFred extends GolfActivity {
   public class GingerAndFredView extends GolfView {
		
		private Bitmap people;
		private AnimationAction peopleAnimation;
		private int rainStage=0;
		private int rainStage1=3;
		private int rainStage2=6;
		private int peoplePosition=0;
		private int peopleDirection=1;
		private int holeCloudPosition=320-140;
		private int holeCloudDirection=-1;
		
		private Bitmap[] rain;
		private Bitmap holeCloud;
		private Bitmap lightning;
		boolean showLightning=false;
		private AnimationAction rainAnimation;
		private Zone lowCloudZone;
		private Matrix peopleMatrix;
		private Zone rainZone;
		private Velocity rainVelocity;
		private Zone hiCloudZone;
		private Matrix hiCloudMatrix;
		static final int PEOPLE_INTERVAL = 200;
		static final int RAIN_INTERVAL = 150;
		
		private static final int LOW_CLOUD = 1;
		private static final int RAIN_ZONE = 2;
		private static final int HI_CLOUD = 3;
			
		
		public GingerAndFredView(Context context, int width, int height) {
			super(context, width, height);
			
			setBackgroundResource(R.drawable.ginger_and_fred_background);
			//showDebug=true;
			
			rain = new Bitmap[13];
			rain[0] = loadBitmap(R.drawable.rain1);
			rain[1] = loadBitmap(R.drawable.rain2);
			rain[2] = loadBitmap(R.drawable.rain3);
			rain[3] = loadBitmap(R.drawable.rain4);
			rain[4] = loadBitmap(R.drawable.rain5);
			rain[5] = loadBitmap(R.drawable.rain6);
			rain[6] = loadBitmap(R.drawable.rain7);
			rain[7] = loadBitmap(R.drawable.rain8);
			rain[8] = loadBitmap(R.drawable.rain9);
			rain[9] = loadBitmap(R.drawable.rain10);
			rain[10] = loadBitmap(R.drawable.rain11);
			rain[11] = loadBitmap(R.drawable.rain12);
			rain[12] = loadBitmap(R.drawable.rain13);
				
			people = loadBitmap(R.drawable.ginger_and_fred_people);
		    holeCloud = loadBitmap(R.drawable.ginger_and_fred_hole_cloud);
			lightning = loadBitmap(R.drawable.ginger_and_fred_lightning);
			
			peopleMatrix = new Matrix();
			peopleMatrix.setTranslate(peoplePosition, 90);
		
			
			peopleAnimation = new AnimationAction(System.currentTimeMillis()+PEOPLE_INTERVAL,new Runnable(){public void run(){synchronized(GingerAndFredView.this){
				peoplePosition+=(peopleDirection*4);
				
				if (peoplePosition>=320-people.getWidth()-5){
					peopleDirection=-1;
				}
				if (peoplePosition <=5){
					peopleDirection=1;
				}
				
				peopleMatrix.reset();
				if (peopleDirection<0){
					peopleMatrix.postScale(-1, 1);
					peopleMatrix.postTranslate(people.getWidth(), 0);
				}		
				peopleMatrix.postTranslate(peoplePosition, 90);
				lowCloudZone.applyMatrix(peopleMatrix);
				rainZone.applyMatrix(peopleMatrix);
				
				showLightning=false;
				if (peoplePosition>=320-people.getWidth()-15 && peopleDirection>0 && ((peoplePosition&1)==1)){
					showLightning=true;
				}
				if (peoplePosition<=15 && peopleDirection<0 && ((peoplePosition&1)==1)){
					showLightning=true;
				}
				
				holeCloudPosition+=(holeCloudDirection*4);
				if (holeCloudPosition>=320-holeCloud.getWidth()-5){
					holeCloudDirection=-1;
				}
				if (holeCloudPosition <= 5){
					holeCloudDirection=1;
				}
				hiCloudMatrix.setTranslate(holeCloudPosition, 8);
				hiCloudZone.applyMatrix(hiCloudMatrix);
				
				++rainStage;
				if (rainStage>=rain.length){
					rainStage=0;
				}
				
				checkIfBumperMovedOverBall(POEPLE);
				
				peopleAnimation.actionTime=System.currentTimeMillis()+PEOPLE_INTERVAL;
				postInvalidate();
			}}});
			addAction(peopleAnimation);
			
			rainAnimation = new AnimationAction(System.currentTimeMillis()+RAIN_INTERVAL,new Runnable(){public void run(){synchronized(GingerAndFredView.this){
				
				++rainStage;
				if (rainStage>=rain.length){
					rainStage=0;
				}
				
				rainAnimation.actionTime=System.currentTimeMillis()+RAIN_INTERVAL;
				postInvalidate();
			}}});
			addAction(rainAnimation);
			
			lowCloudZone = new Zone(new Point[]{		
				new Point(29,247),
				new Point(51,245),
				new Point(66,231),
				new Point(91,240),
				new Point(116,215),
				new Point(136,228),
				new Point(175,241),
				new Point(192,230),
				new Point(205,229),
				new Point(221,218),
				new Point(244,223),
				new Point(253,245),
				new Point(246,265),
				new Point(231,267),
				new Point(212,257),
				new Point(189,269),
				new Point(168,266),
				new Point(135,286),
				new Point(99,264),
				new Point(84,271),
				new Point(69,265),
				new Point(39,273),
				new Point(29,260)
			    },LOW_CLOUD, new VelocityAdjustor(){public Velocity adjust(Velocity velocity, int timeDelta) {synchronized(GingerAndFredView.this){
			    	
			    	//Decelerate due to friction
			    	float total = velocity.total() + (DEFAULT_FRICTION*2)*timeDelta;
					if (total<0)
						total=0;
					velocity.setTotal(total);
			    	
					ballStart.x=ball.x+=(holeCloudDirection);
			    	
			    	return velocity;//.push(rampVelocity,timeDelta);
			    }
			}},true);
			lowCloudZone.applyMatrix(peopleMatrix);
			
			rainVelocity = new Velocity(new Point(307,37),new Point(205,202));
			rainVelocity.setTotal(0.003f);
			
			rainZone = new Zone(new Point[]{		
				new Point(38,271),
				new Point(68,266),
				new Point(85,273),
				new Point(98,266),
				new Point(132,284),
				new Point(167,265),
				new Point(191,272),
				new Point(210,255),
				new Point(229,268),
				new Point(228,363),
				new Point(39,360),
				},RAIN_ZONE, new VelocityAdjustor(){public Velocity adjust(Velocity velocity, int timeDelta) {synchronized(GingerAndFredView.this){
		    	
			    	//Decelerate due to friction
			    	float total = velocity.total() + DEFAULT_FRICTION*timeDelta;
					if (total<0)
						total=0;
					velocity.setTotal(total);
			   
			    	return velocity.accelerate(rainVelocity,timeDelta);
				}
			}},true);
			rainZone.applyMatrix(peopleMatrix);
			
			hiCloudZone = new Zone(new Point[]{		
				new Point(0,28),
				new Point(9,19),
				new Point(48,18),
				new Point(63,1),
				new Point(88,1),
				new Point(109,20),
				new Point(118,20),
				new Point(134,14),
				new Point(139,18),
				new Point(136,31),
				new Point(124,39),
				new Point(100,38),
				new Point(82,43),
				new Point(70,42),
				new Point(54,37),
				new Point(46,37),
				new Point(37,42),
				new Point(28,42),
				new Point(22,36),
				new Point(3,36)
			},HI_CLOUD, new VelocityAdjustor(){public Velocity adjust(Velocity velocity, int timeDelta) {synchronized(GingerAndFredView.this){
		    	
		    	//Decelerate due to friction
		    	float total = velocity.total() + (DEFAULT_FRICTION*2)*timeDelta;
				if (total<0)
					total=0;
				velocity.setTotal(total);
		    	
				ball.x+=(peopleDirection);
				ballStart.x=ball.x;
		    	
		    	return velocity;//.push(rampVelocity,timeDelta);
			}}},true);
			hiCloudMatrix = new Matrix();
			
			
		}
	
		synchronized protected void drawBackground(Canvas canvas){
			super.drawBackground(canvas);
			Matrix m = new Matrix(peopleMatrix);
			m.preTranslate(40,270);
			canvas.drawBitmap(rain[rainStage], m, null);
			m.preTranslate(60,0);
			//canvas.drawBitmap(rain[rainStage1], m, null);
			m.preTranslate(60, 0);
			//canvas.drawBitmap(rain[rainStage2], m, null);
			canvas.drawBitmap(people, peopleMatrix, null);
			if (showLightning){
				m.preTranslate(-30, -75);
				canvas.drawBitmap(lightning, m, null);
			}
			canvas.drawBitmap(holeCloud, hiCloudMatrix, null);
		}
		
		protected void drawSurface(Canvas canvas){
			super.drawSurface(canvas);
	
		}	
		
		protected Coordinate ballStartPoint(){
			return new Coordinate(40,440);
		}
		protected Coordinate ballHolePoint(){
			return new Coordinate(holeCloudPosition+75,29);
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
	
		static final int POEPLE=1;
		
		protected void onObjectBounce(int id) {
			
		}
		
		synchronized Bumper getBumper(int whichBumper){
			switch (whichBumper){
			case POEPLE: 
				if (peopleDirection<0){
					return new Bumper(new Point[]{
							new Point(peoplePosition+(255-88),316),	
							new Point(peoplePosition+(255-88),292),	
							new Point(peoplePosition+(255-65),277),	
							new Point(peoplePosition+(255-10),277),	
							new Point(peoplePosition+(255-41),209),	
							new Point(peoplePosition+(255-67),202),	
							new Point(peoplePosition+(255-53),153),	
							new Point(peoplePosition+(255-24),163),	
							new Point(peoplePosition+(255-0),153),	
							new Point(peoplePosition+(255-0),138),	
							new Point(peoplePosition+(255-18),146),	
							new Point(peoplePosition+(255-41),145),	
							new Point(peoplePosition+(255-28),130),	
							new Point(peoplePosition+(255-57),115),	
							new Point(peoplePosition+(255-74),93),	
							new Point(peoplePosition+(255-96),113),	
							new Point(peoplePosition+(255-127),121),	
							new Point(peoplePosition+(255-120),97),	
							new Point(peoplePosition+(255-149),98),	
							new Point(peoplePosition+(255-145),117),	
							new Point(peoplePosition+(255-242),104),	
							new Point(peoplePosition+(255-224),132),	
							new Point(peoplePosition+(255-167),138),	
							new Point(peoplePosition+(255-167),158),	
							new Point(peoplePosition+(255-205),186),	
							new Point(peoplePosition+(255-227),165),	
							new Point(peoplePosition+(255-259),164),	
							new Point(peoplePosition+(255-215),222),	
							new Point(peoplePosition+(255-185),214),	
							new Point(peoplePosition+(255-176),235),	
							new Point(peoplePosition+(255-187),291),	
							new Point(peoplePosition+(255-159),309),	
							new Point(peoplePosition+(255-158),255),	
							new Point(peoplePosition+(255-145),272),
							new Point(peoplePosition+(255-114),268),
							new Point(peoplePosition+(255-107),296)
							
					},POEPLE);	
				}else{
				return new Bumper(new Point[]{
						new Point(peoplePosition+88,316),	
						new Point(peoplePosition+88,292),	
						new Point(peoplePosition+65,277),	
						new Point(peoplePosition+10,277),	
						new Point(peoplePosition+41,209),	
						new Point(peoplePosition+67,202),	
						new Point(peoplePosition+53,153),	
						new Point(peoplePosition+24,163),	
						new Point(peoplePosition+0,153),	
						new Point(peoplePosition+0,138),	
						new Point(peoplePosition+18,146),	
						new Point(peoplePosition+41,145),	
						new Point(peoplePosition+28,130),	
						new Point(peoplePosition+57,115),	
						new Point(peoplePosition+74,93),	
						new Point(peoplePosition+96,113),	
						new Point(peoplePosition+127,121),	
						new Point(peoplePosition+120,97),	
						new Point(peoplePosition+149,98),	
						new Point(peoplePosition+145,117),	
						new Point(peoplePosition+242,104),	
						new Point(peoplePosition+224,132),	
						new Point(peoplePosition+167,138),	
						new Point(peoplePosition+167,158),	
						new Point(peoplePosition+205,186),	
						new Point(peoplePosition+227,165),	
						new Point(peoplePosition+259,164),	
						new Point(peoplePosition+215,222),	
						new Point(peoplePosition+185,214),	
						new Point(peoplePosition+176,235),	
						new Point(peoplePosition+187,291),	
						new Point(peoplePosition+159,309),	
						new Point(peoplePosition+158,255),	
						new Point(peoplePosition+145,272),
						new Point(peoplePosition+114,268),
						new Point(peoplePosition+107,296)
						
				},POEPLE);
				}
			}
			return null;
		}
		
		@Override
		Zone getZone(int whichZone){
			switch (whichZone){
			case LOW_CLOUD: 
				return lowCloudZone;			
			case RAIN_ZONE: 
				return rainZone;
			case HI_CLOUD:
				return hiCloudZone;
			}
			return null;
		}
		
		@Override
	    void downTheHole(){
	    	super.downTheHole();
	    	Intent data = new Intent(getApplicationContext(),GardenGolf.class);
	    	data.putExtra("Score", numberOfSwings);
	    	data.putExtra("green", 8);
	    	startActivity(data);
	    	GingerAndFred.this.finish();
	    }
	}
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        golfView = new GingerAndFredView (this.getApplicationContext(),width,height);
        layout.addView(golfView, 0);
        golfView.setScoreUpdator(this);
        score.setPadding(10, 10, 10, 10);
        LayoutParams lp = new FrameLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
        lp.gravity=Gravity.BOTTOM|Gravity.RIGHT;
        score.setLayoutParams(lp);
    }
}

