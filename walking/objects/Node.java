package org.dynamac.bot.api.walking.objects;

import org.dynamac.bot.api.wrappers.Tile;

public class Node {

	Tile tile;
	double g;

	public Node(Tile tile, double g) {
		this.tile = tile;
		this.g = g;
	}
	
	public double getG() {
		return this.g;
	}

	public Tile getTile() {
		return this.tile;
	}
}
