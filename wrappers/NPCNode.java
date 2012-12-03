/******************************************************
* Created by Marneus901                                *
* © 2012 MarneusScripts.com                            *
* **************************************************** *
* Access to this source is unauthorized without prior  *
* authorization from its appropriate author(s).        *
* You are not permitted to release, nor distribute this* 
* work without appropriate author(s) authorization.    *
********************************************************/
package org.dynamac.bot.api.wrappers;

import org.dynamac.environment.Data;
import org.dynamac.reflection.ClassHook;
import org.dynamac.reflection.FieldHook;

public class NPCNode extends Node{
	public Object currentObject;
	public static ClassHook currentHook;
	private static FieldHook npc;
	public NPCNode(Object o){
		super(o);
		currentObject = o;
		if(currentHook==null){
			currentHook = Data.runtimeClassHooks.get("NPCNode");
			npc = currentHook.getFieldHook("getNPC");
		}
	}
	public static void resetHooks(){
		currentHook=null;
		npc=null;
	}
	public NPC getNPC(){
		if(npc==null)
			npc = currentHook.getFieldHook("getNPC");
		if(npc!=null){
			Object data = npc.get(currentObject);
			if(data!=null)
				return new NPC(data);
		}
		return null;
	}
}
