package com.catglo.gardengolf18;


import android.graphics.Bitmap;

public class AnimationFrame {
	boolean mirror=false;
	float angle=0f;
	public AnimationFrame(int X,int Y,Bitmap bitmap, int time, int sound){
		this.X = X;
		this.Y = Y;
		this.image = bitmap;
		this.time = time;
		this.sound = sound;
		this.extraAction = null;
	}
	public AnimationFrame(int X,int Y,Bitmap bitmap, int time, int sound, boolean mirror, float angle){ 
		this.X = X;
		this.Y = Y;
		this.image = bitmap;
		this.time = time;
		this.sound = sound;
		this.extraAction = null;
		this.mirror = mirror;
		this.angle = angle;
	}
	public AnimationFrame(int X,int Y,Bitmap bitmap, int time, int sound, Runnable extraAction){
		this.X = X;
		this.Y = Y;
		this.image = bitmap;
		this.time = time;
		this.sound = sound;
		this.extraAction = extraAction;
	}
	public AnimationFrame(AnimationFrame other){
		X = other.X;
		Y = other.Y;
		image = other.image;
		time =  other.time;
		sound = other.sound;
		this.extraAction = other.extraAction;
	}
	int X;
	int Y;
	Bitmap image;
	int time;
	int sound;
	Runnable extraAction;
}

