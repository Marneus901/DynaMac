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

public class Animator {
	public Object currentObject;
	public static ClassHook currentHook;
	private static FieldHook animation;
	public Animator(Object o){
		currentObject = o;
		if(currentHook==null){
			currentHook = Data.runtimeClassHooks.get("Animator");
			animation=currentHook.getFieldHook("getAnimation");
		}
	}
	public static void resetHooks(){
		currentHook=null;
		animation=null;
	}
	public Animation getAnimation(){
		if(animation==null)
			animation=currentHook.getFieldHook("getAnimation");
		if(animation!=null){
			Object data = animation.get(currentObject);
			if(data!=null)
				return new Animation(data);
		}
		return null;
	}
}
