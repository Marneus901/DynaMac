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

public class EntityNode {
	public Object currentObject;
	public static ClassHook currentHook;
	private static FieldHook next;
	private static FieldHook previous;
	public EntityNode(Object o){
		currentObject = o;
		if(currentHook==null){
			currentHook = Data.runtimeClassHooks.get("EntityNode");
			next = currentHook.getFieldHook("getNext");
			previous = currentHook.getFieldHook("getPrevious");
		}
	}
	public static void resetHooks(){
		currentHook=null;
		next=null;
		previous=null;
	}
	public EntityNode getNext(){
		if(next==null)
			next = currentHook.getFieldHook("getNext");
		if(next!=null){
			Object data = next.get(currentObject);
			if(data!=null)
				return new EntityNode(data);
		}
		return null;
	}
	public EntityNode getPrevious(){
		if(previous==null)
			previous = currentHook.getFieldHook("getPrevious");
		if(previous!=null){
			Object data = previous.get(currentObject);
			if(data!=null)
				return new EntityNode(data);
		}
		return null;
	}
}
