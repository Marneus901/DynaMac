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

public class ItemDefLoader {
	public Object currentObject;
	public static ClassHook currentHook;
	private static FieldHook members;
	private static FieldHook defCache;
	private static FieldHook modelCache;
	public ItemDefLoader(Object o){
		currentObject = o;
		if(currentHook==null){
			currentHook = Data.runtimeClassHooks.get("ItemDefLoader");
			members = currentHook.getFieldHook("isMembers");
			defCache = currentHook.getFieldHook("getDefCache");
			modelCache = currentHook.getFieldHook("getModelCache");
		}
	}
	public static void resetHooks(){
		currentHook=null;
		members=null;
		defCache=null;
		modelCache=null;
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
	public Cache getDefCache(){
		if(defCache==null)
			defCache = currentHook.getFieldHook("getDefCache");
		if(defCache!=null){
			Object data = defCache.get(currentObject);
			if(data!=null)
				return new Cache(data);
		}
		return null;
	}
	public Cache getModelCache(){
		if(modelCache==null)
			modelCache = currentHook.getFieldHook("getModelCache");
		if(modelCache!=null){
			Object data = modelCache.get(currentObject);
			if(data!=null)
				return new Cache(data);
		}
		return null;
	}
}
