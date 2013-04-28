package com.catglo.gardengolf18;

import java.util.LinkedList;

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

//TODO: check if bumpers moved over ball

public class FishPond extends GolfActivity {
   public class FishPondView extends GolfView {
	   static final int GOLD_FISH_LEFT=90;
	   static final int GOLD_FISH_RIGHT=150;
	   
		private int goldFishStage=0;
		private Bitmap[] bubble;
		private Bitmap[] goldFish;
		static final int BUBBLE_INTERVAL = 1000;
		static final int WATER_INTERVAL = 200;
		static final int GOLDFISH_MOVEMENT_INTERVAL=100;
		int bubbleStage=0;
		AnimationAction bubbles;
		AnimationAction goldfishMovement;
		Coordinate goldfishPoint = new Coordinate(GOLD_FISH_LEFT,314);
		int goldFishSwimDirection=-10;
		private Bitmap[] water;
		private AnimationAction waterMotion;
		int waterStage=0;
		private Zone waterFountainZone;
		private Velocity top;
		private Velocity middle;
		private Velocity bottom;
		private Velocity tip;
		private Velocity swirl;
		private Matrix fishMatrix;
		
		public FishPondView(Context context, int width, int height) {
			super(context, width, height);
			
			setBackgroundResource(R.drawable.fishpond_background);
			goldFish=new Bitmap[12];
			goldFish[0] = loadBitmap(R.drawable.orange_fish_tail_frame1);
			goldFish[1] = loadBitmap(R.drawable.orange_fish_tail_frame2);
			goldFish[2] = loadBitmap(R.drawable.orange_fish_tail_frame3);
			goldFish[3] = loadBitmap(R.drawable.orange_fish_tail_frame4);
			goldFish[4] = loadBitmap(R.drawable.orange_fish_tail_frame5);
			goldFish[5] = loadBitmap(R.drawable.orange_fish_tail_frame6);
			goldFish[6] = loadBitmap(R.drawable.orange_fish_tail_frame7);
			goldFish[7] = loadBitmap(R.drawable.orange_fish_tail_frame8);
			goldFish[8] = loadBitmap(R.drawable.orange_fish_tail_frame9);
			goldFish[9] = loadBitmap(R.drawable.orange_fish_tail_frame10);
			goldFish[10] = loadBitmap(R.drawable.orange_fish_tail_frame11);
			goldFish[11] = loadBitmap(R.drawable.orange_fish_tail_frame12);
					
			bubble = new Bitmap[4];
			bubble[0] = loadBitmap(R.drawable.fishpond_whitefish_1bubble);
			bubble[1] = loadBitmap(R.drawable.fishpond_whitefish_2bubbles);
			bubble[2] = loadBitmap(R.drawable.fishpond_whitefish_3bubbles);
			bubble[3] = loadBitmap(R.drawable.fishpond_whitefish_4bubbles);
			water = new Bitmap[6];
			water[0]  = loadBitmap(R.drawable.water1);
			water[1]  = loadBitmap(R.drawable.water2);
			water[2]  = loadBitmap(R.drawable.water3);
			water[3]  = loadBitmap(R.drawable.water4);
			water[4]  = loadBitmap(R.drawable.water5);
			water[5]  = loadBitmap(R.drawable.water6);
			 
			bubbles = new AnimationAction(System.currentTimeMillis()+BUBBLE_INTERVAL,new Runnable(){public void run(){
				synchronized (FishPondView.this){bubbleStage++;
					checkIfBumperMovedOverBall(2);
					postInvalidate();
					if (bubbleStage==bubble.length){
						bubbleStage=0;
					}
					bubbles.actionTime=System.currentTimeMillis()+BUBBLE_INTERVAL;
				}
			}});
			addAction(bubbles);
			
			waterMotion = new AnimationAction(System.currentTimeMillis()+WATER_INTERVAL,new Runnable(){public void run(){
				synchronized (FishPondView.this){
					waterStage++;
					postInvalidate();
					if (waterStage==water.length){
						waterStage=0;
					}
					waterMotion.actionTime=System.currentTimeMillis()+WATER_INTERVAL;
				}
			}});
			addAction(waterMotion);
			
			fishMatrix = new Matrix();
			goldfishMovement = new AnimationAction(System.currentTimeMillis()+GOLDFISH_MOVEMENT_INTERVAL+1000,new Runnable(){public void run(){
				synchronized (FishPondView.this){
					++goldFishStage;
					
					if (goldFishStage >= goldFish.length){
						goldFishStage=0;
					}
					
					goldfishPoint.x += goldFishSwimDirection;
					checkIfFishMovedOverBall();
					if (goldfishPoint.x > GOLD_FISH_RIGHT){
						if (justPushed){
							ballStart.x=ball.x+=(goldFishSwimDirection*4);
						}
						goldFishSwimDirection=-1;
					}
					if (goldfishPoint.x < GOLD_FISH_LEFT){
						if (justPushed){
							ballStart.x=ball.x+=(goldFishSwimDirection*4);
						}
						goldFishSwimDirection=1;
					}
					fishMatrix.setTranslate(goldfishPoint.x, goldfishPoint.y);
					
					postInvalidate();
					goldfishMovement.actionTime=System.currentTimeMillis()+GOLDFISH_MOVEMENT_INTERVAL;
				}
			}});
			addAction(goldfishMovement);
			
			final float TOTAL_FOUNTAIN_FORCE = 0.01f;//0.0009f;
			top = new Velocity(new Point(30,58),new Point(121,162));
			top.setTotal(TOTAL_FOUNTAIN_FORCE);
			middle = new Velocity(new Point(133,178),new Point(142,288));
			middle.setTotal(TOTAL_FOUNTAIN_FORCE);
			bottom = new Velocity(new Point(142,315),new Point(25,354));
			bottom.setTotal(TOTAL_FOUNTAIN_FORCE);
			swirl = new Velocity(new Point(142,315),new Point(30,372));
			swirl.setTotal(TOTAL_FOUNTAIN_FORCE);
			tip = new Velocity(new Point(210,380),new Point(50,378));
			tip.setTotal(TOTAL_FOUNTAIN_FORCE);
			
			
			waterFountainZone = new Zone(new Point[]{		
					new Point(24,55),
					new Point(96,113),
					new Point(149,182),
					new Point(168,233),
					new Point(171,273),
					new Point(163,304),
					new Point(145,327),
					new Point(116,342),
					new Point(66,347),
					new Point(45,355),
					new Point(36,369),
					new Point(41,384),
					new Point(62,385),
					new Point(45,391),
					new Point(24,383),
					new Point(15,362),
					new Point(20,340),
					new Point(51,322),
					new Point(85,313),
					new Point(107,298),
					new Point(121,268),
					new Point(122,229),
					new Point(114,183),
					new Point(97,147),
					new Point(72,111)
					},WATTER_FOUNTAIN,new VelocityAdjustor(){public Velocity adjust(Velocity velocity, int timeDelta) {
						synchronized (FishPondView.this){//Decelerate due to friction
					    	float total = velocity.total() + (DEFAULT_FRICTION*8)*timeDelta;
							if (total<0)
								total=0;
							velocity.setTotal(total);
					    	
							Velocity rampVelocity = top;
							if (ball.y > 176){
								rampVelocity=middle;
							}
							if (ball.y > 290){
								rampVelocity=bottom;
							}
							if (ball.y > 332){
								rampVelocity=swirl;
							}
							if (ball.y > 364){
								rampVelocity=tip;
							}
					    	
					    	return velocity.accelerate(rampVelocity,timeDelta);
						}
					}},true);
		}
		
