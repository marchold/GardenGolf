package com.catglo.gardengolf18;

//TODO: fire should push ball
//TODO: fire should look like its blowing in the wind from ball motion
//TODO: ??the flambo should knock the ball

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

public class Jester extends GolfActivity {
    public class JesterView extends GolfView {
		private Bitmap face;
		private Matrix faceMatrix;
		private AnimationAction faceAnimation;
		private Random random;
		private Bumper triangleRight;
		private Bumper triangleLeft;
		private Bumper square;
		private Bumper[] bumpers;
		private Bumper triangle;
		private Bumper triangleRightB;
		private Bumper triangleLeftB;
		static final int FACE_INTERVAL = 1000;
		boolean hide=true;
		private Bumper faceBumper;
		private Matrix goneMatrix;
		
		public JesterView(Context context, int width, int height) {
			super(context, width, height);
				
			setBackgroundResource(R.drawable.jester_background);
			face = loadBitmap(R.drawable.jester_face);
			faceMatrix = new Matrix();
			random=new Random();
			faceBumper = new Bumper(new Point[]{new Point(32,133),new Point(49,115),new Point(50,108),new Point(55,102),new Point(59,93),new Point(59,83),new Point(59,76),new Point(65,70),new Point(73,70),new Point(80,80),new Point(89,92),new Point(98,97),new Point(108,94),new Point(117,84),new Point(119,86),new Point(121,83),new Point(122,75),new Point(114,74),new Point(113,80),new Point(105,80),new Point(95,72),new Point(86,63),new Point(77,58),new Point(69,53),new Point(74,48),new Point(85,43),new Point(94,35),new Point(96,25),new Point(92,14),new Point(94,12),new Point(94,7),new Point(96,3),new Point(90,0),new Point(87,3),new Point(82,4),new Point(85,8),new Point(85,12),new Point(82,25),new Point(58,37),new Point(47,49),new Point(37,38),new Point(17,38),new Point(10,32),new Point(6,22),new Point(9,17),new Point(6,13),new Point(0,14),new Point(0,20),new Point(2,23),new Point(0,34),new Point(0,47),new Point(6,56),new Point(13,60),new Point(23,61),new Point(15,72),new Point(12,84),new Point(12,104),new Point(16,113),new Point(11,113),new Point(15,125),new Point(25,132)},44);
			
			goneMatrix = new Matrix();
			goneMatrix.postTranslate(400, 500);
			faceAnimation = new AnimationAction(System.currentTimeMillis()+FACE_INTERVAL,new Runnable(){public void run(){
				int x = random.nextInt(6);
				int y = random.nextInt(7);
				faceMatrix.reset();
				faceMatrix.postTranslate(x*45+38, y*48+55);
				boolean mirror = false;
				
				if (random.nextInt(10)==1){
					hide=false;
					faceBumper.applyMatrix(faceMatrix);
				}else {
					hide=true;
					checkIfBumperMovedOverBall(44);
					faceBumper.applyMatrix(goneMatrix);
				}
				
				if (random.nextBoolean())
					mirror=true;
				if (mirror){
					faceMatrix.preScale(-1, 1);
					faceMatrix.postTranslate(67, 0);
				}
				faceAnimation.actionTime=System.currentTimeMillis()+FACE_INTERVAL;
				postInvalidate();
			}});
			addAction(faceAnimation);
			
			
			triangleRight = new Bumper(new Point[]{new Point(0,0) ,new Point(23,0),new Point(23,46)},0);
			triangleLeft  = new Bumper(new Point[]{new Point(23,0),new Point(0,0) ,new Point(0,46)},0);
			triangleRightB= new Bumper(new Point[]{new Point(0,0) ,new Point(23,46),new Point(23,0)},0);
			triangleLeftB = new Bumper(new Point[]{new Point(23,0),new Point(0,0) ,new Point(23,46)},0);
			square        = new Bumper(new Point[]{new Point(0,0) ,new Point(46,0),new Point(46,46),new Point(0,46)},0);
		    triangle      = new Bumper(new Point[]{new Point(0,46),new Point(23,0),new Point(46,46)},0);
		    
		    
			bumpers = new Bumper[]{null,
				triangleRight.clone(67, 2,1),
				square.clone(91, 2,2),
				square.clone(181,2, 2),
				triangleLeft.clone(227,2,3),
				//row2
				triangleLeftB.clone(23, 50,4),
				triangleLeftB.clone(115, 50,5),
				new Bumper(new Point[]{new Point(182,98),new Point(181,50),new Point(228,50),new Point(181,50)},6),
				triangle.clone(182,50,7),
				triangle.clone(273,50,8),
				//row3
				square.clone(45, 97,9),
				triangle.clone(138, 97,10),
				new Bumper(new Point[]{new Point(251,98),new Point(273,146),new Point(228,146),new Point(227,145),new Point(270,145),new Point(249,98)},11),
				//row 4
				new Bumper(new Point[]{new Point(0,195),new Point(24,145),new Point(22,143),new Point(0,192)},11),		
				square.clone(92,145,12),
				square.clone(138,145,13),
				triangleRight.clone(206, 145,14),
				new Bumper(new Point[]{new Point(274,193),new Point(295,147),new Point(293,145),new Point(272,192)},15),
				//row 5
				new Bumper(new Point[]{new Point(0,193),new Point(4,237),new Point(47,241),new Point(47,193),new Point(45,192),new Point(45,238),new Point(3,238),new Point(3,193),},15),
				triangleRightB.clone(161, 192,16),
				triangle.clone(227,192,17),
				//row 6
				square.clone(2, 240, 18),
				square.clone(47, 240, 19),
				triangle.clone(92, 240, 20),
				new Bumper(new Point[]{new Point(139,241),new Point(139,336),new Point(135,336),new Point(136,240)},21),
				triangleRightB.clone(205, 240,22),
				triangle.clone(273, 240, 23),
				new Bumper(new Point[]{new Point(180,240),new Point(206,240)},24),
				//row 7
				triangle.clone(46,288,25),
				//line extended in row 6
				new Bumper(new Point[]{new Point(160,286),new Point(184,335)},26),
				new Bumper(new Point[]{new Point(228,288),new Point(228,480)},27),
				new Bumper(new Point[]{new Point(249,288),new Point(228,336)},28),
				new Bumper(new Point[]{new Point(272,288),new Point(272,335)},29),
				//row 8
				new Bumper(new Point[]{new Point(2,382),new Point(23,336)},30),
				new Bumper(new Point[]{new Point(91,382),new Point(91,336)},31),
				triangle.clone(90,336, 32),
				//row 9
				triangleLeft.clone(47, 383,33),
				new Bumper(new Point[]{new Point(137,382),new Point(137,430),new Point(160,383),new Point(137,430)},34),
				new Bumper(new Point[]{new Point(183,427),new Point(184,383),new Point(228,382),new Point(227,430),new Point(228,382),new Point(184,383)},35),
				new Bumper(new Point[]{new Point(250,384),new Point(273,430),new Point(273,384),new Point(273,430)},36),
				//row 10
				new Bumper(new Point[]{new Point(114,432),new Point(139,478)},37),
				new Bumper(new Point[]{new Point(183,432),new Point(183,478)},38),
				triangle.clone(183, 432, 39),
				new Bumper(new Point[]{new Point(228,432),new Point(228,478)},38),
				triangle.clone(272, 432, 39),
				//ones I forgot
				new Bumper(new Point[]{new Point(185,335),new Point(227,335)},40),
				new Bumper(new Point[]{new Point(272,237),new Point(272,190)},41),
				new Bumper(new Point[]{new Point(272,382),new Point(294,336)},42),
			    new Bumper(new Point[]{new Point(97,381),new Point(60,381)},43),
			    faceBumper
			};
		}
		
		   
		
		protected void drawBackground(Canvas canvas){
			super.drawSurface(canvas);
			if (!hide)
				canvas.drawBitmap(face, faceMatrix, null);
	
		}	
		
		protected Coordinate ballStartPoint(){
			return new Coordinate(40,440);
		}
		protected Coordinate ballHolePoint(){
			return new Coordinate(160,27);
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
		
		Bumper getBumper(int whichBumper){
			if (whichBumper >= bumpers.length){
				return null;
			}
			return bumpers[whichBumper];
		}
				
		@Override
	    void downTheHole(){
	    	super.downTheHole();
	    	Intent data = new Intent(getApplicationContext(),GardenGolf.class);
	    	data.putExtra("Score", numberOfSwings);
	    	data.putExtra("green",greenNumber);
	    	startActivity(data);
	    	Jester.this.finish();
	    }
		
	}

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        golfView = new JesterView (this.getApplicationContext(),width,height);
        layout.addView(golfView, 0);
        golfView.setScoreUpdator(this);
        score.setPadding(10, 10, 10, 10);
        LayoutParams lp = new FrameLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
        lp.gravity=Gravity.BOTTOM|Gravity.RIGHT;
        score.setLayoutParams(lp);
        fontColor = 000000;
    }
}

