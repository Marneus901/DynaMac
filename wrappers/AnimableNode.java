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

public class AnimableNode {
	public Object currentObject;
	public static ClassHook currentHook;
	private static FieldHook next;
	private static FieldHook animable;
	public AnimableNode(Object o){
		currentObject = o;
		if(currentHook==null){
			currentHook = Data.runtimeClassHooks.get("AnimableNode");
			next=currentHook.getFieldHook("getNext");
			animable=currentHook.getFieldHook("getAnimable");
		}
	}
	public static void resetHooks(){
		currentHook=null;
		next=null;
		animable=null;
	}
	public AnimableNode getNext(){
		if(next==null)
			next=currentHook.getFieldHook("getNext");
		if(next!=null){
			Object data = next.get(currentObject);
			if(data!=null)
				return new AnimableNode(data);
		}
		return null;
	}
	public Animable getAnimable(){
		if(animable==null)
			animable=currentHook.getFieldHook("getAnimable");
		if(animable!=null){
			Object data = animable.get(currentObject);
			if(data!=null)
				return new Animable(data);
		}
		return null;
	}
}
