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

public class HintArrow {
	public Object currentObject;
	public static ClassHook currentHook;
	private static FieldHook headIconIndex;
	private static FieldHook targetIndex;
	public HintArrow(Object o){
		currentObject = o;
		if(currentHook==null){
			currentHook = Data.runtimeClassHooks.get("HintArrow");
			headIconIndex = currentHook.getFieldHook("getHeadIconIndex");
			targetIndex = currentHook.getFieldHook("getTargetIndex");
		}
	}
	public static void resetHooks(){
		currentHook=null;
		headIconIndex=null;
		targetIndex=null;
	}
	public int getHeadIconIndex(){
		if(headIconIndex==null)
			headIconIndex = currentHook.getFieldHook("getHeadIconIndex");
		if(headIconIndex!=null){
			Object data = headIconIndex.get(currentObject);
			if(data!=null)
				return (Integer)data;
		}			
		return -1;
	}
	public int getTargetIndex(){
		if(targetIndex==null)
			targetIndex = currentHook.getFieldHook("getTargetIndex");
		if(targetIndex!=null){
			Object data = targetIndex.get(currentObject);
			if(data!=null)
				return (Integer)data;
		}			
		return -1;
	}
}
