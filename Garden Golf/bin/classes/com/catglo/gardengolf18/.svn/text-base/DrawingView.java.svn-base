package com.catglo.gardengolf18;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.Shader;
import android.graphics.Bitmap.Config;
import android.graphics.Paint.Style;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.GestureDetector.OnGestureListener;
import android.view.View.OnTouchListener;

abstract public class DrawingView extends View implements Runnable,OnGestureListener {
	public Bitmap backgroundImage;
	public static boolean showDebug=false;
	
	
	/***************************************************************************
	 * Action Scheduling
	 * 
	 *   - addAction
	 *   - removeAction
	 *   - containsAction
	 *   - run()
	 *   
	 */
	private static final int MAX_ACTIONS=255;
	private static final int SYSTEM_MINIMUM_THREAD_WAKEUP=10; 
	private AnimationAction[] actions = new AnimationAction[MAX_ACTIONS];
	long currentWaitTime=0;
	int actionsLength=0;
	void addAction(AnimationAction action){
		if (containsAction(action)){
			//throw (new IllegalStateException());
			return;
		}
		synchronized(actions){
			if (actionsLength>=MAX_ACTIONS){
				throw(new IllegalStateException());
			}
			actions[actionsLength++]=action;
			if (action.actionTime < currentWaitTime){
				synchronized (thread) {
					thread.notify();
				}
			}
		}
	}
	
	void removeAction(AnimationAction action){
		synchronized(actions){
			boolean removing=false;
			for (int i = 0; i < actionsLength;i++){
				if (removing){
					actions[i-1]=actions[i];
				} else {
					if (action == actions[i]) {
						removing=true;
					}
				} 
			}
			if (removing){
				actionsLength--;
			}
		}
	}
	
	boolean containsAction(AnimationAction action){
		boolean contains=false;
		synchronized(actions){
			for (int i = 0; i < actionsLength;i++){
				if (action==actions[i]){
					contains=true;
				}
			}
		}
		return contains;
	}
	
	public void run(){
		long time = System.currentTimeMillis();
		
		if (actionsLength==0)
			return;
		
		AnimationAction[] actionsToRun = new AnimationAction[MAX_ACTIONS];
		int runCount=0;
		long now = System.currentTimeMillis();
		
		synchronized(actions){
			currentWaitTime=Long.MAX_VALUE;
			for (int i = 0; i < actionsLength;i++){
				if (actions[i].actionTime < currentWaitTime){
					currentWaitTime=actions[i].actionTime;
				}
				if (actions[i].actionTime < now+SYSTEM_MINIMUM_THREAD_WAKEUP) {
					actionsToRun[runCount++] = actions[i];
					actions[i].actionTime=Long.MAX_VALUE;
				}
			}
		}
		
		if (runCount==0){
			try {
				synchronized (thread) {
					thread.wait(currentWaitTime-now);
				}
			} catch (InterruptedException e) {}
		} else {
			for (int i = 0; i < runCount;i++){
				actionsToRun[i].action.run();	
			}
		}
	}
	
	/*************************************************************************
	 * Fixed animations handlers
	 * 
	 * 
	 */
	
	LinkedList<AnimationFrames> currentAnimation= new LinkedList<AnimationFrames>();

	
	protected void drawBackground(Canvas canvas){
	
	}
	
	
	protected void drawSurface(Canvas canvas){
	      
	}
	
	synchronized protected void onDraw(Canvas c){
		c.scale(XScaling, YScaling);
		drawBackground(c);
		
		if (currentAnimation!=null){
			Iterator<AnimationFrames> i = currentAnimation.iterator();
			while (i.hasNext()){
				AnimationFrames frames = i.next();
				if (frames.background){
					AnimationFrame animationFrame = frames.frames.get(frames.current);
					Matrix matrix = new Matrix();
					matrix.postTranslate(animationFrame.X, animationFrame.Y);
					if (animationFrame.mirror){
						matrix.preScale(-1.0f,1.0f);
					}
					if (animationFrame.angle!=0){
						matrix.postRotate(animationFrame.angle);
					}
					c.drawBitmap(animationFrame.image, matrix, null);
				}
			}
		}
		
		drawSurface(c);
		  
		if (currentAnimation!=null){
			Iterator<AnimationFrames> i = currentAnimation.iterator();
			while (i.hasNext()){
				AnimationFrames frames = i.next();
				if (frames.background==false){
					AnimationFrame animationFrame = frames.frames.get(frames.current);
					Matrix matrix = new Matrix();
					matrix.postTranslate(animationFrame.X, animationFrame.Y);
					if (animationFrame.mirror){
						matrix.preScale(-1.0f,1.0f);
					}
					if (animationFrame.angle!=0){
						matrix.postRotate(animationFrame.angle);
					}
					c.drawBitmap(animationFrame.image, matrix, null);
				}
			}
		} 
	}
	
			
	protected void pause(){
		thread._pause();
		/*Iterator<AnimationAction> itterator = removeOnMenuExit.iterator();
		while (itterator.hasNext()){
			removeAction(itterator.next());
			itterator.remove();
		}*/
	}
	
	protected void resume(){
		thread._unpause();
	}
	
	public void destroy(){
		thread.interrupt();
		freeBitmaps();
	}
	
