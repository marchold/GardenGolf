package com.catglo.gardengolf18;


import java.util.Iterator;


import android.graphics.Point;

public class PollyLine extends Object implements Iterator<StreightLine>{
	PollyLine(Point[] points){
		this.points=points;
	}
	Point[] points;
	
	private int itterationCount;
	private int xoffset;
	private int yoffset;
	public Iterator<StreightLine> iterate(int xoffset, int yoffset){
		itterationCount=0;
		this.xoffset = xoffset;
		this.yoffset = yoffset;
		return this;
	}
	public Iterator<StreightLine> iterate(){
		return iterate(0,0);
	}
	
	public boolean hasNext() {
		if (itterationCount<points.length-1)
			return true;
		return false;
	}
	public StreightLine next() {
		Point p1 = new Point(points[itterationCount]);
		Point p2 = new Point(points[itterationCount+1]);
		p1.x += xoffset;
		p1.y += yoffset;
		p2.x += xoffset;
		p2.y += yoffset;
		StreightLine sl = new StreightLine(p1,p2);
		itterationCount++;
		return sl;
	}
	public void remove() {
		throw(new UnsupportedOperationException());
	}
}