		boolean justPushed=false;
		protected void checkIfFishMovedOverBall() {
			//bottom fish
			Bumper b = getBumper(3);
			if (b.contains(ball)){		
				while (b.contains(ball)){
					ball.x+=goldFishSwimDirection;
				}
				justPushed=true;
				
				ballStart.x = ball.x;
				ballStart.y = ball.y;
				
				onBumperMovedBall(3);
			}else {
				justPushed=false;
			}
			
			//top fish bubbles
			b = getBumper(2);
			if (b.contains(ball)){		
				while (b.contains(ball)){
					ball.y--;
				}
				ballStart.x = ball.x;
				ballStart.y = ball.y;
				
				onBumperMovedBall(2);
			}
		}
		
		synchronized protected void drawBackground(Canvas canvas){
			canvas.drawBitmap(bubble[bubbleStage], 209+60,38+39, null);
			canvas.drawBitmap(goldFish[goldFishStage], goldfishPoint.x,goldfishPoint.y, null);
			canvas.drawBitmap(water[waterStage],16,56,null);
		}
		
		
		protected Coordinate ballStartPoint(){
			return new Coordinate(82,440);
		}
		protected Coordinate ballHolePoint(){
			return new Coordinate(274,40);
		}
		protected Polygon getGreenPolygon(){
			Polygon green = new Polygon(new Point[] {
				new Point(2,478),
	            new Point(2,71),
	            new Point(59,2),
	            new Point(318,2),
	            new Point(318,478)
			});
			return green;
		}
		
		
	
