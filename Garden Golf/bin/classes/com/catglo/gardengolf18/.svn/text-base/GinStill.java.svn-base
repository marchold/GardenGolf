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
import android.util.Log;

public class GinStill extends GolfActivity {
    public class GinStillView extends GolfView {
		
		private Bitmap[] catapult;
		private Bitmap[] shotGlass;
		private AnimationAction catapultAnimation;
		private AnimationAction shotglassShakingAnimation;
		private int catapultStage=0;
		private int shotglassShakingStage=0;
		private int shotglassShakingStep=0;
		private Bitmap still;
		private Matrix shotglassRotationMatrix;
		private AnimationAction shotglassTipingAnimation;
		protected int shotglassTipingStage=0;
		private Matrix shotGlassPlacementMatrix;
		private Bitmap[] goblet;
		private int gobletStage=0;
		static final int CATAPULT_INTERVAL = 600;
		static final int FLOWER_WAIT = 3000;
		boolean shotOpen=false;
		private Matrix shotGlassMatrix;
		private int shakeForce;
		static final int SHOTGLASS_INTERVAL = 50;
		
		
		public GinStillView(Context context, int width, int height) {
			super(context, width, height);
			setBackgroundResource(R.drawable.ginstill_background);
			
			catapult = new Bitmap[4];
			catapult[0]  = loadBitmap(R.drawable.ginstill_catapault_startposition);
			catapult[1]  = loadBitmap(R.drawable.ginstill_catapault_handledown);
			catapult[2]  = loadBitmap(R.drawable.ginstill_catapault_handleup);
			catapult[3]  = loadBitmap(R.drawable.ginstill_catapault_empty);
			
			shotGlass = new Bitmap[2];
			shotGlass[0] = loadBitmap(R.drawable.ginstill_shotglass);
			shotGlass[1] = loadBitmap(R.drawable.ginstill_shotglass_contense);
		
			still = loadBitmap(R.drawable.ginstill_cord_right);
			goblet = new Bitmap[3];
			goblet[0] = loadBitmap(R.drawable.ginstill_martini_glass_empty);
			goblet[1] = loadBitmap(R.drawable.ginstill_martini_glass_vermoth);
			goblet[2] = loadBitmap(R.drawable.ginstill_martini_glass_full);
			
			
			catapultAnimation = new AnimationAction(System.currentTimeMillis()+CATAPULT_INTERVAL,new Runnable(){public void run(){
				catapultStage++;
				catapultAnimation.actionTime=System.currentTimeMillis()+CATAPULT_INTERVAL;
				if (catapultStage==catapult.length-1){
					removeAction(catapultAnimation);
					gobletStage++;
					shotOpen=true;
				}
				postInvalidate();
			}});
			//
			
			shotglassRotationMatrix = new Matrix();
			shotGlassPlacementMatrix = new Matrix(); 
			shotGlassMatrix = new Matrix();
			shotGlassPlacementMatrix.postTranslate(57+(shotGlass[0].getWidth()/2),169+(shotGlass[0].getHeight()/2));
			shotglassRotationMatrix.postTranslate(-(shotGlass[0].getWidth()/2),-(shotGlass[0].getHeight()/2));
			
			shakeForce=12;
			shotglassShakingAnimation = new AnimationAction(System.currentTimeMillis()+SHOTGLASS_INTERVAL,new Runnable(){public void run(){
				if (shotglassShakingStep==0){
					shotglassRotationMatrix.postRotate(shakeForce/2);
				}
				if ((shotglassShakingStep&1)==0){
					shotglassRotationMatrix.postRotate(-shakeForce);
				} else {
					shotglassRotationMatrix.postRotate(shakeForce);
				}
				shotglassShakingStage++;
				if (shotglassShakingStage==2){
					shotglassShakingStep++;
					shotglassShakingStage=0;
					if (shotglassShakingStep==3){
						removeAction(shotglassShakingAnimation);
						shotglassRotationMatrix.setTranslate(-(shotGlass[0].getWidth()/2),-(shotGlass[0].getHeight()/2));	
					}
				}
				postInvalidate();
				shotglassShakingAnimation.actionTime=System.currentTimeMillis()+SHOTGLASS_INTERVAL;	
			}});
			
			shotglassTipingAnimation = new AnimationAction(System.currentTimeMillis()+SHOTGLASS_INTERVAL,new Runnable(){public void run(){
				shotglassTipingStage++;
				shotglassRotationMatrix.postRotate(90/10);
				if (shotglassTipingStage==10){
					removeAction(shotglassTipingAnimation);
					++gobletStage;
					addAction(catapultAnimation);
				}
				postInvalidate();
				shotglassTipingAnimation.actionTime=System.currentTimeMillis()+SHOTGLASS_INTERVAL;	
			}});
			
		}
		
