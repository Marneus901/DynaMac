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


public class NPCs {
	public static NPC[] getNPCArray(){
		ArrayList<NPC> npcs = new ArrayList<NPC>();
		for(NPCNode node : Client.getNPCNodeArray()){
			if(node!=null){
				NPC npc = node.getNPC();
				if(npc!=null && !npc.getNPCName().equals("null"))
					npcs.add(npc);
			}
		}
		return npcs.toArray(new NPC[]{});
	}
	
	public static NPC getNearest(final int...ids) {
		NPC temp = null;
		double dist = Double.MAX_VALUE;
		for (final NPC npc : getNPCArray()) {
			final int id = npc.getID();
			for (final int i : ids) {
				if (i == id) {
					final int distance = Calculations.distanceTo(npc.getLocationX(), npc.getLocationY());
					if (distance < dist) {
						dist = distance;
						temp = npc;
					}
				}
			}
		}
		return temp;
	}
}
