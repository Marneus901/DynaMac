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

public class ItemDef {
	public Object currentObject;
	public static ClassHook currentHook;
	private static FieldHook defLoader;
	private static FieldHook nodeTable;
	private static FieldHook id;
	private static FieldHook groundActions;
	private static FieldHook actions;
	private static FieldHook members;
	private static FieldHook name;
	public ItemDef(Object o){
		currentObject = o;
		if(currentHook==null){
			currentHook = Data.runtimeClassHooks.get("ItemDef");
			defLoader = currentHook.getFieldHook("getItemDefLoader");
			nodeTable = currentHook.getFieldHook("getNodeTable");
			id = currentHook.getFieldHook("getID");
			groundActions = currentHook.getFieldHook("getGroundActions");
			actions = currentHook.getFieldHook("getActions");
			members = currentHook.getFieldHook("isMembers");
			name = currentHook.getFieldHook("getName");
		}
	}
	public static void resetHooks(){
		currentHook=null;
		defLoader=null;
		nodeTable=null;
		id=null;
		groundActions=null;
		actions=null;
		members=null;
		name=null;
	}
	public ItemDefLoader getItemDefLoader(){
		if(defLoader==null)
			defLoader = currentHook.getFieldHook("getItemDefLoader");
		if(defLoader!=null){
			Object data = defLoader.get(currentObject);
			if(data!=null)
				return new ItemDefLoader(data);
		}
		return null;
	}
	public HashTable getNodeTable(){
		if(nodeTable==null)
			nodeTable = currentHook.getFieldHook("getNodeTable");
		if(nodeTable!=null){
			Object data = nodeTable.get(currentObject);
			if(data!=null)
				return new HashTable(data);
		}
		return null;
	}
	public int getID(){
		if(id==null)
			id = currentHook.getFieldHook("getID");
		if(id!=null){
			Object data = id.get(currentObject);
			if(data!=null)
				return (Integer)data * id.getIntMultiplier();
		}
		return -1;
	}
	public String[] getGroundActions(){
		if(groundActions==null)
			groundActions = currentHook.getFieldHook("getGroundActions");
		if(groundActions!=null){
			Object data = groundActions.get(currentObject);
			if(data!=null)
				return (String[])data;
		}
		return new String[]{};
	}
	public String[] getActions(){
		if(actions==null)
			actions = currentHook.getFieldHook("getActions");
		if(actions!=null){
			Object data = actions.get(currentObject);
			if(data!=null)
				return (String[])data;
		}
		return new String[]{};
	}
	public boolean isMembers(){
		if(members==null)
			members = currentHook.getFieldHook("isMembers");
		if(members!=null){
			Object data = members.get(currentObject);
			if(data!=null)
				return (Boolean)data;
		}
		return false;
	}
	public String getName(){
		if(name==null)
			name = currentHook.getFieldHook("getName");
		if(name!=null){
			Object data = name.get(currentObject);
			if(data!=null)
				return data.toString();
		}
		return "";
	}
}