		protected void checkIfBumperMovedOverBall(int whichBumper) {
			// TODO Auto-generated method stub
		}
	
		protected void drawBackground(Canvas canvas){
			super.drawBackground(canvas);
			
			canvas.drawBitmap(shotGlass[1], 84,207, null);
			shotGlassMatrix.set(shotglassRotationMatrix);
			shotGlassMatrix.postConcat(shotGlassPlacementMatrix);
			canvas.drawBitmap(shotGlass[0],  shotGlassMatrix, null);
			canvas.drawBitmap(goblet[gobletStage], 114,228, null);
			canvas.drawBitmap(catapult[catapultStage], 172,261, null);
			canvas.drawBitmap(still, 5,19, null);
						
		}
		
		protected Coordinate ballStartPoint(){
			return new Coordinate(40,440);
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
		
		static final int GINSTILL=1;
		static final int SHOT_GLASS=2;
		static final int MARTINI_GLASS=3;
		static final int SPOON=4;
			
		@Override
		protected void onObjectBounce(int id, float force) {
			switch (id){
			case SHOT_GLASS: 
				Log.i("GOLF","force = "+force);
				if (force > 0.6){
					addAction(shotglassTipingAnimation);
				} else {
					shakeForce=(int) (force*30);
					shotglassShakingStage=0;
					shotglassShakingStep=0;
					addAction(shotglassShakingAnimation);
				}
				break;
			}
		}
		
		Bumper getBumper(int whichBumper){
			switch (whichBumper){
			case GINSTILL: 
				if (shotOpen) {
					return new Bumper(new Point[]{
							new Point(5,155),
							new Point(77,161),
							new Point(100,178),
							new Point(126,166),
							new Point(163,202),
							new Point(148,236),
							new Point(156,237),
							new Point(167,202),
							new Point(157,178),
							new Point(133,162),
							new Point(153,122),
							new Point(142,93),
							new Point(127,80),
							new Point(131,58),
							new Point(108,26),
							new Point(74,5)
						},GINSTILL);
				}
				else{
					return new Bumper(new Point[]{
						new Point(0,157),
						new Point(78,165),
						new Point(91,177),
						new Point(129,169),
						new Point(157,197),
						new Point(151,234),
						new Point(176,281),
						new Point(232,288),
						new Point(220,314),
						new Point(248,308),
						new Point(264,315),
						new Point(320,277),
						new Point(300,165)
					},GINSTILL);
				}			
			case SHOT_GLASS:
				if (shotOpen) {
					return dummyBumper;
				}
				else{
					return new Bumper(new Point[]{
						new Point(89,212),
						new Point(92,242),
						new Point(115,243),
						new Point(116,210)
					},SHOT_GLASS); 
				}			
			case MARTINI_GLASS:
				return new Bumper(new Point[]{
					new Point(155,272),
					new Point(156,306),
					new Point(138,314),
					new Point(160,321),
					new Point(179,316),
					new Point(164,307),
					new Point(164,270),
					new Point(194,241),
					new Point(161,234),
					new Point(121,243)
				},MARTINI_GLASS);	
			case SPOON:
				if (shotOpen){
					return new Bumper(new Point[]{
						new Point(191,328),
						new Point(216,305),
						new Point(228,312),
						new Point(249,305),
						new Point(258,290),
						new Point(275,289),
						new Point(284,268),
						new Point(264,282),
						new Point(246,275),
						new Point(182,323)
					},SPOON);	
				} else {
					return dummyBumper;
				}
			}
				
			return null;
		}//(gimp-path-get-points 48 "b")
		//> (gimp-image-list)
		//(5 #( 56 50 48 36 27 ))
		//> (gimp-image-get-name 56)
		
	
		@Override
	    void downTheHole(){
	    	super.downTheHole();
	    	Intent data = new Intent(getApplicationContext(),GardenGolf.class);
	    	data.putExtra("Score", numberOfSwings);
	    	data.putExtra("green", greenNumber);
	    	startActivity(data);
	    	GinStill.this.finish();
	    }
		
	} 
	
	 @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        golfView = new GinStillView (this.getApplicationContext(),width,height);
        layout.addView(golfView, 0);
        golfView.setScoreUpdator(this);
    }
}

