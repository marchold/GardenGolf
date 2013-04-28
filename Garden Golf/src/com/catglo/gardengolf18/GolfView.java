package com.catglo.gardengolf18;

import java.util.Iterator;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MotionEvent;


abstract public class GolfView extends DrawingView {
	boolean drawBall=true;
	Coordinate ball = new Coordinate();
	Coordinate ballStart = new Coordinate();
	int numberOfSwings;
	protected Velocity velocity;
	private Matrix ballMatrix;
	static final int CONTROLER_FLING=1;
	static final int CONTROLER_AIM=2;
	static final int CONTROLER_MOTION=3;
	static int ControlerMode = CONTROLER_MOTION;
	
	static final float DEFAULT_DAMP=0.95f;
	static final int TOSS_INTERVAL=30;
	private long timeStartTossStage;
	private Bitmap golfBall;

	final Bumper dummyBumper = new Bumper(new Point[]{new Point(0,0)},-1);
	private Zone currentZone;
	private Zone zoneNoZone;
	
	/***********************************************************************
	 * 
	 * Constructor, init, pause and resume
	 * 
	 * View lifecycle
	 * 
	 */
	static final float DEFAULT_FRICTION = -0.0006f;
	public GolfView(Context context, int width, int height) {
		super(context, width, height);
		resetGame();
		
		 SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
         String s = sharedPreferences.getString("gameMode", "fling");
         
         if (s.compareTo("fling")==0){
        	 ControlerMode=CONTROLER_FLING;
         } else 
         if (s.compareTo("motion")==0){
        	 ControlerMode=CONTROLER_MOTION;
         } else {	 
        	 ControlerMode=CONTROLER_AIM;
         }
		
		golfBall = loadBitmap(R.drawable.golf_ball);
		
		ballMatrix = new Matrix();
		ballMatrix.postTranslate(-golfBall.getWidth()/2, -golfBall.getHeight()/2);	
		
		zoneNoZone = new Zone(new Point[]{},0,new VelocityAdjustor(){public Velocity adjust(Velocity velocity, int delta) {
			//v = v0*a
			//v = v0 + a*delta
			//velocity.setTotal(velocity.total()*DEFAULT_DAMP);
			float total = velocity.total() + DEFAULT_FRICTION*delta;
			if (total<0)
				total=0;
			velocity.setTotal(total);
			return velocity;
		}},false);
		currentZone = zoneNoZone;
	}
	
	void resetGame(){
		drawBall=true;
		velocity=new Velocity(0,0);
		if (ballStartPoint()!=null){
			ball.x = ballStartPoint().x;
			ball.y = ballStartPoint().y;
		}
	}
	
	protected void resume(){
		super.resume();
	}

	protected void pause(){
		super.pause();
	}
	
	/*****************************************************
	 * Virtual functions to define the hole
	 * 
	 */
	//TODO: make abstract
	abstract protected Coordinate ballStartPoint();
	abstract protected Coordinate ballHolePoint();
	abstract protected Polygon getGreenPolygon();
	
