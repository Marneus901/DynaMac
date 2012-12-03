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

public class Viewport {
	public Object currentObject;
	public static ClassHook currentHook;
	private static FieldHook floats;
	public Viewport(Object o){
		currentObject = o;
		if(currentHook==null){
			currentHook = Data.runtimeClassHooks.get("Viewport");
			floats = currentHook.getFieldHook("getFloats");
		}
	}
	public static void resetHooks(){
		currentHook=null;
		floats=null;
	}
	public float[] getFloats(){
		if(floats==null)
			floats = currentHook.getFieldHook("getFloats");
		if(floats!=null){
			Object data = floats.get(currentObject);
			if(data!=null)
				return (float[])data;
		}
		return new float[]{};		
	}
}
