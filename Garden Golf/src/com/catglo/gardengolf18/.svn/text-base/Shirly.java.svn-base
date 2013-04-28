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
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;

public class Shirly extends GolfActivity {
  	public class ShirlyView extends GolfView {
	
	
		private static final long LEAF_INTERVAL = 50;
		private static final long DANCE_INTERVAL = 150;
		private Bitmap[] dance;
		private Bitmap standing;
		private AnimationAction leafAnimation;
		private int danceStage;
		private int danceStep;
		private Bitmap leaf1;
		private Bitmap leaf2;
		private Bitmap leaf3;
		private Bitmap leaf4;
		private Bitmap leaf5;
		private int leaf1Y;
		private int leaf2Y;
		private int leaf3Y;
		private int leaf4Y;
		private int leaf5Y;
		private AnimationAction danceAnimation;
		private Matrix[] leafMatrix;
		
		public ShirlyView(Context context, int width, int height) {
			super(context, width, height);
			setBackgroundResource(R.drawable.shirly_background);
			
			dance = new Bitmap[10];
			dance[0] = loadBitmap(R.drawable.dance_anim1);
			dance[1] = loadBitmap(R.drawable.dance_anim2);
			dance[2] = loadBitmap(R.drawable.dance_anim3);
			dance[3] = loadBitmap(R.drawable.dance_anim4);
			dance[4] = loadBitmap(R.drawable.dance_anim5);
			dance[5] = loadBitmap(R.drawable.dance_anim6);
			dance[6] = loadBitmap(R.drawable.dance_anim7);
			dance[7] = loadBitmap(R.drawable.dance_anim8);
			dance[8] = loadBitmap(R.drawable.dance_anim9);
			dance[9] = loadBitmap(R.drawable.dance_anim10);
			
			
			
			leaf1 = loadBitmap(R.drawable.shirly_leaf1);	
			leaf2 = loadBitmap(R.drawable.shirly_leaf2);	
			leaf3 = loadBitmap(R.drawable.shirly_leaf3);	
			leaf4 = loadBitmap(R.drawable.shirly_leaf4);	
			leaf5 = loadBitmap(R.drawable.shirly_leaf5);	
			
			leaf1Y = 0;
			leaf2Y = 240;
			leaf3Y = 140;
			leaf4Y = 320;
			leaf5Y = 200;
			
			leafMatrix = new Matrix[5];
			leafMatrix[0] = new Matrix();
			leafMatrix[1] = new Matrix();
			leafMatrix[2] = new Matrix();
			leafMatrix[3] = new Matrix();
			leafMatrix[4] = new Matrix();
			
			
			
			leafAnimation = new AnimationAction(System.currentTimeMillis()+LEAF_INTERVAL,new Runnable(){public void run(){synchronized(ShirlyView.this){
				leaf1Y+=2;
				leaf2Y+=2;
				leaf3Y+=2;
				leaf4Y+=2;
				leaf5Y+=2;
				if (leaf1Y>480)
					leaf1Y=0;
				if (leaf2Y>480)
					leaf2Y=0;
				if (leaf3Y>480)
					leaf3Y=0;
				if (leaf4Y>480)
					leaf4Y=0;
				if (leaf5Y>480)
					leaf5Y=0;
				leafMatrix[0].setTranslate(37, leaf1Y);
				leafMatrix[1].setTranslate(37+8, leaf2Y);
				leafMatrix[2].setTranslate(37+46, leaf3Y);
				leafMatrix[3].setTranslate(37+170, leaf4Y);
				leafMatrix[4].setTranslate(37+200, leaf5Y);
				for (int i = 0; i < leafMatrix.length;i++){
					leaves[i].applyMatrix(leafMatrix[i]);
					checkIfBumperMovedOverBall(leaves[i].id);
				}
				leafAnimation.actionTime=System.currentTimeMillis()+LEAF_INTERVAL;	
				postInvalidate();
			}}}); 
			addAction(leafAnimation);	
			
			
			danceAnimation = new AnimationAction(System.currentTimeMillis()+DANCE_INTERVAL,new Runnable(){public void run(){	synchronized(ShirlyView.this){
				++danceStage;
				if (danceStage>=dance.length){
					danceStage=0;
					danceStep++;
					if (danceStep>=3){
						removeAction(danceAnimation);
						Intent data = new Intent(getApplicationContext(),GardenGolf.class);
				    	data.putExtra("Score", numberOfSwings);
				    	data.putExtra("green", greenNumber);
				    	startActivity(data);
						Shirly.this.finish();
					}
				}
				danceAnimation.actionTime=System.currentTimeMillis()+DANCE_INTERVAL;
				postInvalidate();
			}}});
			
			
		}
	