	/********************************************************
	 * Draw
	 * 
	 */
	private Coordinate debugPoint;
	synchronized protected void drawSurface(Canvas canvas){
		super.drawSurface(canvas);
	
		
		Matrix b = new Matrix();
		b.set(ballMatrix);
		b.postTranslate(ball.x, ball.y);
		
		if (drawBall)
			canvas.drawBitmap(golfBall, ball.x-7, ball.y-7, null);
		
		if (currentAim!=null){
			Paint arrowColor = new Paint();
			arrowColor.setStrokeWidth(3);
			arrowColor.setColor(Color.RED);
			canvas.drawLine(ball.x, ball.y, ball.x+currentAim.x, ball.y+currentAim.y, arrowColor);
			Coordinate tip = new Coordinate(ball.x+currentAim.x, ball.y+currentAim.y);
			Vector v = new Vector(tip,ball);
			v.setTotal(10);
			v.setAngle(v.getAngle()-45);
			canvas.drawLine(tip.x, tip.y, tip.x+v.x, tip.y+v.y, arrowColor);
			v.setAngle(v.getAngle()+90);
			canvas.drawLine(tip.x, tip.y, tip.x+v.x, tip.y+v.y, arrowColor);	
		}
		
		if (powerBarHeight>0){
			Paint powerBarColor = new Paint();
			powerBarColor.setColor(Color.WHITE);
			powerBarColor.setStrokeWidth(15);
			canvas.drawLine(10,480,10,480-powerBarHeight, powerBarColor);
		}
		
		if (showDebug){
			Iterator<StreightLine> i = getGreenPolygon().iterate();	
			while (i.hasNext()){
				StreightLine greenBumper = i.next();
				Paint p = new Paint();
				p.setColor(Color.GRAY);
				canvas.drawLine(greenBumper.A.x,greenBumper.A.y,greenBumper.B.x,greenBumper.B.y,p);
			}
			
			Polygon bumper = getFirstBumper();
			while (bumper!=null){
				i = bumper.iterate();
				while (i.hasNext()){
					StreightLine insideBumper = i.next();
					Paint p = new Paint();
					p.setColor(Color.GRAY);
					canvas.drawLine(insideBumper.A.x,insideBumper.A.y,insideBumper.B.x,insideBumper.B.y,p);
					
				}
				bumper=getNextBumper();
			}
			
			
			Polygon zone = getFirstZone();
			while (zone!=null){
				i = zone.iterate();
				while (i.hasNext()){
					StreightLine insideZone = i.next();
					Paint p = new Paint();
					p.setColor(Color.RED);
					canvas.drawLine(insideZone.A.x,insideZone.A.y,insideZone.B.x,insideZone.B.y,p);	
				}
				zone=getNextZone();
			}
			if (debugLine!=null){
				Paint p = new Paint();
				p.setColor(Color.RED);
				canvas.drawLine(debugLine.A.x,debugLine.A.y,debugLine.B.x,debugLine.B.y,p);
			}
			
			if (debugPoint!=null){
				Paint p = new Paint();
				p.setStrokeWidth(5);
				p.setColor(Color.YELLOW);
				canvas.drawPoint(debugPoint.x,debugPoint.y,p);
			}
			
			Paint p = new Paint();
			p.setStrokeWidth(10);
			p.setColor(Color.GREEN);
			canvas.drawPoint(ballHolePoint().x,ballHolePoint().y,p);
		}	
			
		
		
	}
	
	
	/***************************************************************
	 * Ball movement modeling
	 * 
	 */
	Coordinate findPointOutside(Polygon polygon, StreightLine line, Coordinate intersection){
		Coordinate outsidePoint = new Coordinate(intersection);
	
		if (polygon.contains(ballStart)){
			float distance = ballStart.distanceFrom(outsidePoint);
			
			outsidePoint = velocity.getPointDistanceAway(++distance,ballStart);
			Coordinate outsidePoint2 = velocity.getPointDistanceAway(++distance,ballStart);
		
			while ((line.intersects(new StreightLine(outsidePoint,outsidePoint2)))!=null){
				outsidePoint = outsidePoint2;
				outsidePoint2 = velocity.getPointDistanceAway(++distance,ballStart);
				
				
				if (distance>1000 || distance<1) {
					throw new IllegalStateException();
				}
				
			}
			return outsidePoint;
		} else {
			float distance = ballStart.distanceFrom(outsidePoint);
			while ((line.intersects(new StreightLine(ballStart,outsidePoint)))!=null ){
				distance--;
				outsidePoint = velocity.getPointDistanceAway(distance,ballStart);	
				
				if (distance>1000 || distance<-10) {
					throw new IllegalStateException();
				}
				
			}
			return outsidePoint;
		}
		
		
	}
	
