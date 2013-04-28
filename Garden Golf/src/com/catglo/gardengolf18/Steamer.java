package com.catglo.gardengolf18;

import java.util.Iterator;


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

public class Steamer extends GolfActivity {
	public class SteamerView extends GolfView {
		
		class Pos {
			public Pos(int x,int y,float scale,int angle){
				this.x = x;
				this.y = y;
				this.scale = scale;
				this.angle = angle;
			}
			float scale;
			int angle;
			int x;
			int y;
		}
		
	
		
		private Bitmap sideView;
		private AnimationAction boatAnimation;
		private int stage=0;
		private Bitmap anchor;
		private Bitmap topView;
		private Bitmap[] dolphin;
		private Bitmap splash;
		final static int INTERVAL=50;
		private static final long DOLPHIN_INTERVAL = 120;
		
		boolean gameModeSideView=true;
		private Matrix anchorMatrix;
		private Matrix sailingMatrix;
		private Matrix dolphinMatrix;
		private int boatStage=0;
		
		Coordinate boatStartPoint = new Coordinate();
		Coordinate boatDestPoint = new Coordinate();
		int src=0;
		int dest=1;
		Pos[] boatPositions = new Pos[17];
		private Matrix baseBoatMatrix;
		float dolphinStage=100;
		private AnimationAction dolphinAnimation;
		
	
		public SteamerView(Context context, int width, int height) {
			super(context, width, height);
			setBackgroundResource(R.drawable.steamer_background);
			
			sideView = loadBitmap(R.drawable.steamer_sideview);
			anchor = loadBitmap(R.drawable.steamer_anchor);
			topView = loadBitmap(R.drawable.steamer_topview);
			dolphin = new Bitmap[9];
			dolphin[0] = loadBitmap(R.drawable.steamer_dolphin1);
			dolphin[1] = loadBitmap(R.drawable.steamer_dolphin2);
			dolphin[2] = loadBitmap(R.drawable.steamer_dolphin3);
			dolphin[3] = loadBitmap(R.drawable.steamer_dolphin4);
			dolphin[4] = loadBitmap(R.drawable.steamer_dolphin5);
			dolphin[5] = loadBitmap(R.drawable.steamer_dolphin6);
			dolphin[6] = loadBitmap(R.drawable.steamer_dolphin7);
			dolphin[7] = loadBitmap(R.drawable.steamer_dolphin8);
			dolphin[8] = loadBitmap(R.drawable.steamer_dolphin9);
			
			splash = loadBitmap(R.drawable.steamer_dolphinsplash);
			anchorMatrix = new Matrix();
			
			anchorMatrix.postTranslate(-anchor.getWidth()-2, 0);//Make the center point where the anchor should pivot from
			anchorMatrix.postTranslate(270, 442); //Move to connect with chain on ship
			
			sailingMatrix = new Matrix();
			
			
			
			baseBoatMatrix = new Matrix();
			baseBoatMatrix.postTranslate(-(topView.getWidth()/2), -(topView.getHeight()/2));
		
			final float MID_BOAT_SCALE=0.95f;//0.7f;
			final float SMALL_BOAT_SCALE=0.85f;//0.65f;//0.55f;
			
			boatPositions[0]  = new Pos(214, 303, 1f, -29);
			boatPositions[1]  = new Pos(90, 152,1f,-29);
			boatPositions[2]  = new Pos(65, 80, MID_BOAT_SCALE, 32);
			boatPositions[3]  = new Pos(182,36, SMALL_BOAT_SCALE, 95);
			boatPositions[4]  = new Pos(258,89,SMALL_BOAT_SCALE,95+65);
			boatPositions[5]  = new Pos(244,161,SMALL_BOAT_SCALE,95+65+50);
			boatPositions[6]  = new Pos(168,224,SMALL_BOAT_SCALE,95+65+50+26);
		    boatPositions[7]  = new Pos(98,275,SMALL_BOAT_SCALE,95+65+50+26-14);
		    boatPositions[8]  = new Pos(92,385,SMALL_BOAT_SCALE,95+65+50+26-14-71);
		    boatPositions[9]  = new Pos(150,441,SMALL_BOAT_SCALE,95+65+50+26-14-71-60);
	        boatPositions[10] = new Pos(247,413,SMALL_BOAT_SCALE,95+65+50+26-14-71-60-50);
			boatPositions[11] = new Pos(277,344,SMALL_BOAT_SCALE,95+65+50+26-14-71-60-50-49);
		    boatPositions[12] = new Pos(212,257,SMALL_BOAT_SCALE,95+65+50+26-14-71-60-50-49-50);
			boatPositions[13] = new Pos(113,197,SMALL_BOAT_SCALE,95+65+50+26-14-71-60-50-49-50-3);
		    boatPositions[14] = new Pos(75,118,SMALL_BOAT_SCALE,0);
			boatPositions[15] = new Pos(112,52,SMALL_BOAT_SCALE,60);
			boatPositions[16]  = new Pos(182,36, SMALL_BOAT_SCALE, 95);
			
			
			sailingMatrix.set(baseBoatMatrix);
			sailingMatrix.postRotate(boatPositions[0].angle);
			sailingMatrix.postScale(boatPositions[0].scale,boatPositions[0].scale);
			sailingMatrix.postTranslate(boatPositions[0].x, boatPositions[0].y);
		
			dolphinAnimation = new AnimationAction(System.currentTimeMillis()+DOLPHIN_INTERVAL,new Runnable(){public void run(){synchronized(SteamerView.this){
				dolphinStage++;
				if (dolphinStage>=dolphin.length){
					dolphinStage=-1;
					removeAction(dolphinAnimation);
				}
				checkIfBumperMovedOverBall(DOLPHIN);
				
				dolphinAnimation.actionTime=System.currentTimeMillis()+DOLPHIN_INTERVAL;
				postInvalidate();
			}}});
			
			boatAnimation = new AnimationAction(System.currentTimeMillis()+INTERVAL,new Runnable(){public void run(){synchronized(SteamerView.this){
				stage++;
				float invRatio = (float)stage/10f;
				float ratio = 1f-invRatio;
		
				int x = (int)(((float)boatPositions[src].x*ratio)+((float)boatPositions[dest].x*invRatio));
				int y = (int)(((float)boatPositions[src].y*ratio)+((float)boatPositions[dest].y*invRatio));
				int angle = (int)(((float)boatPositions[src].angle*ratio)+((float)boatPositions[dest].angle*invRatio));
				float scale = (((float)boatPositions[src].scale*ratio)+((float)boatPositions[dest].scale*invRatio));
				
				sailingMatrix.set(baseBoatMatrix);
				sailingMatrix.postRotate(angle);
				sailingMatrix.postScale(scale, scale);
				sailingMatrix.postTranslate(x, y);
				checkIfBumperMovedOverBall(MOVING_BOAT);
			
				if (src==1 || src==4 || src==7 || src==10 || src==13 ){
					addAction(dolphinAnimation);
				}
				
				
				if (stage>=10){
					stage=0;
					src++;
					dest++;
					if (dest>16) {
						src=3;
						dest=4;
					}
				}
				
				boatAnimation.actionTime=System.currentTimeMillis()+INTERVAL;
				postInvalidate();
			}}});
		}
		
