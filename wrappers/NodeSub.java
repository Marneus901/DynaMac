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

public class NodeSub extends Node{
	public Object currentObject;
	public static ClassHook currentHook;
	private static FieldHook idSub;
	private static FieldHook nextSub;
	private static FieldHook previousSub;
	public NodeSub(Object o){
		super(o);
		currentObject = o;
		if(currentHook==null){
			currentHook = Data.runtimeClassHooks.get("NodeSub");
			idSub = currentHook.getFieldHook("getIDSub");
			nextSub = currentHook.getFieldHook("getNextSub");
			previousSub = currentHook.getFieldHook("getPreviousSub");
		}
	}
	public static void resetHooks(){
		currentHook=null;
		idSub=null;
		nextSub=null;
		previousSub=null;
	}
	public int getIDSub(){
		if(idSub==null)
			idSub = currentHook.getFieldHook("getIDSub");
		if(idSub!=null){
			Object data = idSub.get(currentObject);
			if(data!=null)
				return (Integer)data;
		}
		return -1;
	}
	public NodeSub getNextSub(){
		if(nextSub==null)
			nextSub = currentHook.getFieldHook("getNextSub");
		if(nextSub!=null){
			Object data = nextSub.get(currentObject);
			if(data!=null)
				return new NodeSub(data);
		}
		return null;
	}
	public NodeSub getPreviousSub(){
		if(previousSub==null)
			previousSub = currentHook.getFieldHook("getPreviousSub");
		if(previousSub!=null){
			Object data = previousSub.get(currentObject);
			if(data!=null)
				return new NodeSub(data);
		}
		return null;
	}
}