	Coordinate findPointInside(Polygon polygon, StreightLine line, Coordinate intersection){
		Coordinate insidePoint = new Coordinate(intersection);
		
		//If the ball is inside the polygon we need to towards the ball 
		//otherwise we move away from the ball
		if (polygon.contains(ballStart)){
			//This is the case where the ball is traveling from inside the polygon and needs to bounce 
			//off the inside wall
			float distance = ballStart.distanceFrom(insidePoint);
			while ((line.intersects(new StreightLine(ballStart,insidePoint)))!=null){
				distance--;
				insidePoint = velocity.getPointDistanceAway(distance,ballStart);
				if (distance>1000 || distance<-10) {
					Log.i("GOLF","distance = "+distance);
					Log.i("GOLF","ballStartPoint = "+ballStart.x+" , "+ballStart.y);
					Log.i("GOLF","ballInsidePoint = "+insidePoint.x+" , "+insidePoint.y);
					
					throw new IllegalStateException();
				}
			}
			return insidePoint;
		} else {
			//This is that case where the ball is traveling from outside a polygon to inside it.
			float distance = ballStart.distanceFrom(intersection);
			
			insidePoint = velocity.getPointDistanceAway(++distance,ballStart);
			Coordinate insidePoint2 = velocity.getPointDistanceAway(++distance,ballStart);
			
			while ((line.intersects(new StreightLine(insidePoint,insidePoint2)))!=null){
				distance++;
				insidePoint = insidePoint2;//velocity.getPointDistanceAway(distance,ballStart);
				insidePoint2 = velocity.getPointDistanceAway(++distance,ballStart);
				
				if (distance>1000 || distance<1) {
					throw new IllegalStateException();
				}
			}
			return insidePoint;
		}
		
		
	}
	
	StreightLine debugLine=null;

	void downTheHole(){
		Log.i("GOLF","Down The Hole");
	}
	final float velocityScalingX = 0.0003f;
	final float velocityScalingY = 0.0003f;
			
