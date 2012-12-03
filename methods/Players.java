/******************************************************
* Created by Marneus901                                *
* © 2012 MarneusScripts.com                            *
* **************************************************** *
* Access to this source is unauthorized without prior  *
* authorization from its appropriate author(s).        *
* You are not permitted to release, nor distribute this* 
* work without appropriate author(s) authorization.    *
********************************************************/
package org.dynamac.bot.api.methods;

import java.util.ArrayList;

import org.dynamac.bot.api.wrappers.Player;
import org.dynamac.bot.api.wrappers.PlayerDef;


public class Players {
	public static String[] getAllPlayerNames(){
		ArrayList<String> names = new ArrayList<String>();
		for(Player p : getPlayerArray()){
			if(p!=null && !p.getPlayerName().equals("")){
				names.add(p.getPlayerName());
			}
		}
		return names.toArray(new String[]{});
	}
	public static Player getMyPlayer(){
		return Client.getMyPlayer();
	}
	public static Player[] getPlayerArray(){
		ArrayList<Player> players = new ArrayList<Player>();
		for(Player p : Client.getPlayerArray())
			if(p!=null)
				players.add(p);
		return players.toArray(new Player[]{});
	}
	public static Player getPlayerByID(int id){
		for(Player p : getPlayerArray()){
			PlayerDef def = p.getPlayerDef();
			if(def==null)
				continue;
			if(def.getID()==id)
				return p;
		}
		return null;
	}
	public static Player getPlayerByName(String name){
		for(Player p : getPlayerArray())
			if(p.getPlayerName().equals(name))
				return p;
		return null;
	}
}
