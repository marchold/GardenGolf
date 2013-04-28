package com.catglo.gardengolf18;

import android.graphics.Point;
import android.graphics.PointF;

public class Coordinate extends PointF {
	public Coordinate(float x, float y) {
		super(x,y);
	}

	public Coordinate() {
		super();
	}

	public Coordinate(Coordinate other) {
		super(other.x,other.y);
	}

	public Coordinate(Point other) {
		super(other.x,other.y);
	}

	float distanceFrom(Point p){
		return (float)Math.sqrt((p.x-x)*(p.x-x)+(p.y-y)*(p.y-y));
	}
	
	float distanceFrom(PointF p){
		return (float)Math.sqrt((p.x-x)*(p.x-x)+(p.y-y)*(p.y-y));
	}
}