	final AnimationAction tossing = new AnimationAction(TOSS_INTERVAL,new Runnable(){public void run() {			
		synchronized (GolfView.this) {
			/********************************************
			 * calculate ball movement in a straight line
			 */
			int delta = (int)(System.currentTimeMillis()-timeStartTossStage);
			
			float lastBallX = ball.x;
			float lastBallY = ball.y;
			ball.x = (ballStart.x + (delta * velocity.x));
			ball.y = (ballStart.y + (delta * velocity.y));
			
			//Get future position so we can determine if the ball is done getting closer to the hole
			Coordinate futureBall = new Coordinate();
			futureBall.x = (ballStart.x + ((delta+2) * velocity.x));
			futureBall.y = (ballStart.y + ((delta+2) * velocity.y));
				
			//Determine if the ball rolled in to the hole
			Coordinate hole = ballHolePoint();
			float distanceFromHole = hole.distanceFrom(ball);
			float ballSpeed = velocity.total();
			if (distanceFromHole<30 && hole.distanceFrom(futureBall)>=distanceFromHole) 
			{
			//	Log.i("GOLF","___dist="+distanceFromHole);
			//	Log.i("GOLF","___speed="+(ballSpeed/100));
				Log.i("GOLF","_________"+(distanceFromHole*distanceFromHole)+((ballSpeed*ballSpeed)));
				if ((distanceFromHole*distanceFromHole)+((ballSpeed*ballSpeed)) < 50){
					ball.x=hole.x;
					ball.y=hole.y;
					downTheHole();
					removeAction(tossing);
					return;
				}
			}
			
			Polygon green = getGreenPolygon();	
			
			//I need to see if the line between (ballX,ballY) and (ballStartX,ballStartY)
			//crosses any of the lines defined by the golf course polygon
			Coordinate A = new Coordinate(ball);
			Coordinate B = new Coordinate(ballStart);
			StreightLine ballLine = new StreightLine(A,B);
			
			/****************************************************
			 * Find the perimeter bumper which the ball 
			 * intersects with.
			 *    The path may intersect with multiple bumpers
			 *    so it is necessary to determine the first one it 
			 *    crosses
			 */
			StreightLine lineWhichIntersects = null;
			boolean intersectWithBorder=false;
			Coordinate intersectionWithBorder = null;
			Coordinate bestIntersection = new Coordinate(-1,-1);
			Iterator<StreightLine> i = green.iterate();
			while (i.hasNext()){
				StreightLine greenBumper = i.next();
				Coordinate inter = greenBumper.intersects(ballLine);
				if (inter!=null) {
					//Log.i("GOLF","ball intersects outside bumper");
					debugLine = greenBumper;
					intersectWithBorder=true;
					
					//if we intersect multiple bumpers then we must only bounce off the closest one
					float distNew = ballStart.distanceFrom(inter);
					float distOld = ballStart.distanceFrom(bestIntersection);
					
					if (distOld > distNew || bestIntersection.x==-1){
						lineWhichIntersects=greenBumper;
						bestIntersection=inter;
						intersectionWithBorder = inter;
					} 
				}
			}
			intersectionWithBorder=bestIntersection;
			
			
			/*****************************************************
			 * Determine if the ball went through an inside bumper
			 */
			Bumper bumper = getFirstBumper();
			Bumper intersectedBumper = null;
			boolean intersectWithObject=false;
			Coordinate intersectionWithObject = null;
			StreightLine lineWhichIntersectsObject = null;
			bestIntersection = new Coordinate(-1,-1);
			while (bumper!=null){
				i = bumper.iterate();
				while (i.hasNext()){
					StreightLine insideBumper = i.next();
					Coordinate inter = insideBumper.intersects(ballLine);
					if (inter!=null){
						Log.i("GOLF","ball intersects object");
						
						intersectWithObject=true;
						
						//if we intersect multiple bumpers then we must only bounce off the closeset one
						float distNew = ballStart.distanceFrom(inter);
						float distOld = ballStart.distanceFrom(bestIntersection);
						
						if (distOld > distNew || bestIntersection.x==-1){
							lineWhichIntersectsObject=insideBumper;
							bestIntersection=inter;
							intersectionWithObject = inter;
							intersectedBumper=bumper;
						} 
						
					}	
				}
				bumper=getNextBumper();
			}
			intersectionWithObject=bestIntersection;
			
			/*****************************************************
			 * Determine if the ball went into or out of a Zone
			 */
			Zone zone = getFirstZone();
			boolean intersectWithZone=false;
			Coordinate intersectionWithZone = null;
			StreightLine lineWhichIntersectsZone = null;
			bestIntersection = new Coordinate(-1,-1);
			Zone intersectedZone=null;
			while (zone!=null){
				i = zone.iterate();
				while (i.hasNext()){
					StreightLine zoneEdge = i.next();
				
					Coordinate inter = zoneEdge.intersects(ballLine);
					if (inter!=null){
						Log.i("GOLF","ball intersects Zone at "+inter.x+" , "+inter.y);
						
						intersectWithZone=true;
						
						//if we intersect multiple bumpers then we must first deal with the closest one
						float distNew = ballStart.distanceFrom(inter);
						float distOld = ballStart.distanceFrom(bestIntersection);
						
						if (distOld > distNew || bestIntersection.x==-1){
							lineWhichIntersectsZone=zoneEdge; 
							bestIntersection=inter;
							intersectionWithZone = inter;
							intersectedZone=zone;
						} 
					}	
				}
			
				zone=getNextZone();
			}
			intersectionWithZone=bestIntersection;
			
		
			//if we intersect multiple bumpers then we must only bounce off the closest one
			float borderDist = intersectionWithBorder.distanceFrom(ballStart);
			float bumperDist = intersectionWithObject.distanceFrom(ballStart);
			float zoneDist   = intersectionWithZone.distanceFrom(ballStart);	
			if (!intersectWithObject){
				bumperDist=Float.MAX_VALUE;
			}
			if (!intersectWithZone){
				zoneDist = Float.MAX_VALUE;
			}
			if (!intersectWithBorder){
				borderDist = Float.MAX_VALUE;
			}
			if (borderDist < bumperDist && borderDist < zoneDist){
				intersectWithObject=false;
				intersectWithZone=false;
			}	
			if (bumperDist < borderDist  && bumperDist < zoneDist){
				intersectWithBorder=false;
				intersectWithZone=false;
			}
			if (zoneDist < borderDist  && zoneDist < bumperDist){
				intersectWithBorder=false;
				intersectWithObject=false;
			}	
			
			
			if (intersectWithBorder){
				Coordinate inside=findPointInside(green,lineWhichIntersects,intersectionWithBorder);
				calculateBounceVelocity(lineWhichIntersects.getRadians(),velocity);
		        
		        /*******************************************************
		         * Set the ball start point to the point just before it 
		         * crossed the bumper.
		         *  TODO: Also we need to damp the velocity due to the bounce
		         */			     
		        timeStartTossStage=System.currentTimeMillis()-ratioOfDistancePast(inside,delta);	
		        ball.x = ballStart.x = inside.x;
				ball.y = ballStart.y = inside.y;	
				Log.i("GOLF","ball moved ib = "+ball.x+" , "+ball.y);
				//debugPoint = inside;  
				tossing.actionTime=System.currentTimeMillis();
			}
			else if (intersectWithObject){
				Coordinate outside = findPointOutside(intersectedBumper,lineWhichIntersectsObject,intersectionWithObject);
				
				onObjectBounce(intersectedBumper.id,velocity.total());
				calculateBounceVelocity(lineWhichIntersectsObject.getRadians(),velocity);
		        
		        /*******************************************************
		         * Set the ball start point to the point just before it 
		         * crossed the bumper.
		         *  TODO: Also we need to damp the velocity due to the bounce
		         */	
				timeStartTossStage=System.currentTimeMillis()-ratioOfDistancePast(outside,delta);	
		        ball.x = ballStart.x = outside.x;
				ball.y = ballStart.y = outside.y;
				
				Log.i("GOLF","ball moved io = "+ball.x+" , "+ball.y);
				//debugPoint = outside;    
			
				tossing.actionTime=System.currentTimeMillis();
			} 
			else if (intersectWithZone){	
				//Log.i("GOLF","Intersect with zone at "+intersectionWithZone.x+", "+intersectionWithZone.y);
				Coordinate newBallPoint=null;
				//Determine if we are already in the zone, in which case the ball is exiting the zone
				if (currentZone == intersectedZone){
					//Then we are exiting the zone
					
					newBallPoint = findPointOutside(intersectedZone,lineWhichIntersectsZone,intersectionWithZone);
					//Log.i("GOLF","Exiting Zone at "+newBallPoint.x+","+newBallPoint.y);
					currentZone = zoneNoZone;
				} else {
					newBallPoint = findPointInside(intersectedZone,lineWhichIntersectsZone,intersectionWithZone);
					//Log.i("GOLF","Entering Zone at "+newBallPoint.x+","+newBallPoint.y);
					//Else we are entering the zone
					currentZone = intersectedZone;
				}
			
				
				timeStartTossStage=System.currentTimeMillis()-ratioOfDistancePast(newBallPoint,delta);	
				debugPoint = newBallPoint;
				ballStart.x = ball.x = newBallPoint.x;
				ballStart.y = ball.y = newBallPoint.y;
				Log.i("GOLF","ball moved iz = "+ball.x+" , "+ball.y);
				long delay = TOSS_INTERVAL-ratioOfDistancePast(newBallPoint,delta);
				tossing.actionTime=delay+System.currentTimeMillis();
			}
			else{
				if (currentZone.contains(ball)==false){
					currentZone=zoneNoZone;
				}
				zone = getFirstZone();
				while (zone!=null){
					if (zone.contains(ball)) {
						currentZone=zone;
					}
					zone=getNextZone();
				}
				
				velocity = currentZone.adjustor.adjust(velocity,delta);
				timeStartTossStage=System.currentTimeMillis();
			//	Log.i("GOLF","ball from  rm = "+lastBallX+" , "+lastBallY);
				ballStart.x = ball.x;
				ballStart.y = ball.y;
		//		Log.i("GOLF","ball moved rm = "+ball.x+" , "+ball.y);
				tossing.actionTime=TOSS_INTERVAL+System.currentTimeMillis();
			}
			onBallMovementProcessed(delta);
			postInvalidate();
			
			if (velocity.total()<0.1f){
	    		scoreUpdator.newScore(numberOfSwings,0xFF);
	    	}
			/****************************************
			 * Determine if its time to end this one
			 */
			if (/*System.currentTimeMillis() >= tossTime || */
				velocity.total()<0.1f && currentZone.hasForce==false){
				distanceFromHole = ball.distanceFrom(ballHolePoint());
				
				if (distanceFromHole<10){
					downTheHole();
					drawBall=false;
				} else {
				
				}
			} else {
				
			}
		}
	}

	private long ratioOfDistancePast(Coordinate point, int timeDelta) {
		float distanceInside = ballStart.distanceFrom(point);
	    float ratioInside= distanceInside/(ballStart.distanceFrom(ball));
	    float ratioOutside = 1f-ratioInside;
	    return (int) (timeDelta*ratioOutside);
	}

	static final float BOUNCE_DAMP = 0.8f;
	private void calculateBounceVelocity(float theta, Velocity velocity) {
		 /****************************************************************
         * Calculate Normal vector (perpendicular to bumper) 
         * then use the normal to come up with new velocity for the ball 
         */
        float normalY = (float) Math.cos( theta );
        float normalX = (float) -Math.sin( theta );
        float v_n = (velocity.x*normalX)+(velocity.y*normalY);
        //float n_n = (normalX*normalX)+(normalY*normalY);
        float resultX = velocity.x - 2 * normalX * v_n;
        float resultY = velocity.y - 2 * normalY * v_n;
		velocity.x = resultX;
        velocity.y = resultY;
        velocity.setTotal(velocity.total()*BOUNCE_DAMP);
	}});

	
	/****************************************************************
	 * Game controls fling & touch
	 */
	
