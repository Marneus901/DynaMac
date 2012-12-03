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

public class Reference extends NodeSub{
	public Object currentObject;
	public static ClassHook currentHook;
	private static FieldHook index;
	public Reference(Object o){
		super(o);
		currentObject = o;
		if(currentHook==null){
			currentHook = Data.runtimeClassHooks.get("Reference");
			index = currentHook.getFieldHook("getIndex");
		}
	}
	public static void resetHooks(){
		currentHook=null;
		index=null;
	}
	public int getIndex(){
		if(index==null)
			index = currentHook.getFieldHook("getIndex");
		if(index!=null){
			Object data = index.get(currentObject);
			if(data!=null)
				return ((Integer)data) * index.getIntMultiplier();	
		}
		return -1;
	}
}
