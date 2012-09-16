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

import org.dynamac.enviroment.Data;
import org.dynamac.enviroment.hook.ClassHook;
import org.dynamac.enviroment.hook.FieldHook;


public class NPC extends Character{
	private Object currentObject;
	private ClassHook currentHook;
	public NPC(Object o){
		super(o);
		currentObject=o;
		currentHook = Data.indentifiedClasses.get("NPC");
	}
	public int getLevel(){
		FieldHook fh = currentHook.getFieldHook("getLevel");
		if(fh!=null){
			Object data = fh.getData(currentObject);
			if(data!=null)
				return (Integer)data * fh.getMultiplier();
		}
		return -1;
	}
	public int getID(){
		NPCDef def = getNPCDef();
		if(def!=null)
			return def.getID();
		return -1;
	}
	public NPCDef getNPCDef(){
		FieldHook fh = currentHook.getFieldHook("getNPCDef");
		if(fh!=null){
			Object data = fh.getData(currentObject);
			if(data!=null)
				return new NPCDef(data);
		}
		return null;
	}
	public String getNPCName(){
		FieldHook fh = currentHook.getFieldHook("getNPCName");
		if(fh!=null){
			Object data = fh.getData(currentObject);
			if(data!=null)
				return data.toString();
		}
		return "";
	}
}
