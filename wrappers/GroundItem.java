package org.dynamac.bot.api.wrappers;

import org.dynamac.bot.api.methods.Client;

public class GroundItem {
	public int localX;
	public int localY;
	public Item item;
	public GroundItem(int x, int y, Item i){
		localX=x;
		localY=y;
		this.item=i;
	}
	public int getLocationX(){
		return localX+Client.getBaseX();
	}
	public int getLocationY(){
		return localY+Client.getBaseY();
	}
	public Tile getLocation(){
		return new Tile(getLocationX(), getLocationY(), Client.getPlane());
	}
}
