package com.catglo.gardengolf18;

import android.graphics.Point;
import android.graphics.PointF;
import android.util.Log;

class Velocity extends Vector {
	Velocity(int x, int y){
		super(x,y);
	}
	Velocity(float x, float y){
		super(x,y);
	}
	Velocity(PointF start,PointF stop){
		super(start,stop);
	}
	Velocity(Point start,Point stop){
		super(start,stop);
	}
	
	public Coordinate getPointDistanceAway(float distance, Coordinate ballStart) {
		Coordinate retVal = new Coordinate(ballStart);
		float theta = getTheta();
		retVal.x += (int) (distance * Math.cos( theta ));
		retVal.y += (int) (distance * Math.sin( theta ));
		return retVal;
	}
	
	public Velocity accelerate(Velocity rampVelocity, int timeDelta) {
		x+=rampVelocity.x*timeDelta;
		y+=rampVelocity.y*timeDelta;
		return this;
	}
	
	public Velocity slideUnder(Velocity rampVelocity, int timeDelta, float stickyness) {
		float invRatio = 1f-stickyness; 
		//x = (rampVelocity.x*stickyness);
		//y = (rampVelocity.y*stickyness);
		
		x = (float)((x*invRatio)+(rampVelocity.x*stickyness));
		y = (float)((y*invRatio)+(rampVelocity.y*stickyness));
		

		//x = (float) Math.sqrt((x*x*invRatio)+(rampVelocity.x*rampVelocity.x*stickyness));
		//y = (float) Math.sqrt((y*y*invRatio)+(rampVelocity.y*rampVelocity.y*stickyness));
		
		//x = (float) Math.sqrt((x)*(x)+(rampVelocity.x)*(rampVelocity.x));
		//y = (float) Math.sqrt((y)*(y)+(rampVelocity.y)*(rampVelocity.y));
	
		
		
		return this;
	}
	
	public Velocity setTotal(float magnatude) {
		super.setTotal(magnatude);
		return this;
	}
}