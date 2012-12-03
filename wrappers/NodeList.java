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

public class NodeList {
	private Object currentObject;
	private static ClassHook currentHook;
	private static FieldHook tail;
	public NodeList(Object o){
		currentObject=o;
		if(currentHook==null){
			currentHook = Data.runtimeClassHooks.get("NodeList");
			tail = currentHook.getFieldHook("getTail");
		}
	}
	public static void resetHooks(){
		currentHook=null;
		tail=null;
	}
	public Node getTail(){
		if(tail==null)
			tail = currentHook.getFieldHook("getTail");
		if(tail!=null){
			Object data = tail.get(currentObject);
			if(data!=null)
				return new Node(data);
		}
		return null;
	}
}
