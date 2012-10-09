package org.dynamac.bot.api.walking;

import org.dynamac.bot.api.wrappers.Tile;

public class Fundamentals {
	
	public static long manhattonDist(Tile start, Tile end) {
		return Math.abs(start.getX()-end.getX()) + Math.abs(start.getY()-end.getY());
	}
}