	class DrawingThread extends Thread {
        private boolean currentlyRunning=true;
        public DrawingThread() {
        }
 
        public void _pause() {
            currentlyRunning=false;
        }
      
        @Override
        public void run() {
        	while (!thread.isInterrupted()){	
	    		if (currentlyRunning) {
	    			DrawingView.this.run();
	    		} else {
	    			try {
						synchronized(thread) {
							thread.wait();
						}
					} catch (InterruptedException e) {
						return;
					}
	    		}
        	}
        }
   
        public void _unpause() {
           	currentlyRunning=true;  
           	synchronized(thread){
           		thread.notify();
           	}
        }
	}	
	DrawingThread thread = new DrawingThread();
	SoundPool sound;
	
	private float XScaling;
	private float YScaling;
	protected float XScalingRecripricol;
	protected float YScalingRecripricol;
	
	int width;
	int height;
	//BitmapCache bitmapCache;
	

	private GestureDetector gestureDector;
	private OnTouchListener gestureListener;

	public DrawingView(Context context,int width,int height) {
		super(context);
		
		sound = new SoundPool(10,AudioManager.STREAM_MUSIC,0);
	    
		thread.start();
		
        XScaling = (float)width/320f;
        YScaling = (float)height/480f;
        XScalingRecripricol = 1/XScaling;
        YScalingRecripricol = 1/YScaling;
        
        this.width = width;  
        this.height = height;
               
        
        gestureDector = new GestureDetector(this);
		gestureListener = new View.OnTouchListener() {
			public boolean onTouch(final View v, final MotionEvent event) {
				if (gestureDector.onTouchEvent(event)) return true;
				return false;
			}
		};
		setOnTouchListener(gestureListener);
	}
	
    synchronized public boolean onTouchEvent (MotionEvent event){
    	super.onTouchEvent(event);
    	return true;
    }
    
	public boolean onDown(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,float velocityY) {
		long time=System.currentTimeMillis();
		
		return false;
	}

	public void onLongPress(MotionEvent e) {
		// TODO Auto-generated method stub
		
	}

	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		// TODO Auto-generated method stub
		return false;
	}

	public void onShowPress(MotionEvent e) {
		// TODO Auto-generated method stub
		
	}

	public boolean onSingleTapUp(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

	//For fixed animations, I need to make this work for more than 1 at a time
	
	synchronized void playAnimationFrames(final AnimationFrames animation){
		Iterator<AnimationFrames> i = currentAnimation.iterator();
		while (i.hasNext()){
			if (animation == i.next()){
				return;
			}
		}
		AnimationAction playFixedAnimation = new AnimationAction(0,new Runnable(){public void run() {
			//TODO: deal with this exception
			//06-23 21:08:20.433: ERROR/AndroidRuntime(16254): Uncaught handler: thread Thread-16 exiting due to uncaught exception
			//06-23 21:08:20.463: ERROR/AndroidRuntime(16254): java.lang.NullPointerException
			//06-23 21:08:20.463: ERROR/AndroidRuntime(16254):     at com.catglo.beginerflashlightpro.DrawingView$1.run(DrawingView.java:697)
			//06-23 21:08:20.463: ERROR/AndroidRuntime(16254):     at com.catglo.beginerflashlightpro.DrawingView.run(DrawingView.java:153)
			//06-23 21:08:20.463: ERROR/AndroidRuntime(16254):     at com.catglo.beginerflashlightpro.DrawingView$DrawingThread.run(DrawingView.java:300)

			synchronized(DrawingView.this) {
	
				final AnimationFrame frame = animation.frames.get(animation.current);
				int time;
				
				if (animation.current==0)
					time=0;
				else 
					time = animation.frames.get(animation.current-1).time;
				
				animation.parent.actionTime=System.currentTimeMillis()+time;
				
				
				postInvalidate();
				if (animation.current == animation.frames.size()-1) {
					if (animation.endless){
						animation.current=0;
					} else {
						removeAction(animation.parent);
						currentAnimation.remove(animation);
					}
				} else {
					animation.current++;
				}
				
				if (frame.extraAction!=null)
					frame.extraAction.run();
				
			}
		}});

		
		animation.parent = playFixedAnimation;
		animation.current=0;
		
		if (currentAnimation==null)
			Log.i("BUNNY","currentAnimation==null");
		if (animation==null)
			Log.i("BUNNY","animation==null");
		
		currentAnimation.add(animation);
		playFixedAnimation.actionTime=System.currentTimeMillis()+animation.frames.get(0).time;
		addAction(playFixedAnimation);
	}
	
	
	/*****************************************************************
	 * Bitmap Cache
	 */
	LinkedList<Bitmap> allBitmaps = new LinkedList<Bitmap>();
	Bitmap loadBitmap(int id){
		BitmapFactory.Options ops = new BitmapFactory.Options(); 
			
		Bitmap b = BitmapFactory.decodeResource(getResources(),id,ops);
		allBitmaps.add(b);
		return b;
	}
	
	void freeBitmaps(){
		Iterator<Bitmap> i = allBitmaps.iterator();
		while (i.hasNext()){
			Bitmap b = i.next();
			if (b!=null){
				b.recycle();
			}
		}
		System.gc();
	}
	
}
