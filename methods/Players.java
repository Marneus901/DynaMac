/******************************************************
* Created by Marneus901                                *
* � 2012 MarneusScripts.com                            *
* **************************************************** *
* Access to this source is unauthorized without prior  *
* authorization from its appropriate author(s).        *
* You are not permitted to release, nor distribute this* 
* work without appropriate author(s) authorization.    *
********************************************************/
package org.dynamac.bot.api.methods;

import java.util.ArrayList;

import org.dynamac.bot.api.wrappers.Player;


public class Players {
	public static Player getMyPlayer(){
		return Client.getMyPlayer();
	}
	public static Player[] getPlayerArray(){
		return Client.getPlayerArray();
	}
	public static String[] getAllPlayerNames(){
		ArrayList<String> names = new ArrayList<String>();
		for(Player p : getPlayerArray()){
			if(p != null) {
				final String name = p.getPlayerName();
				if (name != null && !name.equals("")) {
					names.add(name);
				}
			}
		    }
		}
		return names.toArray(new String[]{});
	}
}
