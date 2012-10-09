package org.dynamac.bot.api.wrappers;

import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.util.Random;
import java.util.Vector;

import org.dynamac.bot.api.methods.Calculations;
import org.dynamac.bot.api.methods.Client;
import org.dynamac.bot.api.methods.GroundItems;
import org.dynamac.bot.api.methods.Mouse;
import org.dynamac.bot.api.methods.Objects;
import org.dynamac.bot.api.wrappers.Character;

public class Tile {
	private static Character c = new Character(null);
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
	public boolean clickTile(){
		Polygon p = Calculations.getTilePolygon(getLocalX(), getLocalY());
		Rectangle r = p.getBounds();
		Point pt = new Point(new Random().nextInt(r.width)+r.x, new Random().nextInt(r.height)+r.y);
		if(pt.x>0 && pt.x<515 && pt.y>54 && pt.y<388){
			Mouse.clickMouse(pt, 1);
			return true;
		}

		return false;
	}
	public boolean clickMap(){
		if(isOnMap()){
			Mouse.clickMouse(getTileOnMap());
			return true;
		}
		return false;
	}
	public boolean containsGameObject() {
		for(GameObject go : Objects.getAllObjects())
			if(go.getLocationX() == x && go.getLocationY() == y) {
				return true;
			} 
		return false;
	}
	public boolean containsGroundItem() {
		GroundItem[] items = GroundItems.getItemsAt(x, y);
		if(items!=null && items.length>0)
			return true;
		return false;
	}
	public boolean equals(Tile t) {
		return x == t.getX() && y == t.getY() && t.getPlane() == getPlane();
	}
	public Rectangle getBounds(){
		return new Rectangle(getX(),getY(),32,32);
	}
	public Tile getClosestTile(final Tile tile) {
		final Tile player = new Tile(c.getLocationX(),c.getLocationY(), getPlane());
		final Tile close = new Tile((player.getX() + tile.getLocalX()) / 2,(player.getY() + tile.getLocalY()) / 2, getPlane());
		if (!close.isOnMap()) {
			return getClosestTile(close);
		}
		return close;
	}
	public Tile getLastTile(final Tile... tiles) {
		return tiles[tiles.length - 1];
	}
	public int getLocalX(){
		return localX;
	}
	public int getLocalY(){
		return localY;
	}
	public Tile getNext(final Tile... tiles) {
		for (int i = tiles.length - 1; i > 0; i -= 1) {
			if (tiles[i].isOnMap()) {
				return tiles[i];
			}
		}
		return null;
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
	public Tile[] getSurrounding(final Tile t) {
		final Vector<Tile> neighbors = new Vector<Tile>();
		for (int i = -1; i <= 1; i++) {
			for (int j = -1; j <= 1; j++) {
				if (j != i || i != 0) {
					neighbors.add(new Tile(t.x + i, t.y + j, getPlane()));
				}
			}
		} return neighbors.toArray(new Tile[neighbors.size()]);
	}
	public Point getTileOnMap(){
		return Calculations.worldToMap(getLocalX(), getLocalY());
	}
	public int getX(){
		return x;
	}
	public int getY(){
		return y;
	}
	public boolean isOnMap(){
		return getTileOnMap().x != -1 && getTileOnMap().y != -1;
	}	
}
