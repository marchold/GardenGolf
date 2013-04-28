package com.catglo.gardengolf18;

import java.util.Iterator;

import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.PointF;

public class Polygon extends PollyLine {
	float[] mapPoints;
	float[] destPoints;
	
	Polygon(Point[] points) {
		super(points);
		
		mapPoints = new float[points.length*2];
		destPoints = new float[points.length*2];
		for (int i = 0; i < points.length; i++){
			mapPoints[i*2+0] = points[i].x;
			mapPoints[i*2+1] = points[i].y;
		}	
	}

	
	 public boolean contains(Point point) {
		 float x = point.x;
		 float y = point.y;
		 boolean oddTransitions = false;
		 int polySides = points.length;
	     for( int i = 0, j = polySides -1; i < polySides; j = i++ ) {
	    	 if( ( (float)(points[i].y) < y && (float)(points[j].y) >= y ) || ( (float)(points[j].y) < y && (float)(points[i].y) >= y ) ) {
	    		 if( (float)(points[i].x) + ( y - (float)(points[i].y) ) / ( (float)(points[j].y) - (float)(points[i].y)) * ((float)(points[j].x) - (float)(points[i].x)) < x ) {
	    			 oddTransitions = !oddTransitions;          
	             }
	    	 }
	     }
		 return oddTransitions;
	}  
	 
	boolean lastOne;
	public Iterator<StreightLine> iterate(){
		lastOne=false;
		return super.iterate();
	}
	
	public boolean hasNext() {  //TODO: make these return the last line segment to close the polygon
		if (lastOne){
			return false;
		}
		boolean retVal = super.hasNext();
		if (retVal==false && lastOne==false){
			lastOne=true;
			retVal=true;
		}
		return retVal;
	}
	public StreightLine next() {
		if (lastOne){
			return new StreightLine(points[points.length-1],points[0]);
		}else {
			return super.next();
		}
	}

	public boolean contains(PointF temp) {
		Point p = new Point();
		p.x = (int)temp.x;
		p.y = (int)temp.y;	
		return contains(p);
	}
	
	public void applyMatrix(Matrix sailingMatrix) {
		sailingMatrix.mapPoints(destPoints,mapPoints);
		for (int i = 0; i < points.length; i++){
			points[i].x = (int) destPoints[i*2+0];
			points[i].y = (int) destPoints[i*2+1];
		}	
	}

	public Polygon clone(int xoffset,int yoffset){
		Point[] p = new Point[points.length];
		for (int i = 0; i < points.length;i++){
			p[i].x = xoffset + points[i].x;
			p[i].y = yoffset = points[i].y;
		}
		return new Polygon(p);		
	}
	
	
}