	void ballVelocityAdd(float x,float y){
		//tossTime= (System.currentTimeMillis()+(long)((Math.abs(y)+Math.abs(x))*TOSS_TIME_DAMP));
		timeStartTossStage=System.currentTimeMillis();
		ballStart.x = ball.x;
		ballStart.y = ball.y;
		tossing.actionTime=System.currentTimeMillis();
		velocity.x+=x;
		velocity.y+=y;
		addAction(tossing);	
	}
	
	protected void onBallMovementProcessed(int delta) {
	}

	void ballVelocitySet(float x, float y){
		//tossTime= (System.currentTimeMillis()+(long)((Math.abs(y)+Math.abs(x))*TOSS_TIME_DAMP));
		timeStartTossStage=System.currentTimeMillis();
		ballStart.x = ball.x;
		ballStart.y = ball.y;
		tossing.actionTime=System.currentTimeMillis();
		velocity.x=x;
		velocity.y=y;
		addAction(tossing);	
	}
	
	float getTotalBallVelocity(){
		return velocity.total();
	}
	
	
	protected void checkIfBumperMovedOverBall(int whichBumper) {
		Bumper b = getBumper(whichBumper);
		int tries=0;
		int ballLineLength=7;
		if (b.contains(ball)){	
			boolean movedBall=false;
			while (movedBall==false && tries < 15){
				tries++;//endless loop brekout
				StreightLine ballLine;// = new StreightLine(ball.x-ballLineLength,ball.y-ballLineLength,ball.x+ballLineLength,ball.y+ballLineLength);
				StreightLine ballLine2;// = new StreightLine(ball.x+ballLineLength,ball.y+ballLineLength,ball.x-ballLineLength,ball.y-ballLineLength);
			
				
				ballLine = new StreightLine(ball.x-ballLineLength,ball.y-ballLineLength,ball.x+ballLineLength,ball.y+ballLineLength);
				ballLine2 = new StreightLine(ball.x+ballLineLength,ball.y+ballLineLength,ball.x-ballLineLength,ball.y-ballLineLength);
				Iterator<StreightLine> slItterator = b.iterate();
				while (slItterator.hasNext()){
					StreightLine sl = slItterator.next();
					Coordinate inrersectionPoint = sl.intersects(ballLine);
					if (inrersectionPoint==null){
						inrersectionPoint=sl.intersects(ballLine2);
					}
					if (inrersectionPoint!=null){
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
	void onBumperMovedBall(int whichBumper){};
	ScoreUpdator scoreUpdator;
	
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
		super.onFling(e1, e2, velocityX, velocityY);
		if (ControlerMode==CONTROLER_FLING){
			synchronized(this)
			{
				removeAction(tossing);		
				if (!containsAction(tossing)){		
					ballVelocitySet(velocityX*velocityScalingX,velocityY*velocityScalingY);
					numberOfSwings++;
					scoreUpdator.newScore(numberOfSwings,0);
				}
			}
			return true;
		}
		return false;
	}
	
	protected void onObjectBounce(int id, float force) {
	}

	int currentBumper;
	Bumper getFirstBumper(){
		currentBumper=0;
		return getNextBumper();
	}
	Bumper getBumper(int whichBumper){return null;}
	Bumper getNextBumper(){
		currentBumper++;
		return getBumper(currentBumper);
	}
	
	int currentZoneCount;
	Zone getFirstZone(){
		currentZoneCount=0;
		return getNextZone();
	}
	Zone getZone(int whichZone){return null;}
	Zone getNextZone(){
		currentZoneCount++;
		return getZone(currentZoneCount);
	}

	public void setScoreUpdator(ScoreUpdator golfView) {
		scoreUpdator=golfView;
	}
	
	public Vector currentAim=null;
	static final int POWER_BAR_INTERVAL=100;
	int powerBarHeight=0;
	final AnimationAction powerBar = new AnimationAction(POWER_BAR_INTERVAL,new Runnable(){public void run() {synchronized (GolfView.this) {
		powerBarHeight+=20;
		postInvalidate();
		if (powerBarHeight>479){
			removeAction(powerBar);
			powerBarHeight=0;
		}
		powerBar.actionTime=System.currentTimeMillis()+POWER_BAR_INTERVAL;
	}}});
	
	static final int WAIT_TIME = 1000;
	final AnimationAction waitForNextShot = new AnimationAction(WAIT_TIME,new Runnable(){public void run() {synchronized (GolfView.this) {
		aimMode=AIM_MODE_AIMING;
		removeAction(waitForNextShot);	
	}}});
	static final int AIM_MODE_AIMING=1;
	static final int AIM_MODE_POWERBAR=2;
	static final int AIM_MODE_WAITING=3;
	
	int aimMode=AIM_MODE_AIMING;
	
	
	synchronized public boolean onTouchEvent (MotionEvent event){
    	super.onTouchEvent(event);
    	if (event.getAction() == MotionEvent.ACTION_DOWN){
    		scoreUpdator.newScore(numberOfSwings,0);
    	}
    	if (event.getAction() == MotionEvent.ACTION_UP && velocity.total()<0.1f){
    		scoreUpdator.newScore(numberOfSwings,0xFF);
    	}
    	
    	
    	if (ControlerMode==CONTROLER_AIM){
    		switch (aimMode){
    			case AIM_MODE_AIMING:
    				if (event.getAction()== MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_MOVE){
    					currentAim = new Vector(ball,new PointF(event.getRawX(),event.getRawY()));
		    			currentAim.setTotal(40);
		    			invalidate();
		    			Log.i("GOLF","Ball Aim set ");
    				}
    				if (event.getAction()==MotionEvent.ACTION_UP){
    					powerBar.actionTime=System.currentTimeMillis()+POWER_BAR_INTERVAL;
    	    			addAction(powerBar);
    	    			aimMode=AIM_MODE_POWERBAR;
    	    			Log.i("GOLF","Ball power bar started");
    				}
    				break;
    			case AIM_MODE_POWERBAR:
    				if (event.getAction()== MotionEvent.ACTION_DOWN){
    	    			if (currentAim!=null){
    	    				currentAim.setTotal((float)powerBarHeight/200f);
		    				ballVelocitySet(currentAim.x,currentAim.y);
		    				currentAim=null;
		    				removeAction(powerBar);
		    				powerBarHeight=0;
		    				aimMode=AIM_MODE_WAITING;
		    				Log.i("GOLF","Ball velocity set ");
		    				waitForNextShot.actionTime=System.currentTimeMillis()+WAIT_TIME;
		    				addAction(waitForNextShot);
		    				numberOfSwings++;
    	    			}
    				}
    				break;
    			case AIM_MODE_WAITING:
    				
    				break;
    		}
    		
    		
    	}
    	
    	return true;
    }
	

}



