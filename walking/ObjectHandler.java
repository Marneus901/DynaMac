package org.dynamac.bot.api.walking;

import org.dynamac.bot.api.methods.Players;
import org.dynamac.bot.api.wrappers.GameObject;
import org.dynamac.bot.api.wrappers.Tile;

public class ObjectHandler {

	static Tile previousLocation;

	public static boolean handleObject(Tile theLocation,
			GameObject theObject, String theAction, boolean useModel) {
		previousLocation = Players.getMyPlayer().getLocation();

		while(previousLocation.equals(Players.getMyPlayer().getLocation()) &&
				previousLocation.getPlane() == Players.getMyPlayer().getLocation().getPlane()) {
			theObject.doAction(theAction);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return previousLocation.equals(Players.getMyPlayer().getLocation());
		}
		return useModel;
	}
}