		synchronized Bumper getBumper(int whichBumper){
			switch (whichBumper){
			case 1: return new Bumper(new Point[]{
				new Point(230,469),	//The green plant at the bottom of the screen
				new Point(133,469),	
				new Point(172,448),	
				new Point(151,420),	
				new Point(179,428),	
				new Point(168,386),	
				new Point(184,389),	
				new Point(204,418),	
				new Point(226,401),	
				new Point(218,458)
				},1);
			case 2: 
				Point lastPoint;
				switch (bubbleStage){
				case 0: lastPoint=new Point(222+60,80); break;
				case 1: lastPoint=new Point(227+60,66); break;
				case 2: lastPoint=new Point(219+60,52); break;
				default:
				case 3: lastPoint=new Point(214+60,40); break;
				}
				return new Bumper(new Point[] {
				    new Point(210+60,83+39),	//White fish
					new Point(188+60,98+39),	
					new Point(183+60,108+39),	
					new Point(156+60,100+39),	
					new Point(162+60,89+39),	
					new Point(159+60,77+39),	
					new Point(148+60,91+39),	
					new Point(130+60,92+39),	
					new Point(122+60,45+39),	
					new Point(148+60,50+39),	
					new Point(167+60,28+39),
					lastPoint
				},2);
			case 3:
				
				Bumper fish;
				if (goldFishStage==7 || goldFishStage==1){
					fish = new Bumper(new Point[] {
						new Point(9,70),
						new Point(0,49),
						new Point(12,10),
						new Point(44,10),
						new Point(56,47),
						new Point(47,70)
					},3);
				} else if (goldFishStage==2 || goldFishStage==6 || goldFishStage==8 || goldFishStage==12){
				    fish = new Bumper(new Point[] {new Point(5,68),new Point(48,69),new Point(48,47),new Point(36,3),new Point(18,3),new Point(7,51)},3);
				} else {
				    fish = new Bumper(new Point[] {new Point(6,66),new Point(29,73),new Point(49,64),new Point(25,13)},3);
				}		 
				fish.applyMatrix(fishMatrix);
				return fish;
			}
			return null;
		}
	
		
		static final int WATTER_FOUNTAIN = 1;
		
	
		Zone getZone(int whichZone){
			switch (whichZone){
			case WATTER_FOUNTAIN: return waterFountainZone;
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
	    	FishPond.this.finish();
	    }
	}
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       	golfView = new FishPondView (this.getApplicationContext(),width,height);
        layout.addView(golfView, 0);
        golfView.setScoreUpdator(this);
        
        score.setPadding(10, 10, 10, 10);
        LayoutParams lp = new FrameLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
        lp.gravity=Gravity.BOTTOM|Gravity.RIGHT;
        score.setLayoutParams(lp);
        
    }
}

