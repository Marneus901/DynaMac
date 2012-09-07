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

import org.dynamac.bot.api.wrappers.NPC;
import org.dynamac.bot.api.wrappers.NPCNode;

public class NPCs {
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
				if(npc!=null && !npc.getNPCName().equals("null"))
					npcs.add(npc);
			}
		}
		return npcs.toArray(new NPC[]{});
	}
}