		protected void checkIfBumperMovedOverBall(int whichBumper) {
			Bumper b = getBumper(whichBumper);
			int tries=0;
			int ballLineLength=7;
			if (b.contains(ball)){	
				Log.i("BOAT","BOAT hit ball!");
				boolean movedBall=false;
				while (movedBall==false && tries < 15){
					tries++;//endless loop brekout
					StreightLine ballLine;// = new StreightLine(ball.x-ballLineLength,ball.y-ballLineLength,ball.x+ballLineLength,ball.y+ballLineLength);
					StreightLine ballLine2;// = new StreightLine(ball.x+ballLineLength,ball.y+ballLineLength,ball.x-ballLineLength,ball.y-ballLineLength);
				
					if (whichBumper == DOLPHIN){
						ballLine = new StreightLine(ball.x,ball.y,ball.x+ballLineLength,ball.y+ballLineLength);
						ballLine2 = new StreightLine(ball.x+ballLineLength,ball.y+ballLineLength,ball.x,ball.y);
					} else {
						ballLine = new StreightLine(ball.x-ballLineLength,ball.y-ballLineLength,ball.x+ballLineLength,ball.y+ballLineLength);
						ballLine2 = new StreightLine(ball.x+ballLineLength,ball.y+ballLineLength,ball.x-ballLineLength,ball.y-ballLineLength);
					}
					Iterator<StreightLine> slItterator = b.iterate();
					while (slItterator.hasNext()){
						StreightLine sl = slItterator.next();
						Coordinate inrersectionPoint = sl.intersects(ballLine);
						if (inrersectionPoint==null){
							inrersectionPoint=sl.intersects(ballLine2);
						}
						if (inrersectionPoint!=null){
							Log.i("BOAT","BOAT found intersection point!");
							
							Vector v = sl.getNormal();
							
							Coordinate ballAlt1 = new Coordinate(ball);
							Coordinate ballAlt2 = new Coordinate(ball);
							while (b.contains(ballAlt1) && b.contains(ballAlt2)){
								ballAlt1.x+=v.x;
								ballAlt1.y+=v.y;
								ballAlt2.x-=v.x;
								ballAlt2.y-=v.y;
							}
							if (!b.contains(ballAlt1)){
								ballStart.x = ball.x = ballAlt1.x;
								ballStart.y = ball.y = ballAlt1.y;
								movedBall=true;
							} else if (!b.contains(ballAlt2)){
								ballStart.x = ball.x = ballAlt2.x;
								ballStart.y = ball.y = ballAlt2.y;	
								movedBall=true;
							} else {
								throw new IllegalStateException();
							}
						}
					}
					ballLineLength*=2;
				}
				onBumperMovedBall(whichBumper);
			}
		}
	
