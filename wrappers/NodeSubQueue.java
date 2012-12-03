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

public class NodeSubQueue {
	private Object currentObject;
	private static ClassHook currentHook;
	private static FieldHook tail;
	public NodeSubQueue(Object o){
		currentObject=o;
		if(currentHook==null){
			currentHook = Data.runtimeClassHooks.get("NodeSubQueue");
			tail = currentHook.getFieldHook("getTail");
		}
	}
	public static void resetHooks(){
		currentHook=null;
		tail=null;
	}
	public NodeSub getTail(){
		if(tail==null)
			tail = currentHook.getFieldHook("getTail");
		if(tail!=null){
			Object data = tail.get(currentObject);
			if(data!=null)
				return new NodeSub(data);
		}
		return null;
	}
}
