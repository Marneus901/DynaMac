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

import org.dynamac.bot.api.wrappers.NPC;
import org.dynamac.bot.api.wrappers.NPCNode;
import org.dynamac.bot.api.wrappers.Tile;

public class NPCs {
	/**
	 * @author Azerbaijan, Marneus901
	 * @param npcIDs
	 * @return The closest NPC with a valid id from the list given
	 */
	public static NPC getNearest(int...ids) {
		NPC temp = null;
		double dist = Double.MAX_VALUE;
		for (NPC npc : getNPCArray()) {
			int id = npc.getNPCDef().getID();
			for (int i : ids) {
				if (i == id) {
					double distance = Calculations.distanceTo(npc.getLocationX(), npc.getLocationY());
					if (distance < dist) {
						dist = distance;
						temp = npc;
					}
				}
			}
		}
		if(temp != null)
			return temp;
		return null;
	}
	
	public NPC getNearest(String... name) {
		NPC temp = null;
		double dist = Double.MAX_VALUE;
		for (NPC npc : NPCs.getNPCArray()) {
			String npcName = npc.getNPCDef().getName();
			for (String i : name) {
				if (i.equalsIgnoreCase(npcName)) {
					double distance = Calculations.distanceTo(
							npc.getLocationX(), npc.getLocationY());
					if (distance < dist) {
						dist = distance;
						temp = npc;
					}
				}
			}
		}
		return temp;
	}

	/**
	 * Some basic recursion added along with a few more null checks, noticed
	 * null pointers being returned occasionally.
	 * @author Gregdw, Marneus901
	 * @param npcIDs
	 * @return The closest NPC with a valid id from the list given on screen
	 */
	public static NPC getNearestOnScreen(int...ids) {
		NPC temp = null;
		double dist = Double.MAX_VALUE;
		for (NPC npc : getNPCArray()) {
			if(npc != null && npc.getNPCDef() != null && npc.getNPCDef().getID() > 1) {
				int id = npc.getNPCDef().getID();
				for (int i : ids) {
					if (i == id) {
						double distance = Calculations.distanceTo(npc.getLocationX(), npc.getLocationY());
						if (distance < dist) {
							dist = distance;
							temp = npc;
						}
					}
				}
			}
		}
		if(temp != null)
			if(temp.isOnScreen())
				return temp;
		return getNearestOnScreen(ids);
	}

	/**
	 * @author Connor132, Marneus901
	 * @param npcIDs
	 * @return Filtered NPCs by given IDs
	 */
	public static NPC[] getNPCsByID(int... npcIDs) {
		ArrayList<NPC> matchedNPCs = new ArrayList<NPC>();
		for (NPC npc : getNPCArray()) {
			for (int currentID : npcIDs) {
				if (npc.getNPCDef().getID() == currentID) {
					matchedNPCs.add(npc);
				}
			}
		}
		return matchedNPCs.toArray(new NPC[]{});
	}
	/**
	 * @author Connor132, Marneus901
	 * @param npcIDs
	 * @return Filtered NPCs by given names
	 */
	public static NPC[] getNPCsByName(String... npcNames) {
		ArrayList<NPC> matchedNPCs = new ArrayList<NPC>();
		for (NPC npc : getNPCArray()) {
			for (String currentName : npcNames) {
				if (npc.getNPCName().toLowerCase().equals(currentName.toLowerCase())) {
					matchedNPCs.add(npc);
				}
			}
		}
		return matchedNPCs.toArray(new NPC[]{});
	}

	public static NPC[] getNPCArray(){
		ArrayList<NPC> npcs = new ArrayList<NPC>();
		for(NPCNode node : Client.getNPCNodeArray()){
			if(node!=null){
				NPC npc = node.getNPC();
				if(npc==null)
					continue;
				if(!npc.getNPCName().equals("null") && !npc.getNPCName().equals(""))
					npcs.add(npc);
			}
		}
		return npcs.toArray(new NPC[]{});
	}

	public static NPC getNPCAt(Tile t){
		if(t==null)
			return null;
		return getNPCAt(t.getX(), t.getY());
	}

	public static NPC getNPCAt(int x, int y){
		for(NPC npc : getNPCArray()){
			if(npc.getLocation().equals(new Tile(x, y, Client.getPlane())))
				return npc;
		}
		return null;
	}

}