		protected void drawBackground(Canvas canvas){
			super.drawBackground(canvas);
			if (gameModeSideView){
				canvas.drawBitmap(sideView, 3, 255, null);
				canvas.drawBitmap(anchor, anchorMatrix, null);
			} else {
				canvas.drawBitmap(topView, sailingMatrix, null);
				if (dolphinStage>=0&&dolphinStage<dolphin.length){
					canvas.drawBitmap(dolphin[(int)dolphinStage], 130,55, null);
				}	
			}
		}
		
		protected Coordinate ballStartPoint(){
			return new Coordinate(40,440);
		}
		protected Coordinate ballHolePoint(){
			return new Coordinate(291,25);
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
	
		static final int BOAT=1;
		static final int ANCHOR=2;
		static final int MOVING_BOAT=3;
		static final int DOLPHIN=4;
			
		@Override
		protected void onObjectBounce(int id, float force) {
			if (id == ANCHOR){
				gameModeSideView=false;//TODO: animate anchor being pulled up and steamer sailing off the screen
				addAction(boatAnimation);
			}
		}
		
		Bumper[] frames = {
		    new Bumper(new Point[]{new Point(4+130,34+55),new Point(22+130,52+55),new Point(54+130,46+55),new Point(28+130,27+55),},DOLPHIN),
			new Bumper(new Point[]{new Point(16+130,42+55),new Point(24+130,47+55),new Point(88+130,54+55),new Point(77+130,30+55),new Point(40+130,18+55),new Point(18+130,25+55),},DOLPHIN),
		    new Bumper(new Point[]{new Point(20+130,34+55),new Point(35+130,43+55),new Point(58+130,36+55),new Point(85+130,49+55),new Point(111+130,77+55),new Point(113+130,47+55),new Point(87+130,22+55),new Point(51+130,23+55),new Point(33+130,27+55)},DOLPHIN),
			new Bumper(new Point[]{new Point(48+130,27+55),new Point(57+130,40+55),new Point(85+130,40+55),new Point(108+130,62+55),new Point(124+130,92+55),new Point(136+130,73+55),new Point(114+130,35+55),new Point(78+130,29+55)},DOLPHIN),
		  	new Bumper(new Point[]{new Point( 85+130,34+55),new Point(88+130,51+55),new Point(109+130,58+55),new Point(125+130,89+55),new Point(129+130,125+55),new Point(148+130,102+55),new Point(143+130,63+55),new Point(110+130,40+55)},DOLPHIN),
	        new Bumper(new Point[]{new Point(115+130,69+55),new Point(122+130,43+55),new Point(161+130,100+55),new Point(151+130,137+55),new Point(124+130,149+55),new Point(136+130,117+55),new Point(132+130,85+55)},DOLPHIN),
			new Bumper(new Point[]{new Point(126+130,149+55),new Point(145+130,166+55),new Point(162+130,142+55),new Point(155+130,109+55),new Point(151+130,85+55),new Point(136+130,100+55),new Point(143+130,125+55)},DOLPHIN),
		    new Bumper(new Point[]{new Point(136+130,159+55),new Point(151+130,169+55),new Point(156+130,136+55),new Point(158+130,119+55),new Point(137+130,128+55),new Point(142+130,144+55)},DOLPHIN),
	        new Bumper(new Point[]{new Point(140+130,163+55),new Point(161+130,174+55),new Point(197+130,163+55),new Point(167+130,142+55),new Point(136+130,155+55)},DOLPHIN)
		};
			
		
		Bumper getBumper(int whichBumper){
			switch (whichBumper){
			case BOAT:
				if (gameModeSideView) {
					return new Bumper(new Point[]{
					new Point(0,407),	
					new Point(263,410),	
					new Point(263,443),	
					new Point(250,449),	
					new Point(258,456),	
					new Point(270,443),	
					new Point(271,387),	
					new Point(260,375),	
					new Point(259,342),	
					new Point(197,280),	
					new Point(0,362)
					},BOAT);
				} else {
					return new Bumper(new Point[]{new Point(0,0)},BOAT);	
				}
			case ANCHOR: 
				if (gameModeSideView) {
					return new Bumper(new Point[]{
					new Point(260,446),
					new Point(242,463),
					new Point(234,447),
					new Point(235,459),
					new Point(254,477),
					new Point(263,477),
					new Point(262,473),
					new Point(247,467),
					new Point(262,449)
					},ANCHOR);
				} else {
					return new Bumper(new Point[]{new Point(0,0)},ANCHOR);
				}
			case MOVING_BOAT:
				if (gameModeSideView) {
					return new Bumper(new Point[]{new Point(0,0)},MOVING_BOAT);
				} else {
					Bumper b = new Bumper(new Point[] {
					new Point(4,180),
					new Point(12,96),
					new Point(28,23),
					new Point(23,13),
					new Point(32,5),
					new Point(43,15),
					new Point(38,24),
					new Point(49,91),
					new Point(55,184),
					new Point(50,192),
					new Point(13,191)
					},MOVING_BOAT);
					b.applyMatrix(sailingMatrix);
					return b;
				}
			case DOLPHIN:
				if (!(dolphinStage>=0&&dolphinStage<dolphin.length)||gameModeSideView) {
					return dummyBumper;
				}
				return frames[(int)dolphinStage];
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
	    	Steamer.this.finish();
	    }
	}

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        golfView = new SteamerView (this.getApplicationContext(),width,height);
        layout.addView(golfView, 0);
        golfView.setScoreUpdator(this);
        score.setPadding(10, 10, 10, 10);
        LayoutParams lp = new FrameLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
        lp.gravity=Gravity.TOP|Gravity.LEFT;
        score.setLayoutParams(lp);
    }
   
}

	