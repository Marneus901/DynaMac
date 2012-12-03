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

public class Cache {
	public Object currentObject;
	public static ClassHook currentHook;
	private static FieldHook table;
	public Cache(Object o){
		currentObject = o;
		if(currentHook==null){
			currentHook = Data.runtimeClassHooks.get("Cache");
			table=currentHook.getFieldHook("getTable");
		}
	}
	public static void resetHooks(){
		currentHook=null;
		table=null;
	}
	public HashTable getTable(){
		if(table==null)
			table=currentHook.getFieldHook("getTable");
		if(table!=null){
			Object data = table.get(currentObject);
			if(data!=null)
				return new HashTable(data);
		}
		return null;
	}
}
