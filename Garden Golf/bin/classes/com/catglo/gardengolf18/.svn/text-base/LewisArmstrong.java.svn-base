package com.catglo.gardengolf18;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;

public class LewisArmstrong extends GolfActivity {
  	public class LewisArmstrongView extends GolfView {
		
		private Bitmap[] lewis;
		private AnimationAction lewisAnimation;
		private Matrix lewisMatrix=new Matrix();
		private int lewisStage=0;
		private int lewisCycle=0;
		private boolean lewisMirror=false;
		static final int LEWIS_INTERVAL = 100;
		
		
		public LewisArmstrongView(Context context, int width, int height) {
			super(context, width, height);
			setBackgroundResource(R.drawable.louis_background);
			//showDebug=true;
			
			lewis = new Bitmap[3];
			lewis[0] = loadBitmap(R.drawable.louis_frame1);
			lewis[1] = loadBitmap(R.drawable.louis_frame2);
			lewis[2] = loadBitmap(R.drawable.louis_frame3);
			final int lewisWidth = lewis[0].getWidth();
			 
			lewisAnimation = new AnimationAction(System.currentTimeMillis()+LEWIS_INTERVAL,new Runnable(){public void run(){
				lewisMatrix.reset();
				//if (lewisMirror) {
				//	lewisMatrix.preScale(-1, 1);
				//	lewisMatrix.postTranslate(lewisWidth+50, 0);
				//}
				lewisMatrix.postTranslate(30, 165);
				lewisStage++;
				if (lewisStage==lewis.length){
					lewisStage=0;
					lewisCycle++;
					if (lewisCycle==10){
						lewisCycle=0;
						//lewisMirror=!lewisMirror;
						checkIfBumperMovedOverBall(1);
					}
				}
				lewisAnimation.actionTime=System.currentTimeMillis()+LEWIS_INTERVAL;
				postInvalidate();
			}});
			addAction(lewisAnimation);
		}
		
		@Override
		void onBumperMovedBall(int whichBumper){
			super.onBumperMovedBall(whichBumper);
			if (lewisMirror){
				ballVelocityAdd(2,0);	
			}else {	
				ballVelocityAdd(-2,0);
			}
		}
	
		protected void drawBackground(Canvas canvas){
			canvas.drawBitmap(lewis[lewisStage], lewisMatrix, null);
		}
		
		
		protected Coordinate ballStartPoint(){
			return new Coordinate(300,458);
		}
		protected Coordinate ballHolePoint(){
			return new Coordinate(110,404);
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
	
		Bumper getBumper(int whichBumper){
			switch (whichBumper){
			case 1: 
				if (lewisMirror==false){
					return new Bumper(new Point[]{
							new Point(28+68,480),	
							new Point(28+114,398),	
							new Point(28+66,356),	
							new Point(28+50,257),	
							new Point(28+63,235),	
							new Point(28+4,214),	
							new Point(28+39,157),	
							new Point(28+183,323),	
							new Point(28+219,294),	
							new Point(28+220,295),	
							new Point(28+256,306),	
							new Point(28+261,353),	
							new Point(28+231,376),	
							new Point(28+253,439),	
							new Point(28+221,480)
					},1);
				} else {
					return new Bumper(new Point[]{
							new Point(96,480),	
							new Point(63,436),	
							new Point(77,398),	
							new Point(90,395),	
							new Point(84,374),	
							new Point(56,352),	
							new Point(55,326),	
							new Point(71,298),	
							new Point(105,299),	
							new Point(129,323),	
							new Point(264,191),	
							new Point(260,168),	
							new Point(285,167),	
							new Point(301,220),	
							new Point(278,216),	
							new Point(259,238),	
							new Point(267,252),	
							new Point(255,272),	
							new Point(253,359),	
							new Point(202,402),	
							new Point(250,480)	
							},1);
				}
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
	    	LewisArmstrong.this.finish();
	    }	
	}
	
	/** Called when the activity is first created. */ 
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        golfView = new LewisArmstrongView (this.getApplicationContext(),width,height);
        layout.addView(golfView, 0);
        golfView.setScoreUpdator(this);
        score.setPadding(10, 10, 10, 10);
        LayoutParams lp = new FrameLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
        lp.gravity=Gravity.TOP|Gravity.LEFT;
        score.setLayoutParams(lp);
    }  
	
}

