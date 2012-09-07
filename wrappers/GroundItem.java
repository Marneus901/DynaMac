package org.dynamac.bot.api.wrappers;

public class GroundItem {
	public int localX;
	public int localY;
	public Item item;
	public GroundItem(int x, int y, Item i){
		localX=x;
		localY=y;
		this.item=i;
	}
	
}
