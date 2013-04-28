package com.catglo.gardengolf18;

//TODO: tire should be places on top of the ball in the middle 

import java.util.Random;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;

public class ModelT extends GolfActivity {
   public class ModelTView extends GolfView {
	
	
		private Bitmap tire;
		public ModelTView(Context context, int width, int height) {
			super(context, width, height);
			
			setBackgroundResource(R.drawable.modelt_background);
			
			score.setPadding(10, 10, 10, 10);
	        score.setTextColor(0xFF000000);
	        LayoutParams lp = new FrameLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
	        lp.gravity=Gravity.TOP|Gravity.LEFT;
	        score.setLayoutParams(lp);
	        
	        
	        tire = loadBitmap(R.drawable.modelt_tire);
		
		}
		
	
		protected void drawBackground(Canvas canvas){
			super.drawSurface(canvas);
		}	
		
		protected void drawSurface(Canvas canvas){
			super.drawSurface(canvas);
			canvas.drawBitmap(tire, 157, 316, null);//So the ball can go under the tire
		}	
		
		protected Coordinate ballStartPoint(){
			return new Coordinate(183,111);
		}
		protected Coordinate ballHolePoint(){
			return new Coordinate(176,345);
		}
		protected Polygon getGreenPolygon(){
			Polygon green = new Polygon(new Point[] {
				new Point(2,478),
	            new Point(2,2),
	            new Point(318,2),
	            new Point(318,478)
			});
			return green;
		}
		
		
		static final int CAR=1;
		static final int LEFT_TIRE=2;
		static final int RIGHT_TIRE=3;
		static final int FRONT_WHEEL=4;
		static final int SHOT_LEFT=5;
		static final int SHOT_RIGHT=6;
		
		protected void onObjectBounce(int id) {
			
		}
		
		Bumper[] bumpers = {null,
		    new Bumper(new Point[]{new Point(0,114),new Point(16,101),new Point(41,101),new Point(76,93),new Point(168,107),new Point(181,135),new Point(189,124),new Point(205,81),new Point(191,74),new Point(223,70),new Point(200,123),new Point(212,112),new Point(221,118),new Point(234,113),new Point(251,127),new Point(264,129),new Point(264,161),new Point(276,154),new Point(282,169),new Point(298,191),new Point(288,216),new Point(274,195),new Point(248,181),new Point(226,191),new Point(213,215),new Point(197,229),new Point(157,234),new Point(104,228),new Point(80,227),new Point(68,219),new Point(65,238),new Point(47,251),new Point(22,239),new Point(15,224),new Point(0,213)},CAR),	
		    new Bumper(new Point[]{new Point(27,409),new Point(50,409),new Point(69,392),new Point(81,393),new Point(98,412),new Point(120,415),new Point(103,384),new Point(92,375),new Point(112,369),new Point(125,391),new Point(150,389),new Point(129,357),new Point(102,347),new Point(84,356),new Point(75,370),new Point(51,378),new Point(42,394),new Point(22,388),new Point(11,396),new Point(2,406)},LEFT_TIRE),
		    new Bumper(new Point[]{new Point(149,440),new Point(188,443),new Point(201,424),new Point(221,425),new Point(231,444),new Point(252,442),new Point(243,421),new Point(228,410),new Point(241,403),new Point(253,403),new Point(259,413),new Point(259,420),new Point(281,421),new Point(273,398),new Point(254,382),new Point(231,381),new Point(212,392),new Point(211,401),new Point(190,408),new Point(181,423),new Point(169,423),new Point(155,420),new Point(146,428)},RIGHT_TIRE),
		    new Bumper(new Point[]{new Point(288,214),new Point(268,185),new Point(234,186),new Point(214,212),new Point(219,234),new Point(234,247),new Point(255,250),new Point(273,243),new Point(281,227)},FRONT_WHEEL),
	        new Bumper(new Point[]{new Point(121,353),new Point(153,353),new Point(167,335),new Point(166,314),new Point(146,329),new Point(121,336)},SHOT_LEFT),
		    new Bumper(new Point[]{new Point(185,335),new Point(187,316),new Point(207,330),new Point(214,357),new Point(196,356)},SHOT_RIGHT)
		};
		Bumper getBumper(int whichBumper){
			if (whichBumper >= bumpers.length)
				return null;
			return bumpers[whichBumper];
		}
		
		Bumper getNextBumper(){
			currentBumper++;
			return getBumper(currentBumper);
		}
		
		@Override
	    void downTheHole(){
	    	super.downTheHole();
	    	Intent data = new Intent(getApplicationContext(),GardenGolf.class);
	    	data.putExtra("Score", numberOfSwings);
	    	data.putExtra("green", greenNumber);
	    	startActivity(data);
	    	ModelT.this.finish();
	    }
	}
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        golfView  = new ModelTView (this.getApplicationContext(),width,height);
        layout.addView(golfView, 0);
        golfView.setScoreUpdator(this);
    }
    
}

