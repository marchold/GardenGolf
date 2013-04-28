package com.catglo.gardengolf18;

import android.graphics.Point;
import android.graphics.PointF;
import android.util.Log;

class Vector extends PointF {
	Vector(int x, int y){
		super(x,y);
	}
	Vector(float x, float y){
		super(x,y);
	}
	Vector(Point start,Point stop){
		x = stop.x-start.x;
		y = stop.y-start.y;
	}
	Vector(PointF start,PointF stop){
		x = stop.x-start.x;
		y = stop.y-start.y;
	}
	
	float getTheta(){
        float theta = (float) Math.atan2(y,x);
        return theta;
	}

	float radiansToDegrees(float theta){
		return  (float) ((theta/Math.PI)*180f);
	}
	
	float degreesToRadians(float angle){
		return (float)((angle*Math.PI)/180);
	}
	
	float getAngle(){
		return radiansToDegrees(getTheta());
	}
	
	void turn(float degrees){
		 float angle;
		 angle = radiansToDegrees(getTheta());
		 angle += degrees;
		 float theta = degreesToRadians(angle);
		 setVelocityFromRadians(theta);
	}
	
	void setVelocityFromRadians(float theta) {
		float totalVelocity = (float) Math.sqrt(x*x+y*y);
		x = (float) (totalVelocity * Math.cos( theta ));
		y = (float) (totalVelocity * Math.sin( theta ));
	}
	
	void setAngle(float angle){
		setVelocityFromRadians((float) Math.toRadians(angle));
	}
	
	public float total() {
		return (float)Math.sqrt(x*x+y*y);
	}
	
	public void scale(float factor){
		float totalVelocity = (float)Math.sqrt(x*x+y*y);
		totalVelocity *= factor;
		float theta = getTheta();
		x = (float) (totalVelocity * Math.cos( theta ));
		y = (float) (totalVelocity * Math.sin( theta ));
	}
	
	public void matchAngle(StreightLine sl) {
		float xDiff = sl.B.x-sl.A.x;
		float yDiff = sl.B.y-sl.A.y;
		float theta = (float) Math.atan2(yDiff,xDiff);
	
		float angle=(float) (theta*(180/Math.PI));
		float a = angle;
		
		if ((int)angle==90)
			angle+=180;
		//else 
		
		else if (angle > 0 && angle < 90)
			angle-=90;
		
		
		else if (angle>90 && angle <180)
			angle-=90;
		
		Log.i("BUNNY","angle = "+angle+"  was "+a);
		theta = (float) Math.toRadians(angle);
		setVelocityFromRadians(theta);   
	}
	
	public Vector setTotal(float magnatude) {
		float theta = getTheta();
		if (magnatude < 0) {
			throw (new IllegalArgumentException());
		}
		x = (float) (magnatude * Math.cos( theta ));
		y = (float) (magnatude * Math.sin( theta ));
		return this;
	}
}