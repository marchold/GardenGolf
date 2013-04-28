package com.catglo.gardengolf18;

/***************************************************************
 * AnimationActions is a class for describing timings and 
 * actions that drive animations in a DrawingView
 *
 *
 */
class AnimationAction implements Comparable<AnimationAction> {
	AnimationAction(long actionTime, Runnable action){
		this.actionTime = actionTime;
		this.action = action;
	}
	long actionTime;
	Runnable action;
	String name;
	
	public int compareTo(AnimationAction arg0) {
		AnimationAction other = arg0;
		if (other.actionTime<actionTime) {
			return 1;
		} else if (other.actionTime>actionTime){
			return -1;
		} 
		return 0;
	}
}