		/*protected void checkIfBumperMovedOverBall(int whichBumper) {
			Bumper b = getBumper(whichBumper);
			
			if (b.contains(ball)){		
				while (b.contains(ball) && ball.y < 470){
					ball.y++;
				}
				ballStart.x = ball.x;
				ballStart.y = ball.y;
				onBumperMovedBall(whichBumper);
			}
		}*/
		
		protected void drawBackground(Canvas canvas){
			super.drawSurface(canvas);
			canvas.drawBitmap(leaf1, 37  , leaf1Y, null);
			canvas.drawBitmap(leaf2, 37+8  , leaf2Y, null);
			canvas.drawBitmap(leaf3, 37+46 , leaf3Y, null);
			canvas.drawBitmap(leaf4, 37+170, leaf4Y, null);
			canvas.drawBitmap(leaf5, 37+200, leaf5Y, null);
				
			if (drawDancer)
				canvas.drawBitmap(dance[danceStage], 45, 120, null);
		}	
		
		protected Coordinate ballStartPoint(){
			return new Coordinate(40,440);
		}
		protected Coordinate ballHolePoint(){
			return new Coordinate(113,94);
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
		
		static final int TOP_LEAVES = 1;
		static final int LEAVE1 = 2;
		static final int LEAVE2 = 3;
		static final int LEAVE3 = 4;
		static final int LEAVE4 = 5;
		static final int LEAVE5 = 6;
		Bumper[] leaves = new Bumper[]{
			new Bumper(new Point[]{new Point(0,39),new Point(13,32),new Point(15,33),new Point(21,25),new Point(30,21),new Point(25,11),new Point(25,-0),new Point(15,4),new Point(11,2),new Point(8,11),new Point(-0,14),new Point(4,23),new Point(0,29)},LEAVE1),
			new Bumper(new Point[]{new Point(2,24),new Point(17,24),new Point(31,13),new Point(18,7),new Point(7,-0),new Point(3,14),new Point(0,14)},LEAVE2),
			new Bumper(new Point[]{new Point(0,16),new Point(12,23),new Point(36,23),new Point(49,7),new Point(37,0),new Point(13,0),new Point()},LEAVE3),
			new Bumper(new Point[]{new Point(-0,11),new Point(20,26),new Point(27,15),new Point(32,16),new Point(31,2),new Point(9,2)},LEAVE4),
			new Bumper(new Point[]{new Point(0,3),new Point(5,12),new Point(2,15),new Point(19,28),new Point(40,28),new Point(38,15),new Point(27,6),new Point(27,0),new Point(3,0)},LEAVE2)
		};
		
		Bumper[] bumpers = new Bumper[]{null,
	        new Bumper(new Point[]{new Point(0,45),new Point(20,52),new Point(29,66),new Point(52,105),new Point(71,87),new Point(83,91),new Point(84,68),new Point(94,73),new Point(109,53),new Point(124,53),new Point(125,65),new Point(150,103),new Point(169,89),new Point(183,93),new Point(182,71),new Point(194,72),new Point(213,41),new Point(225,55),new Point(248,91),new Point(273,81),new Point(283,63),new Point(292,65),new Point(306,36),new Point(321,35)},TOP_LEAVES),
	        leaves[0],
	        leaves[1],
	        leaves[2],
	        leaves[3],
	        leaves[4]
		};

		Bumper getBumper(int whichBumper){
			if (whichBumper >= bumpers.length){
				return null;
			}
			return bumpers[whichBumper];
		}
		
		boolean drawDancer=false;
		
		@Override
	    void downTheHole(){
			removeAction(tossing);
	    	addAction(danceAnimation);
	    	drawDancer=true;
	    }
	}
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      	golfView = new ShirlyView (this.getApplicationContext(),width,height);
        layout.addView(golfView, 0);
        golfView.setScoreUpdator(this);
        score.setPadding(10, 10, 10, 10);
        LayoutParams lp = new FrameLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
        lp.gravity=Gravity.BOTTOM|Gravity.RIGHT;
        score.setLayoutParams(lp);
    }
    
}

