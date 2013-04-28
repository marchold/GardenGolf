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

public class WizardOfOz extends GolfActivity {
	public class WizardOfOzView extends GolfView {
		
		private Bitmap[] slipers;
		private Bitmap[] pail;
		private AnimationAction slipersAnimation;
		private AnimationAction pailAnimation;
		private int slipperStage=0;
		private int pailStage=0;
		private Bitmap[] smoke;
		private AnimationAction smokeAnimation;
		protected int smokeStage=-1;
		static final int SLIPERS_INTERVAL = 200;
		static final int PAIL_INTERVAL = 500;
		private static final long SMOKE_INTERVAL = 70;
		
		
		public WizardOfOzView(Context context, int width, int height) {
			super(context, width, height);
			setBackgroundResource(R.drawable.oz_background);
			
			slipers = new Bitmap[12];
			slipers[0] = loadBitmap(R.drawable.oz_rubyslippers_frames1_3_5_7);
			slipers[1] = loadBitmap(R.drawable.oz_rubyslippers_frames2_4_6);
			slipers[2] = slipers[0];
			slipers[3] = slipers[1];
			slipers[4] = slipers[0];
			slipers[5] = slipers[1];
			slipers[6] = slipers[0];
			slipers[7] = loadBitmap(R.drawable.oz_rubyslippers_frame8);
			slipers[8] = loadBitmap(R.drawable.oz_rubyslippers_frame9);
			slipers[9] = loadBitmap(R.drawable.oz_rubyslippers_frame10);
			slipers[10]= loadBitmap(R.drawable.oz_rubyslippers_frame11);
			slipers[11]= loadBitmap(R.drawable.oz_rubyslippers_frame12);
			
			
			pail = new Bitmap[4];
			pail[0] = loadBitmap(R.drawable.buckethat_anim1);
			pail[1] = loadBitmap(R.drawable.buckethat_anim2);
			pail[2] = loadBitmap(R.drawable.buckethat_anim3);
			pail[3] = loadBitmap(R.drawable.buckethat_anim4);
				
			
			smoke = new Bitmap[2];
			smoke[0] = loadBitmap(R.drawable.smokeframe1_3);
			smoke[1] = loadBitmap(R.drawable.smokeframe2_4);
			
			slipersAnimation = new AnimationAction(System.currentTimeMillis()+SLIPERS_INTERVAL,new Runnable(){public void run(){synchronized(WizardOfOzView.this){
				slipperStage++;
				if (slipperStage==slipers.length){
					removeAction(slipersAnimation);
				}
				slipersAnimation.actionTime=System.currentTimeMillis()+SLIPERS_INTERVAL;
				postInvalidate();
			}}});
			//addAction(slipersAnimation);
			
			pailAnimation = new AnimationAction(System.currentTimeMillis()+PAIL_INTERVAL,new Runnable(){public void run(){synchronized(WizardOfOzView.this){
				pailStage++;
				if (pailStage==pail.length-1){
					removeAction(pailAnimation);
					addAction(smokeAnimation);
				}
				pailAnimation.actionTime=System.currentTimeMillis()+PAIL_INTERVAL;	
			}}});
			
			smokeAnimation = new AnimationAction(System.currentTimeMillis()+SMOKE_INTERVAL,new Runnable(){public void run(){synchronized(WizardOfOzView.this){
				smokeStage++;
				if (smokeStage==8){
					removeAction(pailAnimation);
				}
			}}});
		}
		
	
		synchronized protected void drawBackground(Canvas canvas){
			if (slipperStage<slipers.length){
				canvas.drawBitmap(slipers[slipperStage], 247,11, null);
			}
			if (pailStage < pail.length) //Fix for bug reported through android market
				canvas.drawBitmap(pail[pailStage], 4,4, null);
			if (smokeStage>=0){
				canvas.drawBitmap(smoke[smokeStage&1], 88, 50-(smokeStage*14), null);
			}
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
		
		
		static final int ROSE1=1;
		static final int ROSE2=2;
		static final int ROSE3=3;
		static final int SHOES=4;
		static final int HAT_AND_PAIL=5;
		
		@Override
		protected void onObjectBounce(int id, float force) {
			if (id == HAT_AND_PAIL){
				addAction(slipersAnimation);
				addAction(pailAnimation);
			}
		}
		
		synchronized Bumper getBumper(int whichBumper){
			switch (whichBumper){
			case ROSE1: 
				return new Bumper(new Point[]{
						new Point(35,203),	
						new Point(49,188),	
						new Point(62,191),	
						new Point(64,172),	
						new Point(45,154),	
						new Point(25,165),	
						new Point(34,174)
						
				},ROSE1);
			case ROSE2:
				return new Bumper(new Point[]{
						new Point(259,227),	
						new Point(255,203),	
						new Point(244,196),	
						new Point(253,184),	
						new Point(271,197),	
						new Point(261,204)
					},ROSE2);
			case ROSE3:
				return new Bumper(new Point[]{
						new Point(84,365),	
						new Point(92,341),	
						new Point(101,334),	
						new Point(95,323),	
						new Point(75,332),	
						new Point(84,343)
					},ROSE3);
			case SHOES:
				if (slipperStage==slipers.length){
					return null;
				}
				return new Bumper(new Point[]{
						new Point(318,54),	
						new Point(283,75),	
						new Point(253,66),	
						new Point(245,38),	
						new Point(273,2)
					},SHOES);
			case HAT_AND_PAIL:
				if (slipperStage==slipers.length){
					return null;
				}else{
				return new Bumper(new Point[]{
						new Point(2,67),	
						new Point(31,74),	
						new Point(49,100),	
						new Point(79,110),	
						new Point(110,97),	
						new Point(110,82),	
						new Point(95,76),	
						new Point(90,43),	
						new Point(103,42),	
						new Point(84,19),	
						new Point(60,8)
					},HAT_AND_PAIL);
				}
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
	    	WizardOfOz.this.finish();
	    }
	}
	
	 @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        golfView = new WizardOfOzView (this.getApplicationContext(),width,height);
        layout.addView(golfView, 0);
        golfView.setScoreUpdator(this);
        score.setPadding(10, 10, 10, 10);
        LayoutParams lp = new FrameLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
        lp.gravity=Gravity.BOTTOM|Gravity.RIGHT;
        score.setLayoutParams(lp);
    }
   
}

