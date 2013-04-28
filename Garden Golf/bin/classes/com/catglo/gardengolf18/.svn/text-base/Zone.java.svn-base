package com.catglo.gardengolf18;

import android.graphics.Matrix;
import android.graphics.Point;

public class Zone extends Polygon {
	int id;
	VelocityAdjustor adjustor;
	boolean hasForce;
	Zone(Point[] points, int id, VelocityAdjustor adjustor,boolean hasForce) {
		super(points);
		this.id = id;
		this.adjustor = adjustor;
		this.hasForce = hasForce;
	}	
	Zone(Point[] points, int id, VelocityAdjustor adjustor) {
		super(points);
		this.id = id;
		this.adjustor = adjustor;
		this.hasForce = true;
	}	
}
