package com.catglo.gardengolf18;

import android.content.Intent;

public class ListItem {  
	public ListItem(int s, int id, int par, int min, String dbname) {
		name=s; 
		resource=id;
		this.par = par;
		this.minimum = min;
		score=Integer.MAX_VALUE;
		this.dbname = dbname;
	}
    int name;
    int resource;
    
    int par;
    float average;
    int best;
    int worst;
    int minimum;
    int score;
    String dbname;
    Intent intent;
}