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

public class Interactable extends EntityNode{
	public Object currentObject;
	public static ClassHook currentHook;
	private static FieldHook plane;
	public Interactable(Object o){
		super(o);
		currentObject = o;
		if(currentHook==null){
			currentHook = Data.runtimeClassHooks.get("Interactable");
			plane = currentHook.getFieldHook("getPlane");
		}
	}
	public static void resetHooks(){
		currentHook=null;
		plane=null;
	}
	public byte getPlane(){
		if(plane==null)
			plane = currentHook.getFieldHook("getPlane");
		if(plane!=null){
			Object data = plane.get(currentObject);
			if(data!=null)
				return (Byte)data;
		}
		return -1;
	}
}
