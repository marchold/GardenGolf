package com.catglo.gardengolf18;


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

public class BillyHoliday extends GolfActivity {
   
	public class BillyHolidayView extends GolfView {
		
		private Bitmap frame1;
		private Bitmap frame2;
		private AnimationAction popOutTimer;
		
		
		
		
		public BillyHolidayView(Context context, int width, int height) {
			super(context, width, height);
			setBackgroundResource(R.drawable.billie_frame_1);
			
			popOutTimer = new AnimationAction(0,new Runnable(){public void run(){
				buttonsPresssed=false;
				post(new Runnable(){public void run(){
					setBackgroundResource(R.drawable.billie_frame_1);
					buttonPressedTime=0;
				}});
				removeAction(popOutTimer);
			}});
			
		
		}
	
		protected Coordinate ballStartPoint(){
			return new Coordinate(40,440);
		}
		final Coordinate hole = new Coordinate(57,121);
		final Coordinate noHole = new Coordinate(-100,-100); 
		protected Coordinate ballHolePoint(){
			if (buttonsPresssed) {
				return hole;
			} else {
				return noHole;
			}
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
		
		static final int BUTTON_LEFT=1;
		static final int BUTTON_RIGHT=2;
		static final int NOTE1=3;
		static final int NOTE2=4;
		static final int NOTE3=5;
		
		
		boolean buttonsPresssed=false;
		long buttonPressedTime;
		static final int POP_OUT_TIME_IN_MILLS = 10000;
		
		@Override
		protected void onObjectBounce(int id, float force) {
			if (!buttonsPresssed){
				buttonsPresssed=true;
				post(new Runnable(){public void run(){
					setBackgroundResource(R.drawable.billie_frame_2);
					buttonPressedTime=System.currentTimeMillis();
				}});
				popOutTimer.actionTime = System.currentTimeMillis()+POP_OUT_TIME_IN_MILLS;
				addAction(popOutTimer);
			}
		}
		
		final Bumper[] bumpers1 = {null,
			new Bumper(new Point[]{new Point(157,339),new Point(167,339),new Point(167,349),new Point(157,349)},BUTTON_LEFT),
			new Bumper(new Point[]{new Point(218,341),new Point(228,341),new Point(228,351),new Point(218,351)},BUTTON_RIGHT),
			dummyBumper,  
			dummyBumper,		  
			dummyBumper
		};
		

		final Bumper[] bumpers2 = {null,
			dummyBumper,
			dummyBumper,
			new Bumper(new Point[]{new Point( 82,166),new Point(89,142),new Point( 87,141),new Point( 77,166)},NOTE1),  
			new Bumper(new Point[]{new Point(118,127),new Point(121,121),new Point(121,108),new Point(128,110),new Point(122,102),new Point(121,97),new Point(131,103),new Point(118,91),new Point(117,118),new Point(111,120),new Point(113,126)},NOTE2),		  
			new Bumper(new Point[]{new Point(84,106),new Point(88,105),new Point(98,89),new Point(85,86),new Point(80,97),new Point(75,97),new Point(74,101),new Point(80,102)},NOTE3)
		};
		
		Bumper getBumper(int whichBumper){
			if (whichBumper >= bumpers1.length){
				return null;
			}
			if (buttonsPresssed){
				return bumpers2[whichBumper];
			} else {
				return bumpers1[whichBumper];
			}
		}
		
		final float STAR_FORCE = 0.003f;
		Velocity pushBot = new Velocity(new Point(148,400),new Point(272,356)).setTotal(STAR_FORCE);
		Velocity pushMid = new Velocity(new Point(205,202),new Point(307,202)).setTotal(STAR_FORCE);
		Velocity pushTop = new Velocity(new Point(205,202),new Point(205,37)).setTotal(STAR_FORCE);
		Polygon z1 = new Polygon(new Point[]{new Point(23,94),new Point(58,110),new Point(89,62),new Point(29,73)});
		Polygon z2 = new Polygon(new Point[]{new Point(34,65),new Point(221,61),new Point(113,16)});
		Polygon z3 = new Polygon(new Point[]{new Point(92,438),new Point(129,453),new Point(167,415)});
		Polygon z4 = new Polygon(new Point[]{new Point(313,387),new Point(168,440),new Point(95,368)});
	
		
		static final int TOPSTAR_ZONE =1;
		static final int BOTTOMSTAR_ZONE =2;
		final Zone[] zones = {null,
			new Zone(new Point[] {new Point(30,92),new Point(59,105),new Point(82,66),new Point(125,58),new Point(167,64),new Point(191,53),new Point(194,30),new Point(185,15),new Point(176,21),new Point(182,39),new Point(170,48),new Point(136,37),new Point(90,40),new Point(50,58),new Point(35,78)},
				TOPSTAR_ZONE,new VelocityAdjustor(){public Velocity adjust(Velocity velocity, int timeDelta) {	    	
		    	//Decelerate due to friction
		    	float total = velocity.total() + (DEFAULT_FRICTION*4)*timeDelta;
				if (total<0)
					total=0;
				velocity.setTotal(total);
				if (z1.contains(ball)){
					return velocity.accelerate((Velocity) pushBot,timeDelta);
				} else if (z2.contains(ball)) {
					return velocity.accelerate((Velocity) pushMid,timeDelta);
				} else {
					return velocity.accelerate((Velocity) pushTop,timeDelta);
				}
			}},true),
			new Zone(new Point[] {new Point(96,440),new Point(134,457),new Point(145,413),new Point(178,406),new Point(212,404),new Point(247,403),new Point(281,393),new Point(284,365),new Point(272,349),new Point(264,357),new Point(269,372),new Point(256,382),new Point(219,377),new Point(174,377),new Point(137,387),new Point(114,398),new Point(99,420)},
				BOTTOMSTAR_ZONE,new VelocityAdjustor(){public Velocity adjust(Velocity velocity, int timeDelta) {	    	
				//Decelerate due to friction
		    	float total = velocity.total() + (DEFAULT_FRICTION*2)*timeDelta;
				if (total<0)
					total=0;
				velocity.setTotal(total);
				if (z3.contains(ball)){
					return velocity.accelerate((Velocity) pushBot,timeDelta);
				} else if (z4.contains(ball)) {
					return velocity.accelerate((Velocity) pushMid,timeDelta);
				} else {
					return velocity.accelerate((Velocity) pushTop,timeDelta);
				}
			}},true),
		};
		
		@Override
		Zone getZone(int whichZone){
			if (whichZone >= zones.length || buttonsPresssed==false){
				return null;
			}
			return zones[whichZone];
		}
		
		@Override
	    void downTheHole(){
	    	super.downTheHole();
	    	Intent data = new Intent(getApplicationContext(),GardenGolf.class);
	    	data.putExtra("Score", numberOfSwings);
	    	data.putExtra("green", greenNumber);
	    	startActivity(data);
	    	BillyHoliday.this.finish();
	    }
	}
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        width = getWindow().getWindowManager().getDefaultDisplay().getWidth();
    	height = getWindow().getWindowManager().getDefaultDisplay().getHeight();
    	
    	golfView = new BillyHolidayView (this.getApplicationContext(),width,height);
        layout.addView(golfView, 0);
        golfView.setScoreUpdator(this);
        score.setPadding(10, 10, 10, 10);
        LayoutParams lp = new FrameLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
        lp.gravity=Gravity.TOP|Gravity.RIGHT;
        score.setLayoutParams(lp);
    }
}

