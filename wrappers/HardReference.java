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

public class HardReference extends Reference{
	public Object currentObject;
	public static ClassHook currentHook;
	private static FieldHook reference;
	public HardReference(Object o){
		super(o);
		currentObject = o;
		if(currentHook==null){
			currentHook = Data.runtimeClassHooks.get("HardReference");
			reference = currentHook.getFieldHook("getReference");
		}
	}
	public static void resetHooks(){
		currentHook=null;
		reference=null;
	}
	public Object getHardReference(){
		if(reference==null)
			reference = currentHook.getFieldHook("getReference");
		if(reference!=null){
			Object data = reference.get(currentObject);
			if(data!=null)
				return data;
		}
		return null;
	}
	public static boolean isInstance(Object o){
		if(currentHook==null)
			currentHook = Data.runtimeClassHooks.get("HardReference");
		if(currentHook!=null)
			return o.getClass().getName().equals(currentHook.getClassName());
		return false;
	}
}
