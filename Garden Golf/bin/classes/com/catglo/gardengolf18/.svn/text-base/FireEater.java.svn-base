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

public class FireEater extends GolfActivity {
	public class FireEaterView extends GolfView {
		
		private Bitmap[][] topFlame;
		private Bitmap[] body;
		private AnimationAction fireAnimation;
		private AnimationAction bodyAnimation;
		private int fireStage=0;
		private int bodyStage=0;
		private Bitmap[] ballFlame;
		private Bitmap[] lowFlame;
		private Bitmap[] lowMatches;
		private Bitmap[] topMatches;
		private boolean fireOn=false;
		private boolean ballOnFire=false;
		static final int FIRE_INTERVAL = 270;
		static final int BODY_INTERVAL = 1000;
		
		
		public FireEaterView(Context context, int width, int height) {
			super(context, width, height);
			setBackgroundResource(R.drawable.fire_eater_background);
			
			ballFlame = new Bitmap[3];
			ballFlame[0] = loadBitmap(R.drawable.ball_fire_1);
			ballFlame[1] = loadBitmap(R.drawable.ball_fire_2);
			ballFlame[2] = loadBitmap(R.drawable.ball_fire_3);
			
			topFlame = new Bitmap[4][3];
			topFlame[0][0] = loadBitmap(R.drawable.fire_eater_frame1_topflameanim_a);
			topFlame[0][1] = loadBitmap(R.drawable.fire_eater_frame1_topflameanim_b);
			topFlame[0][2] = loadBitmap(R.drawable.fire_eater_frame1_topflameanim_c);
			
			topFlame[1][0] = loadBitmap(R.drawable.fire_eater_frame2_lowflameanim_a);
			topFlame[1][1] = loadBitmap(R.drawable.fire_eater_frame2_lowflameanim_b);
			topFlame[1][2] = loadBitmap(R.drawable.fire_eater_frame2_lowflameanim_c);
			
			topFlame[2][0] = loadBitmap(R.drawable.fire_eater_frame3_topflameanim_a);
			topFlame[2][1] = loadBitmap(R.drawable.fire_eater_frame3_topflameanim_b);
			topFlame[2][2] = loadBitmap(R.drawable.fire_eater_frame3_topflameanim_c);
			
			topFlame[3][0] = loadBitmap(R.drawable.fire_eater_frame4_topflameanim_a);
			topFlame[3][1] = loadBitmap(R.drawable.fire_eater_frame4_topflameanim_b);
			topFlame[3][2] = loadBitmap(R.drawable.fire_eater_frame4_topflameanim_c);
			
			lowFlame = new Bitmap[3];
			lowFlame[0] = loadBitmap(R.drawable.fire_eater_frame2_lowflameanim_a);
			lowFlame[1] = loadBitmap(R.drawable.fire_eater_frame2_lowflameanim_b);
			lowFlame[2] = loadBitmap(R.drawable.fire_eater_frame2_lowflameanim_c);
			
			lowMatches = new Bitmap[3];
			lowMatches[0] = loadBitmap(R.drawable.fire_eater_matches_bottom_right_flame_a);
			lowMatches[1] = loadBitmap(R.drawable.fire_eater_matches_bottom_right_flame_b);
			lowMatches[2] = loadBitmap(R.drawable.fire_eater_matches_bottom_right_flame_c);
		
			topMatches = new Bitmap[3];
			topMatches[0] = loadBitmap(R.drawable.fire_eater_matches_top_left_flame_a);
			topMatches[1] = loadBitmap(R.drawable.fire_eater_matches_top_left_flame_b);
			topMatches[2] = loadBitmap(R.drawable.fire_eater_matches_top_left_flame_c);
			
			body = new Bitmap[4];
			body[0] = loadBitmap(R.drawable.fire_eater_frame1_2_bodyposture);
			body[1] = body[0];
			body[2] = loadBitmap(R.drawable.fire_eater_frame3_bodyposture);
			body[3] = loadBitmap(R.drawable.fire_eater_frame4_bodyposture);
			
			
			fireAnimation = new AnimationAction(System.currentTimeMillis()+FIRE_INTERVAL,new Runnable(){public void run(){
				fireStage++;
				if (fireStage>=topMatches.length){
					fireStage=0;
				}
				fireAnimation.actionTime=System.currentTimeMillis()+FIRE_INTERVAL;
				postInvalidate();
			}});
			addAction(fireAnimation);
			
			bodyAnimation = new AnimationAction(System.currentTimeMillis()+BODY_INTERVAL,new Runnable(){public void run(){
				bodyStage++;
				if (bodyStage>=body.length){
					bodyStage=0;
				}
				bodyAnimation.actionTime=System.currentTimeMillis()+BODY_INTERVAL;	
			}});
			//addAction();	
		}
		
