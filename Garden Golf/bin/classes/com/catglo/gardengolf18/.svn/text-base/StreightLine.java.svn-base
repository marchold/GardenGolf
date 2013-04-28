package com.catglo.gardengolf18;
import android.graphics.Point;

public class StreightLine {
	StreightLine(Coordinate A, Coordinate B){
		this.A = new Coordinate(A);
		this.B = new Coordinate(B);
	}
	StreightLine(Point A, Point B){
		this.A = new Coordinate(A);
		this.B = new Coordinate(B);
	}
	StreightLine(int aX, int aY, int bX, int bY){
		this.A = new Coordinate(aX,aY);
		this.B = new Coordinate(bX,bY);
	}
	public StreightLine(Coordinate start, float angle, float distance) {
		this.A = start;
		float theta = (float) Math.toRadians(angle);
		this.B = new Coordinate();
		B.x = A.x + (int) (distance * Math.cos( theta ));
		B.y = A.y + (int) (distance * Math.sin( theta ));  
	
	}
	public StreightLine(float aX, float aY, float bX, float bY) {
		this.A = new Coordinate(aX,aY);
		this.B = new Coordinate(bX,bY);
	}
	Coordinate A;
	Coordinate B;
	
	

	Coordinate intersects(StreightLine other){
		Coordinate intersect=null;
		
    	Coordinate C = other.A;
		Coordinate D = other.B;
			
		float g = getHforABCD(C,D,A,B);
		float h = getHforABCD(A,B,C,D);
		if (h >=0 && h <= 1 && g >=0 && g <=1){
			intersect=_intersection;
		}	
		return intersect;
	}
	
	private Coordinate _intersection;
	private float getHforABCD(Coordinate A, Coordinate B, Coordinate C, Coordinate D){
		
		Coordinate E = new Coordinate(B.x-A.x, B.y-A.y );
		Coordinate F = new Coordinate(D.x-C.x, D.y-C.y );
		Coordinate P = new Coordinate(-E.y,E.x);//P = ( -Ey, Ex )
		//h = ( (A-C) * P ) / ( F * P )
		//Coordinate Q = new Coordinate()
		
		
		//F*P (Fx*Px)+(Fy*Py)
		float FxP = (F.x*P.x)+(F.y*P.y);
		if (FxP==0){
			//The lines are parallel
		} else {
			Coordinate AmC = new Coordinate(A.x-C.x,A.y-C.y);
			float AmCxP = (AmC.x*P.x)+(AmC.y*P.y);
			float h = AmCxP/FxP;
			//If h is exactly 0 or 1 the lines touch at an end-point. You can consider this an "intersection" or not as you see fit.
			//Specifically, h is how much you have to multiply the length of the line in order to exactly touch the other line.
			//Therefore, If h<0, it means the rectangle line is "behind" the given line (with "direction" being "from A to B"), and if h>1 the rectangle line is "in front" of the given line.
			if (h>=0 && h <=1){
				_intersection = new Coordinate((int)(C.x+F.x*h),(int)(C.y+F.y*h));//C + F*h.
				return h;
				
			} 
		}
		return Float.MAX_VALUE;
	}
	public float[] getPoints() {
		float[] points = new float[4];
		points[0] = A.x;
		points[1] = A.y;
		points[2] = B.x;
		points[3] = B.y;
		return points;
	}
	public float getRadians() {
		Coordinate C = A;
		Coordinate D = B;
		float xDiff = D.x-C.x; 
        float yDiff = D.y-C.y;
        float theta = (float) Math.atan2(yDiff,xDiff);
        return theta;
	}
	public Vector getNormal() {
		Vector vector = new Vector(A,B);
		vector.setAngle(vector.getAngle()+90);
		vector.setTotal(1);
		return vector;
	}
}
