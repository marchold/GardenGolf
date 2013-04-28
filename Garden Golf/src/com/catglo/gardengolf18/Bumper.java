package com.catglo.gardengolf18;

import android.graphics.Matrix;
import android.graphics.Point;

public class Bumper extends Polygon {
	int id;
	Bumper(Point[] points, int id) {
		super(points);
		this.id = id;
	}
	
	public Bumper clone(int xoffset,int yoffset,int id){
		Point[] p = new Point[points.length];
		for (int i = 0; i < points.length;i++){
			p[i] = new Point(xoffset + points[i].x,yoffset + points[i].y);
		}
		return new Bumper(p, id);		
	}
}