		protected void checkIfBumperMovedOverBall(int whichBumper) {
			// TODO Auto-generated method stub
			
		}
	
		protected void drawBackground(Canvas canvas){
			super.drawBackground(canvas);
			canvas.drawBitmap(topMatches[fireStage], 5,5, null);
			canvas.drawBitmap(lowMatches[fireStage], 283,432, null);
			canvas.drawBitmap(body[bodyStage], 32,6, null);
			final Point[] bodyStageCoordinates = {
				new Point(228,40),
				new Point(255,222),
				new Point(38,125),
				new Point(100,68)
			};
			if (fireOn && topFlame[bodyStage][fireStage]!=null)
				canvas.drawBitmap(topFlame[bodyStage][fireStage], bodyStageCoordinates[bodyStage].x, bodyStageCoordinates[bodyStage].y, null);
		}
		
		protected void drawSurface(Canvas canvas){
			super.drawSurface(canvas);
			if (ballOnFire){
				canvas.drawBitmap(ballFlame[fireStage], ball.x-ballFlame[fireStage].getWidth()+13, ball.y-ballFlame[fireStage].getHeight()+3, null);
			}
		}	
		
		protected Coordinate ballStartPoint(){
			return new Coordinate(160,440);
		}
		protected Coordinate ballHolePoint(){
			return new Coordinate(300,21);
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
			if (currentBumper<5) return true;
			return false;
		}
	
		static final int BODY=1;
		static final int TOP_MATCHES=2;
		static final int LOW_MATCHES=3;
		static final int FLAMBO=4;
		static final int HAT_AND_PAIL=5;
		
		@Override
		protected void onObjectBounce(int id, float force) {
			if (id == TOP_MATCHES || id == LOW_MATCHES){
				if (!ballOnFire){
					ballOnFire=true;
				}
			}
			if (id == FLAMBO && ballOnFire && bodyStage<2 && fireOn==false){
				fireOn=true;
				addAction(bodyAnimation);
			}
		}
		
		Bumper getBumper(int whichBumper){
			switch (whichBumper){
			case BODY: 
				return new Bumper(new Point[]{
						new Point(254,309),	
						new Point(219,333),	
						new Point(258,413),	
						new Point(234,440),	
						new Point(206,440),	
						new Point(222,410),	
						new Point(170,396),	
						new Point(96,331),	
						new Point(90,430),	
						new Point(67,447),	
						new Point(30,443),	
						new Point(65,402),	
						new Point(66,279),	
						new Point(169,125),	
						new Point(153,65),	
						new Point(170,6),	
						new Point(202,6),	
						new Point(194,32),	
						new Point(276,166),	
						new Point(251,228)	
				},BODY);
			case TOP_MATCHES:
				return new Bumper(new Point[]{
						new Point(0,41),
						new Point(36,30),
						new Point(30,0)
					},TOP_MATCHES);
			case LOW_MATCHES:
				return new Bumper(new Point[]{
						new Point(283,480),
						new Point(291,446),
						new Point(320,455)
					},LOW_MATCHES);
			case FLAMBO:
				if (bodyStage>1){
					return null;
				}
				return new Bumper(new Point[]{
						new Point(234,315),	
						new Point(320,289),	
						new Point(320,270),	
						new Point(233,304)
					},FLAMBO);
			}
			return null;
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
	    	FireEater.this.finish();
	    }
	}
	
   @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        golfView = new FireEaterView (this.getApplicationContext(),width,height);
        layout.addView(golfView, 0);
        golfView.setScoreUpdator(this);
        score.setPadding(70, 10, 10, 10);
        fontColor = 000000;
        LayoutParams lp = new FrameLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
        lp.gravity=Gravity.TOP;
        score.setLayoutParams(lp);
    }
   
}

