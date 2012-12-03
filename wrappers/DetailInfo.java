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

public class DetailInfo {
	public Object currentObject;
	public static ClassHook currentHook;
	private static FieldHook level;
	public DetailInfo(Object o){
		currentObject = o;
		if(currentHook==null){
			currentHook = Data.runtimeClassHooks.get("DetailInfo");
			level = currentHook.getFieldHook("getLevel");
		}
	}
	public static void resetHooks(){
		currentHook=null;
		level=null;
	}
	public int getLevel(){
		if(level==null)
			level = currentHook.getFieldHook("getLevel");
		if(level!=null){
			Object data = level.get(currentObject);
			if(data!=null)
				return (Integer)data * level.getIntMultiplier();
		}
		return -1;
	}
}
