package org.dynamac.bot.api.wrappers;

import java.awt.Point;
import java.awt.Polygon;

import org.dynamac.bot.api.methods.Calculations;
import org.dynamac.bot.api.methods.Client;

public class Tile {
	private int localX;
	private int localY;
	private int x;
	private int y;
	private int plane;
	public Tile(int x, int y, int plane){
		this.plane=plane;
		if(x<=104 || y<=104){
			this.localX=x;
			this.localY=y;
			this.x=x+Client.getBaseX();
			this.y=y+Client.getBaseY();
		}
		else{
			this.x=x;
			this.y=y;
			this.localX=x-Client.getBaseX();
			this.localY=y-Client.getBaseY();
		}
	}
	public int getLocalX(){
		return localX;
	}
	public int getLocalY(){
		return localY;
	}
	public int getPlane(){
		return plane;
	}
	public Polygon getPolygon(){
		return Calculations.getTilePolygon(x, y);
	}
	public Point getScreenLocation(){
		return Calculations.locationToScreen(x, y);
	}
	public int getX(){
		return x;
	}
	public int getY(){
		return y;
	}
}
