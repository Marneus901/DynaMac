/******************************************************
* Created by Marneus901                                *
* � 2012 MarneusScripts.com                            *
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

public class ObjectDef {
	public Object currentObject;
	public static ClassHook currentHook;
	private static FieldHook actions;
	private static FieldHook id;
	private static FieldHook name;
	private static FieldHook defLoader;
	public ObjectDef(Object o){
		currentObject = o;
		if(currentHook==null){
			currentHook = Data.runtimeClassHooks.get("ObjectDef");
			actions = currentHook.getFieldHook("getActions");
			id = currentHook.getFieldHook("getID");
			name = currentHook.getFieldHook("getName");
			defLoader = currentHook.getFieldHook("getObjectDefLoader");
		}
	}
	public static void resetHooks(){
		currentHook=null;
		actions=null;
		id=null;
		name=null;
		defLoader=null;
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
	public ObjectDefLoader getObjectDefLoader(){
		if(defLoader==null)
			defLoader = currentHook.getFieldHook("getObjectDefLoader");
		if(defLoader!=null){
			Object data = defLoader.get(currentObject);
			if(data!=null)
				return new ObjectDefLoader(data);
		}
		return null;
	}
}
