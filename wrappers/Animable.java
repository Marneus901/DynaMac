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

public class Animable extends Interactable{
	public Object currentObject;
	public static ClassHook currentHook;
	private static FieldHook minX;
	private static FieldHook minY;
	private static FieldHook maxX;
	private static FieldHook maxY;
	public Animable(Object o){
		super(o);
		currentObject = o;
		if(currentHook==null){
			currentHook = Data.runtimeClassHooks.get("Animable");
			minX=currentHook.getFieldHook("getMinX");
			minY=currentHook.getFieldHook("getMinY");
			maxX=currentHook.getFieldHook("getMaxX");
			maxY=currentHook.getFieldHook("getMaxY");
		}
	}
	public static void resetHooks(){
		currentHook=null;
		minX=null;
		minY=null;
		maxX=null;
		maxY=null;
	}
	public short getMinX(){
		if(minX==null)
			minX=currentHook.getFieldHook("getMinX");
		if(minX!=null){
			Object data = minX.get(currentObject);
			if(data!=null)
				return (Short)data;
		}
		return -1;
	}
	public short getMaxX(){
		if(maxX==null)
			maxX=currentHook.getFieldHook("getMaxX");
		if(maxX!=null){
			Object data = maxX.get(currentObject);
			if(data!=null)
				return (Short)data;
		}
		return -1;
	}
	public short getMinY(){
		if(minY==null)
			minY=currentHook.getFieldHook("getMinY");
		if(minY!=null){
			Object data = minY.get(currentObject);
			if(data!=null)
				return (Short)data;
		}
		return -1;
	}
	public short getMaxY(){
		if(maxY==null)
			maxY=currentHook.getFieldHook("getMaxY");
		if(maxY!=null){
			Object data = maxY.get(currentObject);
			if(data!=null)
				return (Short)data;
		}
		return -1;
	}
}
